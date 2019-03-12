package me.sandias98.fpqhtl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class InventoryListener implements Listener {
    MiPlugin plugin;

    public InventoryListener(MiPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void alVolar(PlayerToggleFlightEvent e){
        if(plugin.dataBase.getBoolean("world." + e.getPlayer().getWorld().getName() + ".doublejump")){
            if(e.getPlayer().getGameMode() == GameMode.SURVIVAL || e.getPlayer().getGameMode() == GameMode.ADVENTURE){
                e.getPlayer().setVelocity(new Vector(0,1,0));
                e.setCancelled(true);

            }
        } else {
            if(e.getPlayer().getGameMode() == GameMode.SURVIVAL || e.getPlayer().getGameMode() == GameMode.ADVENTURE){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void inventoryAction(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = e.getCurrentItem(); // The item that was clicked
        Inventory inventory = e.getInventory(); // The inventory that was clicked in
        if(inventory.equals(plugin.worldInventory)){
            // O qualquier otro tipo xd
            if(clicked.getType().equals(Material.GRASS_BLOCK)){
                Player p = ((Player) e.getWhoClicked()).getPlayer();
                p.addAttachment(plugin, "multiverse.teleport.*", true);
                p.performCommand("mvtp " + clicked.getItemMeta().getDisplayName().replace(ChatColor.BOLD + "", ""));
                if(!p.hasPermission("op")) p.addAttachment(plugin, "multiverse.teleport.*", false);
            }
            e.setCancelled(true);
        }
    }
}
