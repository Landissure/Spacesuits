package landissure.mc.lscpspacesuits;

import org.bukkit.Location;

public class AABB {
	private Location pin1;
	private Location pin2;

	public AABB(Location o, Location t){
		pin1 = o;
		pin2 = t;
	}
	
	public boolean isWithin(Location f){
		return (f.getX() > pin1.getX() && f.getX() < pin2.getX()) &&
				(f.getY() > pin1.getY() && f.getY() < pin2.getY()) &&
				(f.getZ() > pin1.getZ() && f.getZ() < pin2.getZ()) &&
				f.getWorld().getName().equals(pin1.getWorld().getName());
	}
	
	public Location getPin1(){
		return pin1;
	}
	
	public Location getPin2(){
		return pin2;
	}
}
