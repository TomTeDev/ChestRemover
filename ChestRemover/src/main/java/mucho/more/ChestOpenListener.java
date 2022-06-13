package mucho.more;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;

public class ChestOpenListener extends ConfigManager implements Listener {



    @EventHandler
    public void onDamageEvent(VehicleDestroyEvent e){
        if(e.getVehicle() instanceof StorageMinecart){
            StorageMinecart minecraft = (StorageMinecart) e.getVehicle();
                int size = minecraft.getInventory().getSize();
                for(int x = 0;x<size;x++){
                    minecraft.getInventory().setItem(x,new ItemStack(Material.AIR));
                }
                e.setCancelled(true);
                e.getVehicle().remove();
        }
    }


    @EventHandler
    public void onChestOpen(PlayerInteractEvent e){
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        Block b = e.getClickedBlock();
        if(b==null)return;
        Material m = b.getType();
        if(m!=Material.CHEST&&m!=Material.TRAPPED_CHEST)return;
        Location loc = b.getLocation();
        if(isThisChestPlacedByPlayer(loc))return;
        e.setCancelled(true);
        BlockState state = b.getState();
        state.setType(Material.AIR);
        state.update(true);
        addChestLocation(loc);
        setChest(b,m);
    }
    private Location[] getBlocksAround(Location location){
        Location loc = new Location(location.getWorld(),location.getBlockX(),location.getBlockY(),location.getBlockZ());
        Location[] locs = new Location[4];
        locs[0] = loc.clone().add(-1,0,0);
        locs[1] = loc.clone().add(1,0,0);
        locs[2] = loc.clone().add(0,0,-1);
        locs[3] = loc.clone().add(0,0,1);
        return locs;
    }
    @EventHandler
    public void onChestPlace(BlockPlaceEvent e){
        Material m = e.getBlockPlaced().getType();
        if(m!=Material.CHEST&&m!=Material.TRAPPED_CHEST)return;
        addChestLocation(e.getBlockPlaced().getLocation());
        for(Location l :getBlocksAround(e.getBlockPlaced().getLocation())){
            Block block = e.getBlockPlaced().getWorld().getBlockAt(l);
            Material material = block.getType();
            if(material==Material.CHEST||material==Material.TRAPPED_CHEST){
                if(!isThisChestPlacedByPlayer(l)){
                    BlockState state = block.getState();
                    state.setType(Material.AIR);
                    state.update(true);
                    addChestLocation(l);
                    setChest(block,material);
                }
            }
        }
    }
    private void setChest(Block block,Material material){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                BlockState state = block.getState();
                state.setType(material);
                state.update(true);
            }
        },1);
    }
    @EventHandler
    public  void onBlockBreak(BlockBreakEvent e){
        Material m = e.getBlock().getType();;
        if(m!=Material.CHEST&&m!=Material.TRAPPED_CHEST)return;
        Location loc = e.getBlock().getLocation();
        if(isThisChestPlacedByPlayer(loc)){
            removeChestLocation(e.getBlock().getLocation());
            return;
        }
        e.setCancelled(true);
        Block b = e.getBlock();
        BlockState state = b.getState();
        state.setType(Material.AIR);
        state.update(true);
        b.getWorld().dropItemNaturally(loc,new ItemStack(m));
    }
 /*   private boolean isOnWordGuardLandProtection(Player p,Location loc){
        LocalPlayer lp = WorldGuardPlugin.inst().wrapPlayer(p);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        Flag<?> flag = WorldGuard.getInstance().getFlagRegistry().get("BLOCK_PLACE");
        StateFlag stateFlag = (StateFlag) flag;
        StateFlag[] list = new StateFlag[2];
        list[0]=(Flags.BUILD);
        list[1]=(Flags.BLOCK_PLACE);
        if (query.testBuild(BukkitAdapter.adapt(loc),lp,Flags.BUILD)) {
            return true;
        }
        return false;
    }*/
    Main plugin = Main.getPlugin(Main.class);
    private void addChestLocation(Location location){
        Location loc = new Location(location.getWorld(),location.getBlockX(),location.getBlockY(),location.getBlockZ());
        plugin.getLocationsList().add(loc);
        plugin.addLocation(loc);
    }
    private void removeChestLocation(Location location){
        Location loc = new Location(location.getWorld(),location.getBlockX(),location.getBlockY(),location.getBlockZ());
        plugin.getLocationsList().remove(loc);
        plugin.removeLocation(loc);
    }
    private boolean isThisChestPlacedByPlayer(Location loc){
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        for(Location l:plugin.getLocationsList()){
            if(l.getBlockX()==x&&
            l.getBlockY()==y&&
            l.getBlockZ()==z){
                return true;
            }
        }
        return false;
    }
}
