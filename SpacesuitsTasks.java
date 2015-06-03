package landissure.mc.lscpspacesuits;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class SpacesuitsTasks {

	public static Runnable armor = new Runnable(){
		public void run() {
			for(Player g : Bukkit.getServer().getOnlinePlayers()){
				ItemStack helm = g.getInventory().getHelmet();
				if(helm != null && ItemTools.compareIgnoreDurability(helm, new ItemStack(Material.IRON_HELMET)) && !oxySpace(g) ){
					helm.setDurability((short)  (helm.getDurability()+1));
					g.getInventory().setHelmet(helm);
					if(g.getInventory().getHelmet().getDurability() > Material.IRON_HELMET.getMaxDurability()){
						g.getInventory().setHelmet(null);
					}
				}
			}
		}
	};

	private static boolean oxySpace(Player p){
		if(p.getWorld().getName().equals("space") && !withinAirlock(p)){
			return false;
		}
		else{
			return 	LSCPspacesuits.planets.get(p.getWorld().getName()).isOxygenated() || withinAirlock(p);
		}
	}

	private static boolean withinAirlock(Player p){
		for(AABB lock : LSCPspacesuits.airlocks){
			if(lock.isWithin(p.getLocation())){
				return true;
			}
		}
		return false;
	}
	
	public static Runnable checkForAirlock = new Runnable(){
		public void run(){
			for(int i = 0; i < LSCPspacesuits.airlocks.size(); i++){
				if(!LSCPspacesuits.airlocks.get(i).isWithin(Material.SPONGE)){
					LSCPspacesuits.airlocks.remove(i);
				}
			}
		}
	};

	public static Runnable planetBoundaryActor = new Runnable() {
		public void run(){
			for(Player p : Bukkit.getServer().getWorld("space").getPlayers()){
				for(PlanetHandler x : LSCPspacesuits.planets.values()){
					if(x.inPortal(p.getLocation())){
						p.teleport(x.getEntry());
					}
				}
			}
		}
	};


	public static Runnable flyInSpace = new Runnable(){

		public void run() {
			for(Player g : Bukkit.getServer().getOnlinePlayers()){
				if(g.getWorld().getName().equals("space")){
					g.setAllowFlight(true);
				}
				else if(!(g.getGameMode() == GameMode.CREATIVE)){
					g.setAllowFlight(false);
				}
			}
		}
	};

	public static Runnable spacehurt = new Runnable() {

		public void run() {
			for(Player p : Bukkit.getServer().getOnlinePlayers()){
				if(p.getInventory().getHelmet() == null && !p.getGameMode().equals(GameMode.CREATIVE) &&  !oxySpace(p)){
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

	public static Runnable exitAtmosphereDetector = new Runnable(){
		public void run(){
			for(Player p : Bukkit.getServer().getOnlinePlayers()){
				if(!p.getWorld().getName().equals("space") && p.getLocation().getY() > 256){
					p.teleport(LSCPspacesuits.planets.get(p.getWorld().getName()).getExit());
				}
			}
		}
	};
}


