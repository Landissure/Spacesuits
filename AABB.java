package landissure.mc.lscpspacesuits;

import org.bukkit.Location;
import org.bukkit.Material;

public class AABB {
	private Location pin1;
	private Location pin2;

	public AABB(Location o, Location t){
		pin1 = o.getBlock().getLocation();
		pin2 = t.getBlock().getLocation();
	}
	
	public boolean isWithin(Location f){
		return (f.getX() > pin1.getX() && f.getX() < pin2.getX()) &&
				(f.getY() > pin1.getY() && f.getY() < pin2.getY()) &&
				(f.getZ() > pin1.getZ() && f.getZ() < pin2.getZ()) &&
				f.getWorld().getName().equals(pin1.getWorld().getName());
	}
	
	public boolean isWithin(Material m){
		Location d = new Location(pin1.getWorld(),pin1.getX()+5,pin1.getY()+5,pin1.getZ()+5);
		return d.getBlock().getType().equals(m);
	}
	
	public Location getPin1(){
		return pin1;
	}
	
	public Location getPin2(){
		return pin2;
	}
}
