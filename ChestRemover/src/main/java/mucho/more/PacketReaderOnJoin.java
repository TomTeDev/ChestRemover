package mucho.more;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PacketReaderOnJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        System.out.println(ChatColor.GREEN+"Player injected: "+e.getPlayer().getName());
        new PacketReader().inject(e.getPlayer());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        System.out.println(ChatColor.GREEN+"Player uninjected: "+e.getPlayer().getName());
        new PacketReader().uninject(e.getPlayer());
    }

}
