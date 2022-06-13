/*
package mucho.more;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

public class ChunkLoadListener extends ConfigManager implements Listener {
    private final String worldName = "faction";
    final String basePath = "data";
    final String removedminecartPath = basePath+".removedminecart";
    final String removedchestPath = basePath+".removedchest";
    final String removedtrappedchestPath = basePath+".removedtrappedchest";
    final String removedenderchestPath = basePath+".removedenderchest";
    IBlockData chest = net.minecraft.world.level.block.Blocks.bX.getBlockData();
    IBlockData trappedchest = net.minecraft.world.level.block.Blocks.fE.getBlockData();
    IBlockData enderchest = net.minecraft.world.level.block.Blocks.ex.getBlockData();

    BlockData datachest = Material.CHEST.createBlockData();
    BlockData datatrappedChest = Material.TRAPPED_CHEST.createBlockData();
    BlockData dataenderchest = Material.ENDER_CHEST.createBlockData();
    static boolean value = true;
    @EventHandler
    public void onChunkLoad(ChunkPopulateEvent e){
            Bukkit.broadcastMessage("Its new chunk!");
            Chunk chunk = e.getChunk();
            if(!chunk.getWorld().getName().equalsIgnoreCase("faction")){
                return;
            }
            World world = chunk.getWorld();
            int x = chunk.getX();
            int z = chunk.getZ();
            Entity[] nearByEnt = chunk.getEntities();
            for(Entity ent: nearByEnt){
                if(ent instanceof Minecart){
                    int minecartcount = getInt(removedminecartPath,0)+1;
                    setInt(minecartcount,removedminecartPath);
                    Bukkit.broadcastMessage(ChatColor.YELLOW+"Minecart removed, current amount: "+minecartcount);
                    ent.remove();
                }
            }
                for(int xb =x*16;xb<=x*16+16;xb++){
                    for(int zb =z*16;zb<=z*16+16;zb++){
                        for(int yb = 0;yb<190;yb++){
                           Material type =  e.getChunk().getBlock(xb,yb,zb).getType();
                           if(type==Material.CHEST||type==Material.TRAPPED_CHEST||type==Material.ENDER_CHEST){
                               Bukkit.broadcastMessage("Chunk contains chest!");
                               destroyBlockNaturally(world,xb,yb,zb);
                           }
                        }
                    }
                }


    }

    public void destroyBlockNaturally(World world, int x, int y, int z){
        net.minecraft.world.level.World nmsWorld = ((CraftWorld) world).getHandle();
        net.minecraft.core.BlockPosition bp = new BlockPosition(x, y, z);

        IBlockData air = net.minecraft.world.level.block.Block.getByCombinedId(0);
        if(nmsWorld.getType(bp).equals(chest)){
            int removedchest = getInt(removedchestPath,0)+1;
            setInt(removedchest,removedchestPath);
            Bukkit.broadcastMessage(ChatColor.AQUA+"Chest removed, current amount: "+removedchest);
            nmsWorld.setTypeAndData(bp, air, 2);
        }else
        if(nmsWorld.getType(bp).equals(trappedchest)){
            int removedchest = getInt(removedtrappedchestPath,0)+1;
            setInt(removedchest,removedtrappedchestPath);
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"TrappedChest removed, current amount: "+removedchest);
            nmsWorld.setTypeAndData(bp, air, 2);
        }else
        if(nmsWorld.getType(bp).equals(enderchest)){
            int removedchest = getInt(removedenderchestPath,0)+1;
            setInt(removedchest,removedenderchestPath);
            Bukkit.broadcastMessage(ChatColor.GREEN+"Enderchest removed, current amount: "+removedchest);
            nmsWorld.setTypeAndData(bp, air, 2);
        }else{}
    }

*/
/*    public void destroyBlockInBadWay(World world,int xb,int yb, int zb){
        Location loc = new Location(world,xb,yb,zb);
        Block b = world.getBlockAt(loc);
        BlockState state = b.getState();
        Material type = b.getType();
        if(type==Material.TRAPPED_CHEST){
            int removedchest = getInt(removedchestPath,0)+1;
            setInt(removedchest,removedchestPath);
            Bukkit.broadcastMessage(ChatColor.AQUA+"Chest removed, current amount: "+removedchest);
            state.setType(Material.AIR);
            state.update(true);
            return;
        }
        if(type==Material.CHEST){
            int removedchest = getInt(removedtrappedchestPath,0)+1;
            setInt(removedchest,removedtrappedchestPath);
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"TrappedChest removed, current amount: "+removedchest);
            state.setType(Material.AIR);
            state.update(true);
            return;
        }
        if(type==Material.ENDER_CHEST){
            int removedchest = getInt(removedenderchestPath,0)+1;
            setInt(removedchest,removedenderchestPath);
            Bukkit.broadcastMessage(ChatColor.GREEN+"Enderchest removed, current amount: "+removedchest);
            state.setType(Material.AIR);
            state.update(true);
            return;
        }
    }*//*

}
*/
