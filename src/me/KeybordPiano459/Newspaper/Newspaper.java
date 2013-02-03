package me.KeybordPiano459.Newspaper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Newspaper extends JavaPlugin {

    public ArrayList<Short> mapids = new ArrayList<Short>();
    public Economy econ = null;
    private File folder = new File("plugins" + File.separator + "Newspaper");
    private Config config;
    private NewsFile newsfile;
    private MapsFile mapsfile;

    @Override
    public void onEnable() {
        getLogger().info("Newspaper v1.2 has been enabled!");
        config = new Config(this);
        newsfile = new NewsFile();
        mapsfile = new MapsFile();

        folder.mkdirs();
        config.createConfig();
        newsfile.createNews();
        mapsfile.createMapsFile();

        try {
            BukkitMetrics metrics = new BukkitMetrics(this);
            MetricsGraph graph = new MetricsGraph(this);
            graph.addPagesGraph(metrics);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }

        if (!Vault()) {
            getLogger().warning("+--------------------------------------------------------------+");
            getLogger().warning("|               SoftDependency Not Found - VAULT               |");
            getLogger().warning("|  Vault is what makes the economy features of Newspaper work  |");
            getLogger().warning("|      DOWNLOAD - http://dev.bukkit.org/server-mods/vault/     |");
            getLogger().warning("+--------------------------------------------------------------+");
        }

        getServer().getPluginCommand("news").setExecutor(new CommandNews(this));
        getServer().getPluginManager().registerEvents(new NewsSign(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Newspaper v1.2 has been disabled.");
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
}