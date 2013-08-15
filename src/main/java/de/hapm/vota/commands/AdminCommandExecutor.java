package de.hapm.vota.commands;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.avaje.ebean.QueryIterator;

import de.hapm.vota.VotaPlugin;
import de.hapm.vota.data.Vote;

/**
 * The {@link AdminCommandExecutor} handles all admin commands for Vota.
 * 
 * @author Markus Andree
 */
public class AdminCommandExecutor implements CommandExecutor {

	/**
	 * Saves the plugin instance, the CommandExecutor is associated to.
	 */
	private VotaPlugin plugin;

	/**
	 * Initializes a new instance of the AdminCommandExecutor class.
	 * 
	 * @param plugin The plugin to associate with the CommandExecutor.
	 */
	public AdminCommandExecutor(VotaPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Gets the plugin instance associated with the AdminCommandExecutor.
	 * 
	 * @return The associated plugin instance.
	 */
	public VotaPlugin getPlugin() {
		return plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (cmd.getName().equals("appr")) {
			Calendar c = Calendar.getInstance();
			QueryIterator<Vote> it = plugin.findVotes(true);
			int i = 0;
			while (it.hasNext()) {
				Vote v = it.next();
				i++;
				c.setTime(new Date(v.getTimestamp().getTime()-v.getTimeSpan()*1000));
				sender.sendMessage(String.format("%d. %s, voter: %s, mark: %d, time: %5$td.%5$tm. %6$tR-%5$tR", i, v.getSubject(), v.getVoterName(), v.getMark(), v.getTimestamp(), c.getTime()));
			}
			
			return true;
		}
		
		return false;
	}
}
