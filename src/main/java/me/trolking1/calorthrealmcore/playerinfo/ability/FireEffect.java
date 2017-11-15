package me.trolking1.calorthrealmcore.playerinfo.ability;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 4/11/2017.
 */
@SerializableAs("fireeffect")
public class FireEffect implements ConfigurationSerializable {

    private byte duration;

    public FireEffect(byte duration) {
        this.duration = duration;
    }

    public FireEffect(Map<String, Object> map) {
        this.duration = Main.getMain().intToByte((int) map.get("duration"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("duration", duration);

        return map;
    }

    public byte getDuration() {
        return duration;
    }

    public void setDuration(byte duration) {
        this.duration = duration;
    }
}
