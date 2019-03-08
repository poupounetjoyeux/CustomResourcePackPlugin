package com.poupounet.plugins;

import com.poupounet.plugins.managers.Managers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomResourcePackPlugin extends JavaPlugin {

    public void disablePluginOnError(String reason)
    {
        getLogger().severe(reason);
        getLogger().severe("Plugin will be disabled");
        getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public void onLoad() {
        Managers.initializeManagers(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return Managers.getCommandsManager().processCommand(sender, cmd, args);
    }
}
