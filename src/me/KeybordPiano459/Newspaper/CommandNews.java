package me.KeybordPiano459.Newspaper;

import java.util.ArrayList;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

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
                    ArrayList<String> news = plugin.getNewspaper().getNews();
                    ItemStack newspaper = new ItemStack(Material.WRITTEN_BOOK);
                    BookMeta meta = (BookMeta) newspaper.getItemMeta();
                    meta.setTitle("Newspaper");
                    meta.setAuthor(config.getString("author"));
                    meta.setPages(news);
                    newspaper.setItemMeta(meta);
                    player.getInventory().addItem(newspaper);
                    
                    if (plugin.Vault()) {
                        EconomyResponse response = plugin.econ.withdrawPlayer(player.getName(), config.getDouble("cost"));
                        if (config.getBoolean("vault-message")) {
                            if (response.transactionSuccess()) {
                                String msg = config.getString("vault-message");
                                if (!"none".equals(msg)) {
                                    player.sendMessage(prefix + "$" + config.getString("cost") + " has been withdrawn from your account.");
                                }
                            } else if (config.getBoolean("vault-error")) {
                                player.sendMessage(prefix + ChatColor.RED + "There was an error withdrawing money from your account.");
                            }
                        }
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        plugin.getNewsConfig().reloadConfig();
                        player.sendMessage(prefix + "Reload complete.");
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
}