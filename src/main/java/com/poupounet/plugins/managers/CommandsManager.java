package com.poupounet.plugins.managers;

import com.poupounet.plugins.CustomResourcePackPlugin;
import com.poupounet.plugins.models.ConfigurationModel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.Properties;

public class CommandsManager extends BaseManager {

    public CommandsManager(CustomResourcePackPlugin plugin) {
        super(plugin);
    }

    @Override
    public void registerEvents() {

    }

    public boolean processCommand(CommandSender sender, Command cmd, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("crpversion")) {
            return printVersion(sender);
        } else if(cmd.getName().equalsIgnoreCase("crpglobal")) {
            return setGlobalTexture(sender, args);
        } else if(cmd.getName().equalsIgnoreCase("crpworld")) {
            return setWorldTexture(sender, args);
        } else if(cmd.getName().equalsIgnoreCase("crpregion")) {
            return setRegionTexture(sender, args);
        }
        return false;
    }

    private boolean setGlobalTexture(CommandSender sender, String[] args) {
        if(args.length > 1)
        {
            return false;
        }
        ConfigurationManager configurationManager = Managers.getConfigurationManager();
        ConfigurationModel currentConfig = configurationManager.getCurrentConfiguration();
        String finalUri = args.length == 1 ? args[0] : ConfigurationModel.DefaultResourcesPackUri;
        currentConfig.setDefaultResourcePackUri(finalUri);
        configurationManager.saveConfiguration(currentConfig);
        sender.sendMessage("The new default resources pack was correctly set");
        return true;
    }

    private boolean setWorldTexture(CommandSender sender, String[] args) {
        if(args.length > 2 || args.length < 1)
        {
            return false;
        }
        ConfigurationManager configurationManager = Managers.getConfigurationManager();
        ConfigurationModel currentConfig = configurationManager.getCurrentConfiguration();
        String worldName = args[0];
        String finalUri = args.length == 2 ? args[1] : ConfigurationModel.DefaultResourcesPackUri;
        currentConfig.addOrReplacePerWorldUri(worldName, finalUri);
        configurationManager.saveConfiguration(currentConfig);
        sender.sendMessage("The new default resources pack was correctly set for world '" + worldName + "'");
        return true;
    }

    private boolean setRegionTexture(CommandSender sender, String[] args) {
        WorldGuardManager worldGuardManager = Managers.getWorldGuardManager();
        if(args.length > 2 || args.length < 1)
        {
            return false;
        }
        if(!worldGuardManager.isEnable())
        {
            sender.sendMessage("You have no WorldGuard on your server. You cannot put resources pack on regions!");
            return false;
        }
        String regionId = args[0];
        String finalUri = args.length == 2 ? args[1] : ConfigurationModel.DefaultResourcesPackUri;
        return worldGuardManager.putResourcesPackFlagOnRegion(regionId, finalUri, sender);
    }

    private boolean printVersion(CommandSender sender) {
        String version;
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("values.properties"));
            version = properties.getProperty("version");
        } catch (IOException e) {
            version = "Unknown";
        }
        sender.sendMessage("You have currently the version " + version + " of the plugin");
        return true;
    }
}
