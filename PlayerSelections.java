package landissure.mc.lscpspacesuits;

import org.bukkit.Location;

public class PlayerSelections {
	public Location exitpoint;
	public Location entrypoint;
	public Location pin1;
	public Location pin2;
	
	public PlayerSelections() {
		
	}
	
	public void setExit(Location arg){
		exitpoint = arg;
	}
	
	public void setEntry(Location arg){
		entrypoint = arg;
	}
	
	public void setPin1(Location arg){
		pin1 = arg;
	}
	
	public void setPin2(Location arg){
		pin2 = arg;
	}
	


	
}
