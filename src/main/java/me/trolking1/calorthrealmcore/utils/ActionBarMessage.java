package me.trolking1.calorthrealmcore.utils;

import me.trolking1.calorthrealmcore.Main;

import java.lang.reflect.Constructor;

public class ActionBarMessage {

    public void actionBarMessage(String message) {

        try {
            Class<?>[] declaredClasses =  Main.getNmsUtil().getNMSClass("IChatBaseComponent").getDeclaredClasses();

            Object chatSerializer = declaredClasses[0].getMethod("a", String.class).invoke(null, "{\"text\": \" \n" + message + "\"}");
            Object chatMessageType = Main.getNmsUtil().getNMSClass("ChatMessageType").getMethod("valueOf", String.class).invoke(null, "GAME_INFO");

            Constructor<?> actionBarConstructor = Main.getNmsUtil().getNMSClass("PacketPlayOutChat").getConstructor(Main.getNmsUtil().getNMSClass("IChatBaseComponent"), Main.getNmsUtil().getNMSClass("ChatMessageType"));

            Object packet = actionBarConstructor.newInstance(chatSerializer, chatMessageType);

            Main.getNmsUtil().sendPacket(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
