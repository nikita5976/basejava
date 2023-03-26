package webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.AbstractSection;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private final static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter())
            .setPrettyPrinting()
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

}

/*
@SerializedName("age")
private int dwarfAge;
Мы получим на выходе свойство с именем «age» вместо «dwarfAge».
 */

