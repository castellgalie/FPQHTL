package me.sandias98.fpqhtl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class ChatMessagesListener implements Listener {
    private final MiPlugin plugin;

    public ChatMessagesListener(MiPlugin p){
        this.plugin = p;
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(e.getPlayer().getWorld().getName() == p.getWorld().getName()){
                p.sendMessage(ChatColor.WHITE + "[" + ChatColor.BOLD + ChatColor.GREEN + e.getPlayer().getDisplayName() + ChatColor.RESET + ChatColor.WHITE + "] " + e.getMessage());
            } else {
                if(plugin.dataBase.getBoolean("player_settings."+p.getName()+".global_talk")){
                    p.sendMessage(ChatColor.WHITE + "[" + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + e.getPlayer().getWorld().getName() + ChatColor.RESET + ChatColor.WHITE + "] " +
                            ChatColor.WHITE + "[" + ChatColor.BOLD + ChatColor.GREEN + e.getPlayer().getDisplayName() + ChatColor.RESET + ChatColor.WHITE + "] " + e.getMessage());
                }
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e){
        String muerte = e.getDeathMessage().replace(e.getEntity().getName(), "");
        e.setDeathMessage(ChatColor.RED + e.getEntity().getDisplayName() + ChatColor.WHITE + muerte);

        plugin.lastDeathX = e.getEntity().getPlayer().getLocation().getBlockX();
        plugin.lastDeathY = e.getEntity().getPlayer().getLocation().getBlockY();
        plugin.lastDeathZ = e.getEntity().getPlayer().getLocation().getBlockZ();
        plugin.lastDeathplayer = e.getEntity().getPlayer().getName();
        plugin.lastDeathWorld = e.getEntity().getWorld().getWorldType().name();
        plugin.savedLastDeath = true;

        Bukkit.getServer().broadcastMessage("["+ChatColor.AQUA+"INFO"+ChatColor.RESET+"] - Se ha guardado el lugar de muerte de " + ChatColor.RED + e.getEntity().getName());
    }
}
