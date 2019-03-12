package me.sandias98.fpqhtl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

public class JoinMessagesListener implements Listener {
    private final MiPlugin plugin;



    public JoinMessagesListener(MiPlugin p){
        this.plugin = p;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent e){
        e.setJoinMessage(ChatColor.BOLD + "" + ChatColor.AQUA + e.getPlayer().getName() + ChatColor.RESET + ChatColor.WHITE + " ha entrado en el server.");
        if(!(MiPlugin.boards.containsKey(e.getPlayer().getName()))) {
            Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
            board.registerNewObjective("test", "dummy", ChatColor.GOLD + "POSICIONES");
            MiPlugin.boards.put(e.getPlayer().getName(), board);
        }
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent e){
        e.setQuitMessage(ChatColor.BOLD + "" + ChatColor.RED + e.getPlayer().getName() + ChatColor.RESET + ChatColor.WHITE + " ha salido del servidor.");
    }

    @EventHandler
    public void alCambiarMundo(PlayerChangedWorldEvent e){
        // Conseguir spawn de antes y de ahora lol

    }
}
