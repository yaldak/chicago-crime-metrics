package util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        format.setTimeZone(TimeZone.getTimeZone("America/Chicago"));

        try {
            return format.parse(date);
        } catch (ParseException e) {
            // System.err.println(e.getMessage());

            return null;
        }
    }
}
