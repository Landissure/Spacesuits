package landissure.mc.lscpspacesuits;

import org.bukkit.Location;

public class PortalAABB extends AABB {
	
	public String worldTo;

	public PortalAABB(Location a, Location b, String worldTo){
		super(a,b);
		this.worldTo = worldTo;
	}
}
