package com.poupounet.plugins.models;
import org.bukkit.World;
import java.util.HashMap;

public class ConfigurationModel {

    public static final String DefaultResourcesPackUri = "http://";
    public static final String DefaultWorldName = "world";

    public ConfigurationModel()
    {
        this.defaultResourcePackUri = DefaultResourcesPackUri;
        this.defaultPerWorldResourcesPackUris = new HashMap<>();
        this.defaultPerWorldResourcesPackUris.put(DefaultWorldName, DefaultResourcesPackUri);
    }

    private String defaultResourcePackUri;
    private HashMap<String, String> defaultPerWorldResourcesPackUris;

    public void setDefaultResourcePackUri(String defaultResourcePackUri) {
        this.defaultResourcePackUri = defaultResourcePackUri;
    }

    public String getDefaultUriForWorld(World world) {
        String worldResourcesPackUri = defaultPerWorldResourcesPackUris.get(world.getName());
        if(worldResourcesPackUri.isEmpty())
        {
            return defaultResourcePackUri;
        }
        return worldResourcesPackUri;
    }

    public void addOrReplacePerWorldUri(String worldName, String resourcesPackUri)
    {
        this.defaultPerWorldResourcesPackUris.put(worldName, resourcesPackUri);
    }

    public void setDefaultPerWorldResourcesPackUris(HashMap<String, String> defaultPerWorldResourcesPackUris) {
        this.defaultPerWorldResourcesPackUris = defaultPerWorldResourcesPackUris;
    }
}
