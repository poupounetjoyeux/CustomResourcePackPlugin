package com.poupounet.plugins.managers;

import com.poupounet.plugins.CustomResourcePackPlugin;

import java.util.logging.Logger;

public abstract class BaseManager {

    protected CustomResourcePackPlugin plugin;
    protected Logger logger;

    public BaseManager(CustomResourcePackPlugin plugin)
    {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    public abstract void registerEvents();
}
