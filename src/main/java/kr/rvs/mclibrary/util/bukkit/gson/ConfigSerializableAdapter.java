package kr.rvs.mclibrary.util.bukkit.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-08-28.
 */
public class ConfigSerializableAdapter extends TypeAdapter<ConfigurationSerializable> {
    private final TypeAdapter<Map> mapAdapter;

    public ConfigSerializableAdapter(TypeAdapter<Map> mapAdapter) {
        this.mapAdapter = mapAdapter;
    }

    @Override
    public void write(JsonWriter out, ConfigurationSerializable value) throws IOException {
        mapAdapter.write(out, value.serialize());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Location read(JsonReader in) throws IOException {
        Map<String, Object> locationMap = mapAdapter.read(in);
        return (Location) ConfigurationSerialization.deserializeObject(locationMap);
    }
}
