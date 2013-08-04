package de.hapm.vota.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.hapm.vota.VotaPlugin;
import de.hapm.vota.data.PlayerMarks;

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
		if (command.getName().equals("stats")) {
			String subjectName = sender.getName();
			PlayerMarks marks = plugin.findMarks(subjectName);
			if (marks != null) {
				sender.sendMessage(String.format("%s's current mark is %d, his approved mark is %d", marks.getPlayerName(), marks.getMark(), marks.getApprovedMark()));
			}
			else {
				sender.sendMessage(String.format("%s never got a vote so far", subjectName));
			}
		}
		return true;
	}

}
