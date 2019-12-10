package data;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import records.CrimeRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JsonRecordReader implements RecordReader {
    public static final String DEFAULT_CRIME_RECORDS = "/data/crime-80k.json";

    private Reader reader;

    private JsonRecordReader(final Reader reader) {
        this.reader = reader;
    }

    public List<CrimeRecord> readCrimeRecords() throws IOException {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(ZonedDateTime.class, new JsonDateDeserializer())
                .create();

        try {
            return Arrays.asList(gson.fromJson(this.reader, CrimeRecord[].class));
        } catch (JsonParseException e) {
            throw new IOException("Failed to parse JSON", e);
        }
    }

    public static JsonRecordReader from(final Reader reader) {
        Objects.requireNonNull(reader);

        return new JsonRecordReader(reader);
    }

    public static JsonRecordReader from(final InputStream input) {
        Objects.requireNonNull(input);

        return new JsonRecordReader(new InputStreamReader(input));
    }

    public static JsonRecordReader fromDefaultSet() {
        return JsonRecordReader.from(JsonRecordReader.class.getResourceAsStream(DEFAULT_CRIME_RECORDS));
    }
}
