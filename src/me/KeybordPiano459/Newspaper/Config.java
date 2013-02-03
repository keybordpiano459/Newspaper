package me.KeybordPiano459.Newspaper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    Newspaper plugin;
    public Config(Newspaper plugin) {
        this.plugin = plugin;
    }
    
    public FileConfiguration customConfig;
    public File configFile;
    
    public void createConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                generateConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void generateConfig() {
        try {
            File customConfigFile = new File(plugin.getDataFolder(), "config.yml");
            FileWriter w = new FileWriter(customConfigFile);
            w(w, "# Newspaper | KeybordPiano459");
            w(w, "");
            w(w, "# What should the newspaper's form be?");
            w(w, "# Set this to 'book' or 'map'");
            w(w, "news-type: book");
            w(w, "");
            w(w, "# If the newspaper is in the form of a book, who should the author be?");
            w(w, "author: The MCNews");
            w(w, "");
            w(w, "# What should the news cost? (vault)");
            w(w, "cost: 1.00");
            w(w, "");
            w(w, "# Should players see the transaction message when buying a newspaper?");
            w(w, "vault-message: true");
            w(w, "");
            w(w, "# Should players see the error message if a vault transaction fails?");
            w(w, "vault-error: false");
            w.close();
            reloadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void w(FileWriter writer, String string) throws IOException {
        writer.write(string + "\n");
    }
    
    public void reloadConfig() {
        if (!configFile.exists()) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        customConfig = YamlConfiguration.loadConfiguration(configFile);
        
        InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
    }
    
    public FileConfiguration getConfig() {
        if (customConfig == null) {
            reloadConfig();
        }
        return customConfig;
    }
}