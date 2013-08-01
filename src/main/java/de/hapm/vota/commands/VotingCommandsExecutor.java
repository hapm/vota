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
public class VotingCommandsExecutor implements CommandExecutor {
	/**
	 * Saves the plugin instance.
	 */
	private VotaPlugin plugin;
	
	/**
	 * Initializes a new instance of the {@link VotingCommandsExecutor} class.
	 *  
	 * @param plugin The VotaPlugin instance, this executor is initialized for.
	 */
	public VotingCommandsExecutor(VotaPlugin plugin) {
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
		v.setSubject(args[0]);
		v.setVoterName(((Player)sender).getName());
		v.setTimeSpan(600);
		plugin.getDatabase().save(v);
		if (command.getName().equals("up")) {
			v.setMark(100);
		}
		else if(command.getName().equals("down")) {
			v.setMark(-100);
		}
		else {
			return false;
		}
		
		return true;
	}
	
}
