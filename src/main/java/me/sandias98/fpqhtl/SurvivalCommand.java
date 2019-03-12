package me.sandias98.fpqhtl;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SurvivalCommand implements CommandExecutor {
    private final MiPlugin plugin;

    public SurvivalCommand(MiPlugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // implementation exactly as before...t
        if(cmd.getName().equalsIgnoreCase("survival")) {
            plugin.loadDataBase();
            if(sender instanceof Player){
                Player p = (Player) sender;
                if(p.hasPermission("admin")){
                    if(args[0].equalsIgnoreCase("setworld")){
                        if(plugin.dataBase.getBoolean("world."+p.getWorld().getName()+".survival")){
                            plugin.dataBase.set("world."+p.getWorld().getName()+".survival", false);
                            p.sendMessage("Desasignado " + p.getWorld().getName() + " como mundo survival");
                        } else {
                            plugin.dataBase.set("world."+p.getWorld().getName()+".survival", true);
                            p.sendMessage("Asignado " + p.getWorld().getName() + " como mundo survival");
                        }
                        plugin.saveDataBase();
                        return true;
                    }
                }
                if(plugin.getConfig().getBoolean("world."+p.getWorld().getName()+".survival")) {
                    if (args[0].equalsIgnoreCase("status")) {
                        sender.sendMessage(ChatColor.DARK_RED + "------ ESTADO DEL SERVIDOR ------");
                        sender.sendMessage(ChatColor.YELLOW + "Mundo en el que estas: " + ChatColor.BOLD + ChatColor.AQUA + ((Player) sender).getWorld().getName());
                        sender.sendMessage(ChatColor.YELLOW + "Número de jugadores: " + ChatColor.AQUA + Bukkit.getServer().getOnlinePlayers().size());
                        sender.sendMessage(ChatColor.DARK_RED + "---------------------------------");
                        return true;

                    }
                    if (args[0].equalsIgnoreCase("home")) {
                        if (args.length < 2) {
                            return false;
                        } else {
                            if (args[1].equalsIgnoreCase("set")) {
                                plugin.dataBase.set("home." + p.getWorld().getName() + "." + p.getName() + ".x", p.getLocation().getBlockX());
                                plugin.dataBase.set("home." + p.getWorld().getName() + "." + p.getName() + ".y", p.getLocation().getBlockY());
                                plugin.dataBase.set("home." + p.getWorld().getName() + "." + p.getName() + ".z", p.getLocation().getBlockZ());
                                plugin.saveDataBase();
                                p.sendMessage("Establecida tu home del mundo " + p.getWorld().getName() + " en las coordenadas " + p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ());
                                return true;
                            }
                            if (args[1].equalsIgnoreCase("go")) {
                                int x = plugin.dataBase.getInt("home." + p.getWorld().getName() + "." + p.getName() + ".x");
                                int y = plugin.dataBase.getInt("home." + p.getWorld().getName() + "." + p.getName() + ".y");
                                int z = plugin.dataBase.getInt("home." + p.getWorld().getName() + "." + p.getName() + ".z");
                                Location dest = new Location(p.getWorld(), x, y, z);
                                p.teleport(dest);
                                p.sendMessage("Viajando a home");
                                return true;
                            }
                            return false;
                        }


                    }
                    if (args[0].equalsIgnoreCase("lastdeath")) {
                        if (plugin.savedLastDeath) {
                            sender.sendMessage(ChatColor.DARK_RED + "------ ULTIMA MUERTE ------");
                            sender.sendMessage(ChatColor.YELLOW + "Jugador: " + ChatColor.BOLD + ChatColor.AQUA + plugin.lastDeathplayer);
                            sender.sendMessage(ChatColor.YELLOW + "Mundo: " + ChatColor.BOLD + ChatColor.AQUA + plugin.lastDeathWorld);
                            sender.sendMessage(ChatColor.YELLOW + "Coordenadas: " + ChatColor.RED + "X: " + plugin.lastDeathX + ChatColor.GREEN + " Y: " + plugin.lastDeathY + ChatColor.AQUA + " Z: " + plugin.lastDeathZ);
                            sender.sendMessage(ChatColor.DARK_RED + "---------------------------------");
                        }
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "No puedes usar este comando porque no estas en un mundo supervivencia!");
                }


            }
        }
        return false;
    }
}
