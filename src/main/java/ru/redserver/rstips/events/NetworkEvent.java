package ru.redserver.rstips.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import ru.redserver.rstips.RSTipsMod;
import ru.redserver.rstips.TimerWorker;

/**
 * Created by BlackSun on 16.01.16.
 */
public class NetworkEvent {
    public static void registerNetworkEvents() {
        FMLCommonHandler.instance().bus().register(new NetworkEvent());
    }

    @SubscribeEvent
    public void onNetworkEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (event.isLocal) {
            RSTipsMod.log.debug("RSTips [onNetworkEvent] - ClientConnectedToServerEvent");
            TimerWorker.start();
        }
    }

    @SubscribeEvent
    public void onNetworkEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        RSTipsMod.log.debug("RSTips [onNetworkEvent] - ClientDisconnectionFromServerEvent");
        TimerWorker.stop();
    }
}
