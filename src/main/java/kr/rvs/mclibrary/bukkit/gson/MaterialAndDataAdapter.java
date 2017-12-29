package kr.rvs.mclibrary.bukkit.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import kr.rvs.mclibrary.bukkit.item.MaterialAndData;
import org.bukkit.Material;

import java.io.IOException;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
public class MaterialAndDataAdapter extends TypeAdapter<MaterialAndData> {
    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public void write(JsonWriter out, MaterialAndData value) throws IOException {
        if (value != null) {
            out.value(value.getMaterial().getId() + ":" + value.getData());
        } else {
            out.nullValue();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public MaterialAndData read(JsonReader in) throws IOException {
        if (in.hasNext() && in.peek() == JsonToken.STRING) {
            return MaterialAndData.parse(in.nextString());
        }
        return null;
    }
}
