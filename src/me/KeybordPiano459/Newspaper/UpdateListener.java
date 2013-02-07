package me.KeybordPiano459.Newspaper;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener {
    Newspaper plugin;
    public UpdateListener(Newspaper plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("newspaper.admin") && plugin.getConfig().getBoolean("update-checker")) {
            player.sendMessage("[" + ChatColor.AQUA + "Newspaper" + ChatColor.RESET + "] " + plugin.updatemsg);
        }
    }
}
