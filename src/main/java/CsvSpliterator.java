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
        Objects.requireNonNull(s);

        List<String> values = new ArrayList<>();

        boolean inQuoteBlock = false;
        for (int i = 0; i < s.length(); i++) {
            // TODO(@ykako): parse here
        }

        // TODO(@ykako): fix me
        values.add(0, "This");
        values.add(1, "Is");
        values.add(2, "A Test");

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
        return StreamSupport.stream(CsvSpliterator.from(reader, withHeader), false);
    }
}
