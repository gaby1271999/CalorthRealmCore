package me.trolking1.calorthrealmcore.custommobs;

import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.EntityTypes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityManager {

    public static void registerEntity(String name, int id, Class<? extends EntityInsentient> customClass) {
        try {

            /*
             * First, we make a list of all HashMap's in the EntityTypes class
             * by looping through all fields. I am using reflection here so we
             * have no problems later when Mojang changes the field's name. By
             * creating a list of these maps we can easily modify them later on.
             */
            List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                    System.out.println("field  : " + f.getName());
                }
            }

            // we need maps d (1) and f (3)
            if (dataMaps.get(2).containsKey(id)) {
                ((Map<Class<? extends EntityInsentient>, String>) dataMaps.get(1)).put(customClass, name);
                ((Map<Class<? extends EntityInsentient>, Integer>) dataMaps.get(3)).put(customClass, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
