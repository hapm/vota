package de.hapm.vota.commands;

import java.util.Iterator;

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
public class StatsCommandExecutor implements CommandExecutor {

	private VotaPlugin plugin;

	public StatsCommandExecutor(VotaPlugin plugin) {
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
		else if(command.getName().equals("top10")) {
			Iterator<PlayerMarks> marks = plugin.findMarks();
			PlayerMarks mark;
			for (int i = 1; marks.hasNext(); i++) {
				mark = marks.next();
				sender.sendMessage(String.format("%d. %s (mark: %d, approved: %d)", i, mark.getPlayerName(), mark.getMark(), mark.getApprovedMark()));				
			}
		}
		return true;
	}

}
