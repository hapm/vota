package de.hapm.vota.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.hapm.vota.VotaPlugin;
import de.hapm.vota.data.Vote;

/**
 * Implements all voting commands needed to vote users up or down.
 * 
 * @author hapm
 */
public class VotingCommandExecutor implements CommandExecutor {
	/**
	 * Saves the plugin instance.
	 */
	private VotaPlugin plugin;
	
	/**
	 * Initializes a new instance of the {@link VotingCommandExecutor} class.
	 *  
	 * @param plugin The VotaPlugin instance, this executor is initialized for.
	 */
	public VotingCommandExecutor(VotaPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String cmdname,
			String[] args) {
		if (args.length < 1)
			return false;
		
		Player voted = plugin.getServer().getPlayer(args[0]);
		if (voted == null)
			return false;
		
		Vote v = new Vote();
		v.setSubject(voted.getName());
		v.setVoterName(((Player)sender).getName());
		v.setTimeSpan(600);
		if (command.getName().equals("up")) {
			v.setMark(100);
		}
		else if(command.getName().equals("down")) {
			v.setMark(-100);
		}
		else {
			return false;
		}
		
		plugin.getDatabase().save(v);
		
		sender.sendMessage(String.format("You voted for the actions of %s in the last 10 minutes", voted.getName()));
		
		return true;
	}
	
}
