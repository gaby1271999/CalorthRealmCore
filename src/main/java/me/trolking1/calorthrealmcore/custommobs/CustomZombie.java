package me.trolking1.calorthrealmcore.custommobs;

import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.lang.reflect.Field;
import java.util.Set;

public class CustomZombie extends EntityZombie {

    public CustomZombie(org.bukkit.World world) {
        super(((CraftWorld) world).getHandle());

        Set goalB = (Set)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        Set goalC = (Set)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        Set targetB = (Set)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        Set targetC = (Set)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch(NoSuchFieldException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }
}
