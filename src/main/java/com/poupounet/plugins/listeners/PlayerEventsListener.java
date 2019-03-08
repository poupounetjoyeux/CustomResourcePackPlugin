package com.poupounet.plugins.listeners;

import com.poupounet.plugins.managers.Managers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventsListener implements Listener {

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
    {
        Player player = event.getPlayer();
        Managers.getGlobalWorldManager().setResourcesPackByPlayer(player);
    }

    @EventHandler
    public void onPlayerJoinedServer(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Managers.getGlobalWorldManager().setResourcesPackByPlayer(player);
    }
}
