package net.worldofclucky.teleport;

import net.worldofclucky.teleport.utilities.Storage;

import org.raftpowered.ChatColor;
import org.raftpowered.RaftUtils;
import org.raftpowered.api.Command;
import org.raftpowered.api.CommandUtils;
import org.raftpowered.CommandSender;
import org.raftpowered.entity.Player;
import org.raftpowered.exception.CommandException;

public class Homes {
	@Command(description = "Teleports a player to their home", name = "home", usage = "/home [name [player]] ")
	public void home(CommandSender sender, String[] args) {
		
		if (!sender.isPlayer()) {
			sender.sendMessage("This command can only be issued by a player!");
			return;
		}
		
		Player player = sender.asPlayer();
		
		if (!sender.hasPermission("teleport.home", false)) throw CommandException.PERMISSION_DENIED;

		switch (args.length) {
			case 0: // /home
				if (!sender.hasPermission("teleport.home.self", false)) throw CommandException.PERMISSION_DENIED;
				sender.sendMessage(ChatColor.GREEN + "Teleporting you to your default home.");
				break;
				
			case 1: // /home <name>
				if (!sender.hasPermission("teleport.home.self", false)) throw CommandException.PERMISSION_DENIED;
				sender.sendMessage(ChatColor.GREEN + "Teleporting you to your home named " + args[0] + ".");
				break;
				
			case 2:// /home <name> <player>
				if (!sender.hasPermission("teleport.home.other", true)) throw CommandException.PERMISSION_DENIED;
				sender.sendMessage(ChatColor.GREEN + "Teleporting you to the home named " + args[0] + " owned by " + args[1] );
				break;
				
			default:
				sender.sendMessage(ChatColor.RED + "Invalid amount of parameters!");
				break;
		}
	}
	
	@Command(description = "Creates a home", name = "sethome", usage = "/sethome [name [player]]")
	public void setHome(CommandSender sender, String[] args) {
		
		if (!sender.isPlayer()) {
			sender.sendMessage("This command can only be issued by a player!");
			return;
		}
		
		Player player = sender.asPlayer();
		
		if (!sender.hasPermission("teleport.sethome", false)) throw CommandException.PERMISSION_DENIED;
		
		switch (args.length) {
			case 0: //Player sets their main home
				if (!sender.hasPermission("teleport.sethome.self", false)) throw CommandException.PERMISSION_DENIED;
				Storage.setHomeToPlayer(player, "main");
				sender.sendMessage(ChatColor.GREEN + "Main home set to current location.");
				break;
				
			case 1: //Player sets their named home
				if (!sender.hasPermission("teleport.sethome.self", false)) throw CommandException.PERMISSION_DENIED;
				Storage.setHomeToPlayer(player, args[0]);
				sender.sendMessage(ChatColor.GREEN + "Home named " + args[0] + " set to current location.");
				break;
				
			case 2: //Player sets another player's named home
				if (!sender.hasPermission("teleport.sethome.other", true)) throw CommandException.PERMISSION_DENIED;
				Storage.setHome(args[1], args[0], player.getLocation());
				sender.sendMessage(Storage.setHome(args[1], args[0], player.getLocation()).getReason());
				break;
				
			default:
				sender.sendMessage(ChatColor.RED + "Invalid amount of parameters!");
				break;
		}
	}
	
	@Command(description = "Deletes a home", name = "delhome", usage = "/delhome [name [player]]")
	public static void delHome(CommandSender sender, String[] args) {
		return;
	}
	
	@Command(description = "List all homes", name = "listhome", usage = "/listhome [player]")
	public static void listHome(CommandSender sender, String[] args) {
		
	}
}
