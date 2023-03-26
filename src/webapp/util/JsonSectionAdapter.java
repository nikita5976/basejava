package webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter <T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        try {
            Class clazz = Class.forName(className);
            return context.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }


    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, section.getClass().getName());
        JsonElement elem = context.serialize(section);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}

/*
T src — собственно, сериализуемый объект;
Type type — тип сериализуемого объекта;
JsonSerializationContext context — контекст сериализации; интерфейс JsonSerializationContext также является функциональным и содержит 1 метод, тоже serialize(); его стоит использовать для обработки непримитивных данных, входящих в сериализуемый объект (и мы это сделаем чуть ниже); контекст наследует все настройки (в т.ч. зарегистрированные сериализаторы и т.п.) исходного Gson-объекта.

 */