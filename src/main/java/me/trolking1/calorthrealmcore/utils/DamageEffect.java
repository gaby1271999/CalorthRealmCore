package me.trolking1.calorthrealmcore.utils;


import me.trolking1.calorthrealmcore.Main;
import org.bukkit.entity.Entity;

import java.lang.reflect.Constructor;

public class DamageEffect {

    public void damageEffect(Entity entity) {
        try {
            Object craftEntity = Main.getNmsUtil().getOBCClass("entity.CraftEntity").cast(entity);
            Object handle = Main.getNmsUtil().getOBCClass("entity.CraftEntity").getMethod("getHandle").invoke(craftEntity);


            Constructor<?> animationConstructor = Main.getNmsUtil().getNMSClass("PacketPlayOutAnimation").getConstructor(Main.getNmsUtil().getNMSClass("Entity"), int.class);

            Object packet = animationConstructor.newInstance(handle, 1);

            Main.getNmsUtil().sendPacket(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
