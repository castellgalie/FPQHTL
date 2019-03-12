package me.sandias98.fpqhtl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class WorldsCommand implements CommandExecutor {

    private final MiPlugin plugin;

    public WorldsCommand(MiPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("worlds")){
            plugin.initializeMenus();
            if(sender instanceof Player){
                ((Player) sender).openInventory(plugin.worldInventory);
            }
            return true;
        }
        return false;
    }
}
