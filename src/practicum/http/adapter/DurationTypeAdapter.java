package practicum.http.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DurationTypeAdapter extends TypeAdapter<Duration> {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("mm");

    /*@Override
    public void write(JsonWriter jsonWriter, Duration duration) throws IOException {

    }

    @Override
    public Duration read(JsonReader jsonReader) throws IOException {
        return Duration.parse(jsonReader.nextString());
    }*/

    @Override
    public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
        if (duration == null) {
            jsonWriter.nullValue();
            return;
        }
        // Записываем в минутах (или как тебе нужно)
        jsonWriter.value(duration.toMinutes());
    }

    @Override
    public Duration read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek().name().equals("NULL")) {
            jsonReader.nextNull();
            return null;
        }
        // читаем минуты
        long minutes = jsonReader.nextLong();
        return Duration.ofMinutes(minutes);
    }
}
