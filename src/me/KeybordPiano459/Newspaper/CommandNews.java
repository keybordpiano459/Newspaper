package me.KeybordPiano459.Newspaper;

import me.KeybordPiano459.Newspaper.maps.NewsMapRenderer;
import java.util.ArrayList;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class CommandNews implements CommandExecutor {
    Newspaper plugin;
    public CommandNews(Newspaper plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
    }
    
    private String prefix = "[" + ChatColor.AQUA + "Newspaper" + ChatColor.RESET + "] ";
    private FileConfiguration config;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("news")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    String nt = plugin.getConfig().getString("news-type");
                    if (nt.equalsIgnoreCase("book")) giveNewsBook(player);
                    else if (nt.equalsIgnoreCase("map")) giveNewsMap(player);
                    else player.sendMessage(prefix + "This plugin wasn't set up correctly. Please contact an administrator.");
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (player.hasPermission("newspaper.admin")) {
                            plugin.getNewsConfig().reloadConfig();
                            player.sendMessage(prefix + "Reload complete.");
                        } else {
                            player.sendMessage(prefix + "You don't have permission to do that!");
                        }
                    } else {
                        player.sendMessage(prefix + "Incorrect usage! Type /news");
                    }
                } else {
                    player.sendMessage(prefix + "Incorrect usage! Type /news");
                }
            } else {
                Logger.getLogger("Minecraft").info("This command can't be used from console!");
            }
        }
        return false;
    }

    private void giveNewsBook(Player player) {
        ItemStack newspaper = new ItemStack(Material.WRITTEN_BOOK);
        ArrayList<String> news = plugin.getNewspaper().getNews();
        BookMeta meta = (BookMeta) newspaper.getItemMeta();
        meta.setTitle(ChatColor.YELLOW + "Newspaper");
        meta.setAuthor(config.getString("author"));
        meta.setPages(news);
        newspaper.setItemMeta(meta);
        player.getInventory().addItem(newspaper);
        withdrawNewsMoney(player);
    }

    private void giveNewsMap(Player player) {
        ItemStack map = new ItemStack(Material.MAP);
        MapView mv = Bukkit.createMap(Bukkit.getWorlds().get(0));
        for (MapRenderer renderer : mv.getRenderers()) mv.removeRenderer(renderer);
        mv.addRenderer(new NewsMapRenderer(plugin));
        map.setDurability(mv.getId());
        plugin.getMapIDFile().putMapID(mv.getId());
        ItemMeta meta = map.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Newspaper");
        map.setItemMeta(meta);
        player.getInventory().addItem(map);
        withdrawNewsMoney(player);
    }

    private void withdrawNewsMoney(Player player) {
        if (plugin.Vault()) {
            EconomyResponse response = plugin.econ.withdrawPlayer(player.getName(), config.getDouble("cost"));
            if (config.getBoolean("vault-message")) {
                if (response.transactionSuccess()) {
                    player.sendMessage(prefix + "$" + config.getString("cost") + " has been withdrawn from your account.");
                } else if (config.getBoolean("vault-error")) {
                    player.sendMessage(prefix + "There was an error withdrawing money from your account.");
                }
            }
        }
    }
}