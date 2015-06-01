package landissure.mc.lscpspacesuits;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CreationExecutor implements CommandExecutor {
	
	private Plugin plug;

	public CreationExecutor(Plugin t){
		plug = t;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("setenterpoint")){
			if(sender instanceof Player){
				Player x = (Player) sender;
				switch(args.length){
				case 1:
					LSCPspacesuits.spaceSpawnPoints.put(args[0], x.getLocation());
					break;
				default:
					return false;
				}

			}
		}
		else if(cmd.getName().equalsIgnoreCase("pin1")){
			if(sender instanceof Player){
				Player x = (Player) sender;

				switch(args.length){
				case 0:
					LSCPspacesuits.pin1s.put(x.getName(), x.getLocation());
					x.sendMessage("Pin 1 set.");
					break;
				default:
					return false;
				}

			}
		}
		else if(cmd.getName().equalsIgnoreCase("pin2")){
			if(sender instanceof Player){
				Player x = (Player) sender;
				switch(args.length){
				case 0:
					LSCPspacesuits.pin2s.put(x.getName(), x.getLocation());
					x.sendMessage("Pin 2 set.");
					break;
				default:
					return false;
				}

			}
		}
		else if(cmd.getName().equalsIgnoreCase("setzone")){
			if(sender instanceof Player){
				Player x = (Player) sender;
				switch(args.length){
				case 1:
					x.sendMessage("Zone for " + args[0] + " set.");
					LSCPspacesuits.bounds.put(args[0],new PortalAABB(LSCPspacesuits.pin1s.get(x.getName()), LSCPspacesuits.pin2s.get(x.getName()), args[0]));
					break;
				default:
					return false;
				}

			}
		}

		else if(cmd.getName().equalsIgnoreCase("setexitpoint")){
			if(sender instanceof Player){
				Player x = (Player) sender;
				switch(args.length){
				case 1:
					x.sendMessage("Exit point set.");
					LSCPspacesuits.spaceEntryPoints.put(args[0], x.getLocation());
					break;
				default:
					return false;
				}

			}
		}

		return true;

	}
	
}
