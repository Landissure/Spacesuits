package landissure.mc.lscpspacesuits;


import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class LSCPspacesuits extends JavaPlugin implements Listener {

	public static HashMap<String, Location> spaceSpawnPoints = new HashMap<String, Location>();
	public static HashMap<String, Location> spaceEntryPoints = new HashMap<String, Location>();
	public static HashMap<String, Location> pin1s = new HashMap<String, Location>();
	public static HashMap<String, Location> pin2s = new HashMap<String, Location>();
	public static HashMap<String, PortalAABB> bounds = new HashMap<String, PortalAABB>();
	public static ArrayList<AABB> airlocks = new ArrayList<AABB>();
	Runnable armor = new Runnable(){

		public void run() {
			for(Player g : getServer().getOnlinePlayers()){
				ItemStack helm = g.getInventory().getHelmet();
				if(helm != null && ItemTools.compareIgnoreDurability(helm, new ItemStack(Material.IRON_HELMET)) && g.getWorld().getName().equals("space") ){
					helm.setDurability((short)  (helm.getDurability()+1));
					g.getInventory().setHelmet(helm);
					if(g.getInventory().getHelmet().getDurability() > Material.IRON_HELMET.getMaxDurability()){
						g.getInventory().setHelmet(null);
					}
				}

			}
		}

	};

	Runnable flyInSpace = new Runnable(){

		public void run() {
			for(Player g : getServer().getOnlinePlayers()){
				if(g.getWorld().getName().equals("space")){
					g.setAllowFlight(true);
				}
				else if(!(g.getGameMode() == GameMode.CREATIVE)){
					g.setAllowFlight(false);
				}
			}
		}

	};

	Runnable tpzones = new Runnable(){
		public void run(){
			for(Player g : getServer().getOnlinePlayers()){
				if(g.getLocation().getY() >= 256 && !g.getWorld().getName().equals("space")){
					g.teleport(spaceEntryPoints.get(g.getWorld().getName()));
					g.setFlying(true);
				}
				for(PortalAABB box : bounds.values()){
					if(box.isWithin(g.getLocation())){
						g.teleport(spaceSpawnPoints.get(box.worldTo));
					}
				}
			}
		}
	};

	Runnable spacehurt = new Runnable() {

		public void run() {
			for(Player p : getServer().getOnlinePlayers()){
				if(p.getInventory().getHelmet() == null && !p.getGameMode().equals(GameMode.CREATIVE) && p.getWorld().getName().equals("space") ){
					try{
						p.setHealth(p.getHealth()-1);
						if(p.getHealth() <= 1){
							p.setHealth(0);
						}
					}catch(IllegalArgumentException e){

					}

				}

			}
		}

	};

	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		for(World s : Bukkit.getWorlds()){
			if(!s.getName().equals("space")){
				spaceSpawnPoints.put(s.getName(),new Location(s, getConfig().getDouble(s.getName() + ".epoint.x"),getConfig().getDouble(s.getName() + ".epoint.y"),getConfig().getDouble(s.getName() + ".epoint.z")));
				spaceEntryPoints.put(s.getName(),new Location(Bukkit.getWorld("space"), getConfig().getDouble(s.getName() + ".spoint.x"),getConfig().getDouble(s.getName() + ".spoint.y"),getConfig().getDouble(s.getName() + ".spoint.z")));
				bounds.put(s.getName(),new PortalAABB(
						new Location(Bukkit.getWorld("space"), getConfig().getDouble(s.getName() + ".PortalAABB.p1.x"),getConfig().getDouble(s.getName() + ".PortalAABB.p1.y"), getConfig().getDouble(s.getName() + ".PortalAABB.p1.z")),
						new Location(Bukkit.getWorld("space"), getConfig().getDouble(s.getName() + ".PortalAABB.p2.x"),getConfig().getDouble(s.getName() + ".PortalAABB.p2.y"), getConfig().getDouble(s.getName() + ".PortalAABB.p2.z")),
						s.getName()));
			}
		}
		CreationExecutor exec = new CreationExecutor(this);
		getCommand("pin1").setExecutor(exec);
		getCommand("pin2").setExecutor(exec);
		getCommand("setenterpoint").setExecutor(exec);
		getCommand("setexitpoint").setExecutor(exec);
		getCommand("setzone").setExecutor(exec);
		BukkitScheduler sch = getServer().getScheduler();
		sch.scheduleSyncRepeatingTask(this, armor, 0L, 20L);
		sch.scheduleSyncRepeatingTask(this, spacehurt, 0L, 5L);
		sch.scheduleSyncRepeatingTask(this, tpzones, 0L, 5L);
		sch.scheduleSyncRepeatingTask(this, flyInSpace, 0L, 2L);

	}

	public void onDisable(){
		loadIntoVars();
	}



	public void loadIntoVars(){
		for(String s : spaceSpawnPoints.keySet()){
			getConfig().set(s + ".epoint.x", spaceEntryPoints.get(s).getX());
			getConfig().set(s + ".epoint.y", spaceEntryPoints.get(s).getY());
			getConfig().set(s + ".epoint.z", spaceEntryPoints.get(s).getZ());
			getConfig().set(s + ".spoint.x", spaceSpawnPoints.get(s).getX());
			getConfig().set(s + ".spoint.y", spaceSpawnPoints.get(s).getY());
			getConfig().set(s + ".spoint.z", spaceSpawnPoints.get(s).getZ());
			getConfig().set(s + ".PortalAABB.worldto", bounds.get(s).worldTo);
			getConfig().set(s + ".PortalAABB.p1.x", bounds.get(s).getPin1().getX());
			getConfig().set(s + ".PortalAABB.p1.y", bounds.get(s).getPin1().getY());
			getConfig().set(s + ".PortalAABB.p1.z", bounds.get(s).getPin1().getZ());
			getConfig().set(s + ".PortalAABB.p2.x", bounds.get(s).getPin2().getX());
			getConfig().set(s + ".PortalAABB.p2.y", bounds.get(s).getPin2().getY());
			getConfig().set(s + ".PortalAABB.p2.z", bounds.get(s).getPin2().getZ());
		}
		saveConfig();
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		if(e.getBlock().getType().equals(Material.SPONGE) && e.getBlock().getWorld().getName().equals("space")){
			airlocks.add(new AABB(new Location(Bukkit.getWorld("space"),e.getBlock().getLocation().getX()-5,e.getBlock().getLocation().getY()-5,e.getBlock().getLocation().getZ()-5),
					new Location(Bukkit.getWorld("space"),e.getBlock().getLocation().getX()+5,e.getBlock().getLocation().getY()+5,e.getBlock().getLocation().getZ()+5)));
		}
	}
}
