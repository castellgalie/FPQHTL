package me.sandias98.fpqhtl;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public final class MiPlugin extends JavaPlugin {
    // Variables
    public int lastDeathX = 0;
    public int lastDeathY = 0;
    public int lastDeathZ = 0;
    public boolean savedLastDeath = false;
    public String lastDeathplayer = "NULL";
    public String lastDeathWorld;

    public static Scoreboard emptyScoreboard;

    public static MiPlugin instance;

    public static HashMap<String, Scoreboard> boards = new HashMap<String, Scoreboard>();

    public static List<World> worlds;

    // -- Inventarios

    public static Inventory worldInventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "World menu");

    public static void createDisplay(Material material, Inventory inv, int Slot, String name, String[] lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> Lore = new ArrayList<String>();
        for(String lor : lore){
            Lore.add(lor);
        }
        meta.setLore(Lore);
        item.setItemMeta(meta);

        inv.setItem(Slot, item);
    }

    public void initializeMenus(){
        createDisplay(Material.SIGN, worldInventory,4, ChatColor.GREEN + "INFO", new String[]{"En este menu puedes elegir a", "que mundo ir.", "", "Crear mundos: PROXIMAMENTE..."});
        int n = 9;
        for(World w : Bukkit.getWorlds()){
            if(!(w.getName().endsWith("_nether") || w.getName().endsWith("_the_end"))){
                createDisplay(Material.GRASS_BLOCK, worldInventory,n, ChatColor.BOLD + w.getName(), new String[]{"Haz clic para ir al mundo"});
                n++;
            }
        }
    }
    // ---

    public File dataBaseFile = new File(this.getDataFolder()+"/database.yml");
    public FileConfiguration dataBase = YamlConfiguration.loadConfiguration(dataBaseFile);

    public void saveDataBase(){
        try {
            dataBase.save(dataBaseFile);
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void loadDataBase(){
        try{
            dataBase.load(dataBaseFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        loadDataBase();
        saveDataBase();
        getConfig().options().copyDefaults(true);
        saveConfig();
        instance = this;
        // This will throw a NullPointerException if you don't have the command defined in your plugin.yml file!

        // Commands
        this.getCommand("survival").setExecutor(new SurvivalCommand(this));
        this.getCommand("settings").setExecutor(new SettingsCommand(this));
        this.getCommand("sv").setExecutor(new ServerCommands(this));
        this.getCommand("lobby").setExecutor(new ServerCommands(this));
        this.getCommand("worlds").setExecutor(new WorldsCommand(this));


        // Listeners
        Bukkit.getServer().getPluginManager().registerEvents(new JoinMessagesListener(this),this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatMessagesListener(this),this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);

        // Actualizar Scoreboard
        emptyScoreboard = getServer().getScoreboardManager().getNewScoreboard();
        ScoreboardPositionUpdater task = new ScoreboardPositionUpdater(this);
        Every5Messages task5 = new Every5Messages(this);
        task.runTaskTimerAsynchronously(this, 0, 20); //Wait 0 ticks before executing for the first time. Wait 1 tick between each consecutive execution
        task5.runTaskTimerAsynchronously(this, 0, getConfig().getInt("messaje_time")); //Wait 0 ticks before executing for the first time. Wait 1 tick between each consecutive execution

        initializeMenus();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
        saveDataBase();
    }

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
