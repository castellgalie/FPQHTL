package me.sandias98.fpqhtl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Every5Messages extends BukkitRunnable {
    private final MiPlugin plugin;

    public Every5Messages(MiPlugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public void run() {
        //methods
        Bukkit.getServer().broadcastMessage(plugin.getConfig().getString("messaje"));
    }
}
