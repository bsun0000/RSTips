package ru.redserver.rstips.common;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

/**
 * Created by BlackSun on 16.01.16.
 */
public class Config {
    public static Configuration config;
    public static boolean isEnabled = true;
    public static boolean isRandom = false;

    public static int interval = 5 * 60; // 5 минут
    public static int lastUsed = 0;

    public static void load(File file) {
        if (config != null) {
            return;
        }
        config = new Configuration(file);
        config.defaultEncoding = "windows-1251"; // Фикс для русского текста в комментах
        config.load();

        String comment = "Показ сообщений по таймеру";
        isEnabled = config.get("Tips", "enabled", isEnabled, comment).getBoolean();

        comment = "Случайный или последовательный показ сообщений";
        isRandom  = config.get("Tips", "random", isRandom, comment).getBoolean();

        comment = "Номер последнего показанного сообщения (в посл. режиме)";
        lastUsed  = config.get("Tips", "lastUsed", lastUsed, comment).getInt();
        if (lastUsed < 0) {
            lastUsed = 0;
        }

        comment = "Длительность таймера, в секундах";
        interval  = config.get("Timer", "interval", interval, comment).getInt();
        if (interval < 1) {
            interval = 1;
        }

        config.save();
    }

    public static void save() {
        config
                .get("Tips", "lastUsed", lastUsed, "Номер последнего показанного сообщения (в посл. режиме)")
                .set(lastUsed);
        config.save();
    }
}
