package landissure.mc.lscpspacesuits;

import org.bukkit.Location;

public class PlanetHandler {

	private AABB portal;
	private Location entry;
	private Location exit;
	private boolean oxygen;
	
	public PlanetHandler(AABB bounds, Location entry, Location exit, boolean oxy){
		this.portal = bounds;
		this.entry = entry;
		this.exit = exit;
		this.oxygen = oxy;
	}
	
	public boolean inPortal(Location x){
		return portal.isWithin(x);
	}
	
	public Location getEntry(){
		return entry;
	}
	
	public Location getExit(){
		return exit;
	}
	
	public boolean isOxygenated(){
		return oxygen;
	}
	
	public AABB getPortal(){
		return portal;
	}
	
}
