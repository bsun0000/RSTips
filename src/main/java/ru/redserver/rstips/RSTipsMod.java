package ru.redserver.rstips;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ru.redserver.rstips.common.Config;
import ru.redserver.rstips.common.TipsDb;
import ru.redserver.rstips.events.NetworkEvent;

import java.io.File;
import java.io.IOException;

/**
 * Created by BlackSun on 16.01.16.
 */
@Mod (
        modid = RSTipsMod.MODID,
        name = RSTipsMod.MODNAME,
        version = RSTipsMod.VERSION
)
public class RSTipsMod {
    public static final String MODID = "RSTips";
    public static final String MODNAME = "RSTips Mod";
    public static final String VERSION = "1.0";
    public static Logger log;
    public static File modDir;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
        log.info("RSTips [preInit]: Loading configuration..");

        modDir = event.getModConfigurationDirectory();
        try {
            Config.load(event.getSuggestedConfigurationFile());
        } catch (Exception e) {
            log.error("RSTips Mod has a problem loading it's configuration.");
        }
        finally {
            if (Config.config != null) {
                Config.save();
            }
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        log.info("RSTips [init] Initialization..");
        try {
            TipsDb.loadFromResources("/assets/rstips/text/tips.txt");
        } catch (IOException e) {
            log.error("RSTips Mod has failed to load /assets/rstips/text/tips.txt file.");
            Config.isEnabled = false;
        }
        finally {
            log.info(String.format(
                    "RSTips [init] Loaded %d tips. Enabled: %b, Random order: %b, Current pos: %d",
                    TipsDb.size(),
                    Config.isEnabled,
                    Config.isRandom,
                    Config.lastUsed
            ));

            NetworkEvent.registerNetworkEvents();
        }
    }
}