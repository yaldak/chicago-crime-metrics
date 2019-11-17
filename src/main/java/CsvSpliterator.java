import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Basic CSV-file spliterator, allowing you to access a CSV file as a stream.
 *
 * @author Yalda Kako {@literal <yalda@kako.cc>}
 */
public final class CsvSpliterator extends Spliterators.AbstractSpliterator<CsvSpliterator.CsvRecord> {
    public class CsvRecord {
        private List<String> values;

        private CsvRecord(final List<String> values) {
            this.values = values;
        }

        public List<String> getValues() {
            return values;
        }
    }

    private BufferedReader reader;
    private boolean withHeader;

    private CsvSpliterator(final BufferedReader reader, final boolean withHeader) {
        super(Long.MAX_VALUE, ORDERED | NONNULL);

        this.reader = reader;

        // XXX(@ykako): do something with this.
        this.withHeader = withHeader;
    }

    @Override
    public boolean tryAdvance(final Consumer<? super CsvRecord> action) {
        Objects.requireNonNull(action);

        return tryReadLine(this.reader)
                .flatMap(this::tryParseRecord)
                .map(record -> {
                    action.accept(record);
                    return record;
                })
                .isPresent();
    }

    private Optional<CsvRecord> tryParseRecord(final String s) {
        List<String> values = new ArrayList<>();

        boolean inQuoteBlock = false;
        boolean isLastCharQuote = false;
        int blockStart = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '\"') {
                if (isLastCharQuote) {
                    // This is an escaped quote character
                    isLastCharQuote = false;
                    continue;
                }

                if (inQuoteBlock) {
                    // This is the end of a quote block
                    values.add(s.substring(blockStart + 1, i));

                    inQuoteBlock = false;
                } else {
                    // This is the start of a quote block
                    isLastCharQuote = true;
                    inQuoteBlock = true;
                }
            } else if (i == s.length() - 1) {
                // This is the end of the line
                if (c != ',') {
                    // XXX What if this is a quoted block
                    values.add(s.substring(blockStart, i + 1).trim());
                }
            } else if (c == ',' && !inQuoteBlock) {
                values.add(s.substring(blockStart, i).trim());

                blockStart = i + 1;
            } else {
                isLastCharQuote = false;
            }
        }

        return Optional.of(new CsvRecord(Collections.unmodifiableList(values)));
    }

    private Optional<String> tryReadLine(final BufferedReader reader) {
        try {
            // Note that this is a potentially blocking I/O call, we do not check .ready()
            return Optional.ofNullable(this.reader.readLine());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static CsvSpliterator from(final Reader reader, final boolean withHeader) {
        Objects.requireNonNull(reader);

        return new CsvSpliterator(new BufferedReader(reader), withHeader);
    }

    public static Stream<CsvRecord> stream(final Reader reader, final boolean withHeader) {
        Objects.requireNonNull(reader);

        return StreamSupport.stream(CsvSpliterator.from(reader, withHeader), false);
    }
}
