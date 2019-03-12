package me.sandias98.fpqhtl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {
    private final MiPlugin plugin;

    public SettingsCommand(MiPlugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("settings")){
            plugin.loadDataBase();
            if(sender instanceof Player){
                Player p = (Player) sender;
                if(args[0].equalsIgnoreCase("globaltalk")){
                    if(args[1].equalsIgnoreCase("0")){
                        plugin.dataBase.set("player_settings."+p.getName()+".global_talk", false);
                        p.sendMessage("Desactivado globaltalk");
                        plugin.saveDataBase();
                    }
                    else if(args[1].equalsIgnoreCase("1")){
                        plugin.dataBase.set("player_settings."+p.getName()+".global_talk", true);
                        p.sendMessage("Activado globaltalk");
                        plugin.saveDataBase();
                    } else {
                        p.sendMessage("Uso: /settings globaltalk (0,1)");
                    }
                }
                if(args[0].equalsIgnoreCase("doublejump")){
                    if(args[1].equalsIgnoreCase("0")){
                        plugin.dataBase.set("world."+p.getWorld().getName()+".doublejump", false);
                        p.performCommand("mvm set allowflight true");
                        p.sendMessage("Desactivado doublejump para el mundo " + p.getWorld().getName());
                        plugin.saveDataBase();
                    }
                    else if(args[1].equalsIgnoreCase("1")){
                        plugin.dataBase.set("world."+p.getWorld().getName()+".doublejump", true);
                        p.performCommand("mvm set allowflight true");
                        p.sendMessage("Activado doublejump para el mundo " + p.getWorld().getName());
                        plugin.saveDataBase();
                    } else {
                        p.sendMessage("Uso: /settings doublejump (0,1)");
                    }
                }
                return true;
            }

        }
        return false;
    }
}