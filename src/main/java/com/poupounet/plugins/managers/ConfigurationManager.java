package com.poupounet.plugins.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poupounet.plugins.CustomResourcePackPlugin;
import com.poupounet.plugins.models.ConfigurationModel;
import java.io.File;
import java.io.IOException;

public class ConfigurationManager extends BaseManager {

    public static final String ConfigFileName = "config.json";
    private ConfigurationModel currentConfiguration;
    private File configurationFile;

    public ConfigurationManager(CustomResourcePackPlugin plugin)
    {
        super(plugin);
        createDataFolderIfNeeded();
        tryLoadConfig();
    }

    @Override
    public void registerEvents() {

    }

    private void tryLoadConfig() {
        this.configurationFile = new File(this.plugin.getDataFolder(), ConfigFileName);
        if(!configurationFile.exists())
        {
            writeSampleConfigurationFile();
        }
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            this.currentConfiguration = mapper.readValue(configurationFile, ConfigurationModel.class);
        }
        catch (Exception e) {
            this.currentConfiguration = new ConfigurationModel();
        }
    }

    private void writeSampleConfigurationFile()
    {
        ConfigurationModel defaultConfiguration = new ConfigurationModel();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(this.configurationFile, defaultConfiguration);
        } catch (IOException e) {
            plugin.disablePluginOnError("We were not able to write default configuration file");
        }
    }

    public ConfigurationModel getCurrentConfiguration()
    {
        return this.currentConfiguration;
    }

    public void saveConfiguration(ConfigurationModel newConfig)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(this.configurationFile, newConfig);
        } catch (IOException e) {
            this.logger.severe("We were not able to save the new configuration.. " + e.getMessage());
            return;
        }
        this.currentConfiguration = newConfig;
    }

    private void createDataFolderIfNeeded() {
        File dataFolder = this.plugin.getDataFolder();
        if(!dataFolder.exists())
        {
            dataFolder.mkdirs();
        }
    }
}
