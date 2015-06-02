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
				case 0:
					if(LSCPspacesuits.playerSelections.get(x.getName()) == null){
						LSCPspacesuits.playerSelections.put(x.getName(), new PlayerSelections());
					}
					LSCPspacesuits.playerSelections.get(x.getName()).setEntry(x.getLocation());
					x.sendMessage("Entry point set.");
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
					if(LSCPspacesuits.playerSelections.get(x.getName()) == null){
						LSCPspacesuits.playerSelections.put(x.getName(), new PlayerSelections());
					}
					LSCPspacesuits.playerSelections.get(x.getName()).setPin1(x.getLocation());
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
					if(LSCPspacesuits.playerSelections.get(x.getName()) == null){
						LSCPspacesuits.playerSelections.put(x.getName(), new PlayerSelections());
					}
					LSCPspacesuits.playerSelections.get(x.getName()).setPin2(x.getLocation());
					x.sendMessage("Pin 2 set.");
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
				case 0:
					if(LSCPspacesuits.playerSelections.get(x.getName()) == null){
						LSCPspacesuits.playerSelections.put(x.getName(), new PlayerSelections());
					}
					x.sendMessage("Exit point set.");
					LSCPspacesuits.playerSelections.get(x.getName()).setExit(x.getLocation());
					break;
				default:
					return false;
				}
			}
		}

		else if(cmd.getName().equalsIgnoreCase("createplanet")){
			if(sender instanceof Player){
				Player x = (Player) sender;
				switch(args.length){
				case 2:
					LSCPspacesuits.planets.put(args[0], new PlanetHandler(
							new AABB(
									LSCPspacesuits.playerSelections.get(x.getName()).pin1,
									LSCPspacesuits.playerSelections.get(x.getName()).pin2 
									),
							LSCPspacesuits.playerSelections.get(x.getName()).entrypoint,
							LSCPspacesuits.playerSelections.get(x.getName()).exitpoint,
							Boolean.parseBoolean(args[1])));
					x.sendMessage("Planet created.");
					break;
				default:
					return false;
				}
			}
		}
		return true;

		
	}

}
