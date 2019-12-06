package util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonDateDeserializer implements JsonDeserializer<ZonedDateTime> {
    public static final DateTimeFormatter CITYOFCHICAGO_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @Override
    public ZonedDateTime deserialize(final JsonElement element, final Type type,
            final JsonDeserializationContext context) throws JsonParseException {
        // XXX: Null-checks/best practices for GSON?
        try {
            return LocalDateTime.parse(element.getAsString(), CITYOFCHICAGO_DATE_FORMATTER)
                    .atZone(ZoneId.of("America/Chicago"));
        } catch (DateTimeException e) {
            // XXX: For debugging
            System.err.println(e.getMessage());

            return null;
        }
    }
}
