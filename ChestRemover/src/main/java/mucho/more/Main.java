package mucho.more;


import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {
    private static Plugin plugin;
    private FileConfiguration chestConfig;
    private List<Location> locationsList;
    private HashMap<String, Channel> channels;
    @Override
    public void onEnable() {
        plugin = this;
        loadChestConfig();
        loadLocations();
        setupChannelMap();
        getServer().getPluginManager().registerEvents(new ChestOpenListener(), this);
        getServer().getPluginManager().registerEvents(new PacketReaderOnJoin(), this);

        System.out.println(ChatColor.GREEN+""+ChatColor.BOLD+"CHESTREMOVER LOADED!");
        // Plugin startup logic

    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
        plugin = null;
    }


    public static Plugin getInstance(){
        return plugin;
    }
    public void setupChannelMap(){
        this.channels = new HashMap<>();
    }
    public HashMap<String, Channel> getPlayerChannel(){
        return this.channels;
    }

    public List<String> getLocationListString(){
        return chestConfig.getStringList("data");
    }
    public void addLocation(Location loc){
        String location = locationSerializer(loc);
        List<String> list = getLocationListString();
        list.add(location);
        chestConfig.set("data",list);
        saveAsyncChestConfig();
    }
    public void removeLocation(Location loc){
        String location = locationSerializer(loc);
        List<String> list = getLocationListString();
        list.remove(location);
        chestConfig.set("data",list);
        saveAsyncChestConfig();
    }
    final String spacer = "]]";
    public String locationSerializer(Location location){
        return location.getWorld().getName()+spacer+(int)location.getBlockX()+spacer+(int)location.getBlockY()+spacer+(int)location.getBlockZ();
    }
    public Location deserializeLocation(String locString){
        String[] locStrings = locString.split(spacer);
        World world = Bukkit.getWorld(locStrings[0]);
        return new Location(world,Integer.parseInt(locStrings[1]),Integer.parseInt(locStrings[2]),Integer.parseInt(locStrings[3]));
    }
    private void loadLocations(){
        this.locationsList = new ArrayList<>();
        for(String s: getLocationListString()){
            Location loc = deserializeLocation(s);
            this.locationsList.add(loc);
        }
    }
    public List<Location> getLocationsList(){
        return this.locationsList;
    }
    final String rankingsPlayerPath = "plugins/ChestRemover";
    final String rankingsPlayerFile = "chestRemoverData.yml" ;
    public void loadChestConfig(){
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        File file = new File(rankingsPlayerPath, rankingsPlayerFile);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(ChatColor.RED+"Nie powiodło się tworzenie pliku rankingów graczy przy uruchamianiu serwera!");
            }
        }
        this.chestConfig = YamlConfiguration.loadConfiguration(file);
    }
    public void saveAsyncChestConfig(){
        File file = new File(rankingsPlayerPath, rankingsPlayerFile);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(ChatColor.RED+"Nie powiodło się tworzenie pliku rankingów graczy przy uruchamianiu serwera!");
            }
        }
        FileConfiguration fileConfiguration = this.chestConfig;
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getChestConfig(){
        return this.chestConfig;
    }

}
