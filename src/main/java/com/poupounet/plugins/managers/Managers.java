package com.poupounet.plugins.managers;

import com.poupounet.plugins.CustomResourcePackPlugin;

public class Managers {
    private static ConfigurationManager configurationManager;
    private static WorldGuardManager worldGuardManager;
    private static GlobalWorldManager globalWorldManager;
    private static CommandsManager commandsManager;

    public static void initializeManagers(CustomResourcePackPlugin plugin)
    {
        configurationManager = new ConfigurationManager(plugin);
        //worldGuardManager must be init BEFORE globalWorldManager
        worldGuardManager = new WorldGuardManager(plugin);
        globalWorldManager = new GlobalWorldManager(plugin);
        commandsManager = new CommandsManager(plugin);
    }

    public static WorldGuardManager getWorldGuardManager()
    {
        return worldGuardManager;
    }

    public static GlobalWorldManager getGlobalWorldManager()
    {
        return globalWorldManager;
    }

    public static ConfigurationManager getConfigurationManager()
    {
        return configurationManager;
    }

    public static CommandsManager getCommandsManager()
    {
        return commandsManager;
    }
}
