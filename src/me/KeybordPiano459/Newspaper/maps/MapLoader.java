package me.KeybordPiano459.Newspaper.maps;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import me.KeybordPiano459.Newspaper.Newspaper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapLoader implements Listener {
    Newspaper plugin;
    public MapLoader(Newspaper plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerLoad(PluginEnableEvent event) {
        if (event.getPlugin() == plugin) {
        }
    }
    
    public void setupMaps() {
        for (short id : plugin.getMapIDFile().getMapIDs()) setupMapView(id);
    }

    public MapView setupMapView(short mapid) {
        MapView mv = Bukkit.getMap(mapid);
        if (mv == null) {
            mv = Bukkit.createMap(Bukkit.getWorlds().get(0));
            plugin.getMapIDFile().putMapID(mapid);
        }
        
        for (MapRenderer renderer : mv.getRenderers()) mv.removeRenderer(renderer);
        mv.addRenderer(new NewsMapRenderer(plugin));
        return mv;
    }
}