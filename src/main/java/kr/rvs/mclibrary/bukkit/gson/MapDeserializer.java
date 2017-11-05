package kr.rvs.mclibrary.bukkit.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.$Gson$Types;
import kr.rvs.mclibrary.Static;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-11-05.
 */
public class MapDeserializer implements JsonDeserializer<Map> {
    private Optional<Number> getNumber(String string) {
        Number number = null;
        try {
            number = NumberFormat.getInstance().parse(string);
            if (number.longValue() == number.intValue())
                number = number.intValue();
        } catch (ParseException e) {
            // Ignore
        }
        return Optional.ofNullable(number);
    }

    @Override
    public Map deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Type[] genericTypes = $Gson$Types.getMapKeyAndValueTypes(typeOfT, $Gson$Types.getRawType(typeOfT));
        Type valType = genericTypes[1];
        JsonObject json = element.getAsJsonObject();
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            String key = entry.getKey();
            JsonElement val = entry.getValue();
            if (val.isJsonObject()) {
                map.put(key, valType == Object.class
                        ? deserialize(val, Map.class, context)
                        : context.deserialize(val, valType));
            } else if (val.isJsonPrimitive()) {
                JsonPrimitive primitive = val.getAsJsonPrimitive();
                if (primitive.isString()) {
                    map.put(key, primitive.getAsString());
                } else if (primitive.isBoolean()) {
                    map.put(key, primitive.getAsBoolean());
                } else if (primitive.isNumber()) {
                    getNumber(primitive.toString()).ifPresent(number ->
                            map.put(key, number));
                }
            } else if (val.isJsonArray()) {
                map.put(key, context.deserialize(val, List.class));
            } else {
                Static.log("Unknown element type, " + val.toString());
            }
        }
        return map;
    }
}
