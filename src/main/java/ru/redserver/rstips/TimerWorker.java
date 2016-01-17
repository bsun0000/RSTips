package ru.redserver.rstips;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import ru.redserver.rstips.common.Config;
import ru.redserver.rstips.common.TipsDb;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BlackSun on 16.01.16.
 */
public class TimerWorker extends TimerTask {
    private static boolean active = false;
    private static Timer timer;

    @Override
    public void run() {
        RSTipsMod.log.debug("TimerWorker: I'm doing my job, master!");
        String message = TipsDb.getMessage();
        RSTipsMod.log.info(message);

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        player.addChatComponentMessage(new ChatComponentText(message));
    }

    public static void start() {
        if (active) {
            return;
        }

        timer = new Timer();
        TimerTask task = new TimerWorker();
        timer.scheduleAtFixedRate(task, Config.interval * 1000, Config.interval * 1000);
        active = true;
    }

    public static void stop() {
        timer.cancel();
        active = false;
    }

    public static boolean isActive() {
        return active;
    }
}
