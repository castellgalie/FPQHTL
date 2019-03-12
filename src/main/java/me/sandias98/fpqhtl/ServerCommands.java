package me.sandias98.fpqhtl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerCommands implements CommandExecutor {
    private final MiPlugin plugin;

    public ServerCommands(MiPlugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    public void tpPlayerToLobby(Player p){

            p.addAttachment(plugin, "multiverse.teleport.*", true);
            p.performCommand("mvtp " + plugin.getConfig().getString("lobbyWorld"));
            if(!p.hasPermission("op")) p.addAttachment(plugin, "multiverse.teleport.*", false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Solo comandos dentro del juego!");
            return true;
        }
        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("sv")){
            if(args.length == 0){
                p.sendMessage("Comandos disponibles: /sv lobby, /sv tp <mundo>");
            } else {
                if(args[0].equalsIgnoreCase("lobby")){
                    tpPlayerToLobby(p);
                    return true;
                }
                if(args[0].equalsIgnoreCase("tp")){
                    if(args[1] == null){
                        p.sendMessage("Debes de poner un mundo!");
                    } else {
                        p.addAttachment(plugin, "multiverse.teleport.*", true);
                        p.performCommand("mvtp " + args[2]);
                        if(!p.hasPermission("op")) p.addAttachment(plugin, "multiverse.teleport.*", false);
                    }
                    return true;
                }
                p.sendMessage("Comandos disponibles: /sv lobby, /sv tp <mundo>");
                return true;
            }
            return true;
        }
        if(cmd.getName().equalsIgnoreCase("lobby")){
            tpPlayerToLobby(p);
        }
        return false;
    }
}