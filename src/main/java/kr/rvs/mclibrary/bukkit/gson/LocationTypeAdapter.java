package kr.rvs.mclibrary.bukkit.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import kr.rvs.mclibrary.Static;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;

/**
 * Created by Junhyeong Lim on 2017-10-22.
 */
public class LocationTypeAdapter extends TypeAdapter<Location> {
    @Override
    public void write(JsonWriter out, Location value) throws IOException {
        if (value != null) {
            out.beginObject();
            out.name("world").value(value.getWorld().getName());
            out.name("x").value(value.getX());
            out.name("y").value(value.getY());
            out.name("z").value(value.getZ());
            out.name("yaw").value(value.getYaw());
            out.name("pitch").value(value.getPitch());
            out.endObject();
        } else {
            out.nullValue();
        }
    }

    @Override
    public Location read(JsonReader in) throws IOException {
        if (in.peek() != JsonToken.BEGIN_OBJECT)
            return null;

        World world = null;
        Double x = null;
        Double y = null;
        Double z = null;
        Double yaw = null;
        Double pitch = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "world":
                    world = Bukkit.getWorld(in.nextString());
                    break;
                case "x":
                    x = in.nextDouble();
                    break;
                case "y":
                    y = in.nextDouble();
                    break;
                case "z":
                    z = in.nextDouble();
                    break;
                case "yaw":
                    yaw = in.nextDouble();
                    break;
                case "pitch":
                    pitch = in.nextDouble();
                    break;
                default:
                    Static.log("Unknown type, " + name);
            }
        }
        in.endObject();

        return world != null && x != null && y != null && z != null &&
                pitch != null && yaw != null
                ? new Location(world, x, y, z, yaw.floatValue(), pitch.floatValue())
                : null;
    }
}
