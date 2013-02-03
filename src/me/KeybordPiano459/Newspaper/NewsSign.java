package me.KeybordPiano459.Newspaper;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class NewsSign implements Listener {
    Newspaper plugin;
    public NewsSign(Newspaper plugin) {
        this.plugin = plugin;
    }
    
    private String prefix = "[" + ChatColor.AQUA + "Newspaper" + ChatColor.RESET + "] ";

    /*
     * 
     * Make the [News] sign
     */
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (event.getLine(0).contains("[News]") || event.getLine(0).contains("[Newspaper]")) {
            if (player.hasPermission("newspaper.admin")) {
                event.setLine(0, ChatColor.translateAlternateColorCodes('&', event.getLine(0)));
                player.sendMessage(prefix + "You have made a fully functional news sign!");
            } else {
                event.setLine(0, ChatColor.RED + "[?]");
                event.setLine(1, "");
                event.setLine(2, "");
                event.setLine(3, "");
                player.sendMessage(prefix + "You don't have permission to make that sign!");
            }
        }
    }

    /*
     * 
     * Use the [News] sign
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getTypeId() == 63 || event.getClickedBlock().getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).contains("[News]") || sign.getLine(0).contains("[Newspaper]")) {
                    player.performCommand("news");
                }
            }
        }
    }
}