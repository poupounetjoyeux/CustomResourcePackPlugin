package com.poupounet.plugins.managers;

import com.poupounet.plugins.CustomResourcePackPlugin;
import com.poupounet.plugins.listeners.PlayerEventsListener;
import org.bukkit.entity.Player;

public class GlobalWorldManager extends BaseManager {

    public GlobalWorldManager(CustomResourcePackPlugin plugin) {
        super(plugin);
        if(!Managers.getWorldGuardManager().isEnable())
        {
            plugin.getServer().getPluginManager().registerEvents(new PlayerEventsListener(), plugin);
        }
    }

    public void setResourcesPackByPlayer(Player player)
    {
        String worldResourcesPackUri = Managers.getConfigurationManager()
                                                .getCurrentConfiguration()
                                                .getDefaultUriForWorld(player.getWorld());
        if(worldResourcesPackUri.isEmpty())
        {
            this.plugin.disablePluginOnError("No default resources pack was set into configuration file!");
            return;
        }
        player.setResourcePack(worldResourcesPackUri);
    }
}
