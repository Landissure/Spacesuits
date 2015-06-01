package landissure.mc.lscpspacesuits;

import org.bukkit.inventory.ItemStack;

public class ItemTools {

	public static boolean compareIgnoreDurability(ItemStack i, ItemStack j){
		ItemStack g = new ItemStack(i.getType());
		ItemStack h = new ItemStack(j.getType());
		if(h.isSimilar(g)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
}
