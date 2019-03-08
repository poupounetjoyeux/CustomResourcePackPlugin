package com.poupounet.plugins.managers;

import com.poupounet.plugins.CustomResourcePackPlugin;
import com.poupounet.plugins.listeners.WorldGuardRegionsEventsListener;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class WorldGuardManager extends BaseManager {

    public static final String WorldGuardResourcesPackFlagName = "resourcespack";
    public static final String DefaultResourcesPackValue = "default";
    private StringFlag resourcesPackFlag;
    private boolean isEnable;
    private WorldGuard worldGuardInstance;

    public WorldGuardManager(CustomResourcePackPlugin plugin)
    {
        super(plugin);
        this.isEnable = false;
        worldGuardInstance = WorldGuard.getInstance();
        initializeWorldGuardFlagIfWorldGuardIsPresent();
    }

    public void setResourcesPackByPlayerAndRegion(ProtectedRegion region, Player player)
    {
        String regionUri = region.getFlag(this.resourcesPackFlag);
        if(region.equals(DefaultResourcesPackValue))
        {
            Managers.getGlobalWorldManager().setResourcesPackByPlayer(player);
            return;
        }
        player.setResourcePack(regionUri);
    }

    public boolean putResourcesPackFlagOnRegion(String regionId, String resourceUri, CommandSender sender)
    {
        ProtectedRegion region = null;
        for (RegionManager manager : worldGuardInstance.getPlatform().getRegionContainer().getLoaded()) {
            if(manager.hasRegion(regionId))
            {
                region = manager.getRegion(regionId);
                break;
            }
        }
        if(region == null)
        {
            sender.sendMessage("We were not able to found a region with id : '" + regionId + "'");
            return false;
        }
        String finalUri = resourceUri.isEmpty() ? DefaultResourcesPackValue : resourceUri;
        region.setFlag(this.resourcesPackFlag, finalUri);
        sender.sendMessage("'" + regionId + "' has now resources pack '" + finalUri + "'");
        return true;
    }

    public boolean isEnable()
    {
        return this.isEnable;
    }

    private void initializeWorldGuardFlagIfWorldGuardIsPresent()
    {
        if(worldGuardInstance == null)
        {
            this.logger.warning("WorldGuard cannot be found on your server, regions resources packs are disable");
            return;
        }

        this.logger.info("The plugin is now enable and listening for WorldGuard regions events!");
        try {
            FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
            if(registry.get(WorldGuardResourcesPackFlagName) == null)
            {
                this.resourcesPackFlag = new StringFlag(WorldGuardResourcesPackFlagName, DefaultResourcesPackValue);
                registry.register(this.resourcesPackFlag);
                Listener regionsEventsListener = new WorldGuardRegionsEventsListener(logger);
                plugin.getServer().getPluginManager().registerEvents(regionsEventsListener, plugin);
                this.isEnable = true;
                this.logger.info("The flag '" + WorldGuardResourcesPackFlagName + "' was added to WorldGuard!");
            }
        } catch (FlagConflictException e) {
            this.logger.warning("Another plugin register the custom flag " + WorldGuardResourcesPackFlagName +
                    ". WorldGuard regions resources packs are disable");
        }
    }
}
