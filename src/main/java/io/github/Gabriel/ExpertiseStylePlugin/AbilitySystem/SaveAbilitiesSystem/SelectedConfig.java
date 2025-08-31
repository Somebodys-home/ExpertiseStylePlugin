package io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class SelectedConfig {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private File file;
    private String fileName;
    private FileConfiguration config = new YamlConfiguration();

    public SelectedConfig(ExpertiseStylePlugin expertiseStylePlugin, String filename) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        this.fileName = filename;
        file = new File(expertiseStylePlugin.getDataFolder(), filename + ".yml");
    }

    public void loadConfig() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            expertiseStylePlugin.saveResource(fileName + ".yml", false);
        } try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
