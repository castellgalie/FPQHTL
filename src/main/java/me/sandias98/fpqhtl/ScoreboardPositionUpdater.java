package me.sandias98.fpqhtl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Set;

public class ScoreboardPositionUpdater extends BukkitRunnable {

    private final MiPlugin plugin;

    public ScoreboardPositionUpdater(MiPlugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    public void update(Player p){
        if(plugin.getConfig().getBoolean("world."+p.getWorld().getName()+".survival")){
            if(MiPlugin.boards.containsKey(p.getName())){
                Scoreboard board = MiPlugin.boards.get(p.getName());
                Objective o = board.getObjective("test");
                for(String player : board.getEntries())
                {
                    board.resetScores(player);
                }
                o.setDisplaySlot(DisplaySlot.SIDEBAR);

                //Your scores here...
                int scor = Bukkit.getOnlinePlayers().size() * 2;
                for(Player online : Bukkit.getOnlinePlayers()){
                    if(online.getWorld().getName() != p.getWorld().getName()) continue;
                    Score a;
                    if(online.getWorld().getName().endsWith("_nether")){
                        a = o.getScore(ChatColor.YELLOW + online.getName() + ": " + ChatColor.RED + "NETHER"); // Hacer nether
                    } else if (online.getWorld().getName().endsWith("_the_end")){
                        a = o.getScore(ChatColor.YELLOW + online.getName() + ": " + ChatColor.YELLOW + "THE END"); // Hacer nether
                    } else {
                        a = o.getScore(ChatColor.YELLOW + online.getName() + ": " + ChatColor.AQUA + "OVERWORLD"); // Hacer nether
                    }

                    a.setScore(scor); //Example.
                    scor--;
                    Score b = o.getScore(ChatColor.RED + "X " + ChatColor.WHITE + ": " + online.getLocation().getBlockX() +
                            ChatColor.GREEN + " Y " + ChatColor.WHITE + ": " + online.getLocation().getBlockY() +
                            ChatColor.AQUA + " Z " + ChatColor.WHITE + ": " + online.getLocation().getBlockZ());
                    b.setScore(scor);
                    scor--;
                }

                p.setScoreboard(board);
            }
        } else {
            // Arreglar!
            p.setScoreboard(plugin.emptyScoreboard);
        }
    }

    @Override
    public void run() {
        //methods
        for(Player online : Bukkit.getOnlinePlayers()){
            update(online);
        }
    }
}
