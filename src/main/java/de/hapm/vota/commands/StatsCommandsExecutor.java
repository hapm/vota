package de.hapm.vota.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.hapm.vota.VotaPlugin;

/**
 * Implements all statistic commands needed to display voting statistics for the users.
 * 
 * @author hapm
 */
public class StatsCommandsExecutor implements CommandExecutor {

	private VotaPlugin plugin;

	public StatsCommandsExecutor(VotaPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String cmdname,
			String[] args) {
		return true;
	}

}
