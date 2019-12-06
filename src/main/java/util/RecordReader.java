package util;

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

public class RecordReader {
    public static final String RSRC_CRIME_RECORDS = "/data/crime-50k.json";

    public static List<CrimeRecord> readCrimeRecords() throws IOException {
        return RecordReader.readCrimeRecords(new InputStreamReader(RecordReader.class
                .getResourceAsStream(RSRC_CRIME_RECORDS)));
    }

    public static List<CrimeRecord> readCrimeRecords(final InputStream input) throws IOException {
        Objects.requireNonNull(input);

        return RecordReader.readCrimeRecords(new InputStreamReader(input));
    }

    public static List<CrimeRecord> readCrimeRecords(final Reader reader) throws IOException {
        Objects.requireNonNull(reader);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(ZonedDateTime.class, new JsonDateDeserializer())
                .create();

        try {
            return Arrays.asList(gson.fromJson(reader, CrimeRecord[].class));
        } catch (JsonParseException e) {
            throw new IOException("Failed to parse JSON", e);
        }
    }
}
