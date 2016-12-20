package me.KeybordPiano459.Newspaper;

import me.KeybordPiano459.Newspaper.maps.MapsFile;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import me.KeybordPiano459.Newspaper.maps.MapIDFile;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Newspaper extends JavaPlugin {

    public String version = "1.4";
    public String updatemsg = "There was an error retrieving update data.";
    
    public Economy econ = null;
    private File folder = new File("plugins" + File.separator + "Newspaper");
    private Config config;
    private NewsFile newsfile;
    private MapsFile mapsfile;
    private MapIDFile mapidfile;
    private UpdateChecker updatechecker;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Newspaper v{0} has been enabled!", version);
        config = new Config(this);
        newsfile = new NewsFile();
        mapsfile = new MapsFile();
        mapidfile = new MapIDFile();
        updatechecker = new UpdateChecker(this);

        folder.mkdirs();
        config.createConfig();
        newsfile.createNews();
        mapsfile.createMapsFile();
        mapidfile.createMapIDFile();
        updatechecker.startUpdateCheck();

        if (!Vault()) {
            getLogger().warning("+--------------------------------------------------------------+");
            getLogger().warning("|               SoftDependency Not Found - VAULT               |");
            getLogger().warning("|  Vault is what makes the economy features of Newspaper work  |");
            getLogger().warning("|      DOWNLOAD - https://dev.bukkit.org/projects/vault/     |");
            getLogger().warning("+--------------------------------------------------------------+");
        }

        getServer().getPluginCommand("news").setExecutor(new CommandNews(this));
        getServer().getPluginManager().registerEvents(new NewsSign(this), this);
        getServer().getPluginManager().registerEvents(new UpdateListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Newspaper v{0} has been disabled.", version);
        getServer().dispatchCommand(getServer().getConsoleSender(), "save-all");
    }

    public boolean Vault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            }
            econ = rsp.getProvider();
            return econ != null;
        }
    }
    
    @Override
    public FileConfiguration getConfig() {
        return this.config.getConfig();
    }
    
    public Config getNewsConfig() {
        return this.config;
    }
    
    public NewsFile getNewspaper() {
        return this.newsfile;
    }
    
    public MapsFile getMapsFile() {
        return this.mapsfile;
    }
    
    public MapIDFile getMapIDFile() {
        return this.mapidfile;
    }
}