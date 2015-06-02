package landissure.mc.lscpspacesuits;


import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class LSCPspacesuits extends JavaPlugin implements Listener {

	public static HashMap<String, PlayerSelections> playerSelections = new HashMap<String, PlayerSelections>();
	public static HashMap<String, PlanetHandler> planets = new HashMap<String, PlanetHandler>();
	public static ArrayList<AABB> airlocks = new ArrayList<AABB>();

	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		for(World s : Bukkit.getWorlds()){
			planets.put(s.getName(),new PlanetHandler());
			planets.get(s.getName()).setEntry(new Location(Bukkit.getWorld(getConfig().get("planets." + s.getName() + ".world"),
				getConfig().get("planets." + s.getName() + ".entry.x"),
				getConfig().get("planets." + s.getName() + ".entry.y"),
				getConfig().get("planets." + s.getName() + ".entry.z"));
			planets.get(s.getName()).setExit(new Location(Bukkit.getWorld(getConfig().get("planets." + s.getName() + ".world"),
				getConfig().get("planets." + s.getName() + ".exit.x"),
				getConfig().get("planets." + s.getName() + ".exit.y"),
				getConfig().get("planets." + s.getName() + ".exit.z"));
			
		}
		CreationExecutor exec = new CreationExecutor(this);
		getCommand("pin1").setExecutor(exec);
		getCommand("pin2").setExecutor(exec);
		getCommand("setenterpoint").setExecutor(exec);
		getCommand("setexitpoint").setExecutor(exec);
		getCommand("createplanet").setExecutor(exec);
		BukkitScheduler sch = getServer().getScheduler();
		sch.scheduleSyncRepeatingTask(this, SpacesuitsTasks.exitAtmosphereDetector, 0L, 5L);
		sch.scheduleSyncRepeatingTask(this, SpacesuitsTasks.flyInSpace, 0L, 5L);
		sch.scheduleSyncRepeatingTask(this, SpacesuitsTasks.planetBoundaryActor, 0L, 5L);
		sch.scheduleSyncRepeatingTask(this, SpacesuitsTasks.spacehurt, 0L, 5L);
		sch.scheduleSyncRepeatingTask(this, SpacesuitsTasks.armor, 0L, 10L);

	}

	public void onDisable(){
		for(String x : planets.keySet()){
			getConfig().set("planets." + x + ".world",planets.get(x).getEntry().getWorld().getName());
			getConfig().set("planets." + x + ".oxygenated",planets.get(x).isOxygenated());
			getConfig().set("planets." + x + ".exit.x",planets.get(x).getExit().getX());
			getConfig().set("planets." + x + ".exit.y",planets.get(x).getExit().getY());
			getConfig().set("planets." + x + ".exit.z",planets.get(x).getExit().getZ());
			getConfig().set("planets." + x + ".entry.x",planets.get(x).getEntry().getX());
			getConfig().set("planets." + x + ".entry.y",planets.get(x).getEntry().getY());
			getConfig().set("planets." + x + ".entry.z",planets.get(x).getEntry().getZ());
			getConfig().set("planets." + x + ".exit.x",planets.get(x).getExit().getX());
			getConfig().set("planets." + x + ".exit.y",planets.get(x).getExit().getY());
			getConfig().set("planets." + x + ".portal.min.x",planets.get(x).getPortal().getPin1().getX());
			getConfig().set("planets." + x + ".portal.min.y",planets.get(x).getPortal().getPin1().getY());
			getConfig().set("planets." + x + ".portal.min.z",planets.get(x).getPortal().getPin1().getZ());
			getConfig().set("planets." + x + ".portal.max.x",planets.get(x).getPortal().getPin2().getX());
			getConfig().set("planets." + x + ".portal.max.y",planets.get(x).getPortal().getPin2().getY());
			getConfig().set("planets." + x + ".portal.max.z",planets.get(x).getPortal().getPin2().getZ());
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
