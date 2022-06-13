package mucho.more;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class  ConfigManager {
        public static Main plugin = Main.getPlugin(Main.class);
        public FileConfiguration cfg;

        public ConfigManager() {
            this.cfg = plugin.getChestConfig();
        }
        public final static String rankingsPath = "rankings";
        public boolean setInt(int value,String path){
            cfg.set(path,value);
            return true;
        }
        public int getInt(String path,int value){
            return (cfg.contains(path)&&(cfg.get(path)!=null))?cfg.getInt(path): value;
        }
        public boolean setString(String value,String path){
            cfg.set(path,value);
            return true;
        }
        public String getString(String path,String reserveValue){
            return (cfg.contains(path)&&(cfg.get(path)!=null))?cfg.getString(path):reserveValue;
        }
    }
