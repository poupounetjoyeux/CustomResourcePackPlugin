package com.poupounet.plugins.listeners;

import com.poupounet.plugins.managers.Managers;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class WorldGuardRegionsEventsListener implements Listener {

    private Logger logger;

    public WorldGuardRegionsEventsListener(Logger logger)
    {
        this.logger = logger;
    }

    //Needed to be sure that join world event will be processed before region events
    @EventHandler
    public void onRegionEntered(RegionEnteredEvent event)
    {
        Player player = event.getPlayer();
        ProtectedRegion region = event.getRegion();
        Managers.getWorldGuardManager().setResourcesPackByPlayerAndRegion(region, player);
        logger.info("The player '" + player.getDisplayName() + "' is entered in region '" + region.getId() + "'");
    }

    @EventHandler
    public void onRegionLeft(RegionLeftEvent event)
    {
        Player player = event.getPlayer();
        Managers.getGlobalWorldManager().setResourcesPackByPlayer(player);
        ProtectedRegion region = event.getRegion();
        logger.info("The player '" + player.getDisplayName() + "' has left the region '" + region.getId() + "'");
    }
}
