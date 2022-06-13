package mucho.more;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.List;

public class PacketReader {
    Main plugin = Main.getPlugin(Main.class);
    public void addChannel(String name, Channel channel){
        plugin.getPlayerChannel().put(name,channel);
    }
    public void inject(Player player){
        CraftPlayer craftPlayer = (CraftPlayer) player;
        Channel channel = craftPlayer.getHandle().b.a.m;
        if(channel.pipeline().get("PacketPlayInUseEntity")==null){
            channel.pipeline().addAfter("decoder", "PacketPlayInUseEntity", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
                @Override
                protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) throws Exception{
                    arg.add(packet);
                    readPacket(player,packet);
                }
            });
        }


        addChannel(player.getName(),channel);
    }
    public void uninject(Player player){
        plugin.getPlayerChannel().remove(player.getName());
    }
    public void readPacket (Player player, Packet<?> packet) {
        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            int id = (int) getValue(packet, "a");
            removeEntity(player,id);
        }
    }
    private void removeEntity(Player player, int id){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {

                List<Entity> list = player.getWorld().getEntities();
                for(Entity ent:list){
                    int idEnt = ent.getEntityId();
                    if(id == idEnt){
                        if(ent instanceof StorageMinecart){
                            StorageMinecart  minecraft = (StorageMinecart) ent;
                            int size = minecraft.getInventory().getSize();
                            for(int x = 0;x<size;x++){
                                minecraft.getInventory().setItem(x,new ItemStack(Material.AIR));
                            }
                            player.closeInventory();
                            minecraft.remove();
                        }
                        return;
                    }
                }
            }
        },1);
    }
    private Object getValue(Object instance, String name){
        if(name.equals("action")){
            name = "b";
        }
        Object result  = null;
        try{

            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(instance);
            field.setAccessible(false);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
