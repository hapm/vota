package de.hapm.vota;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.java.JavaPlugin;

import de.hapm.vota.commands.StatsCommandsExecutor;
import de.hapm.vota.commands.VotingCommandsExecutor;
import de.hapm.vota.data.Vote;

/**
 * The Vota plugin allows users to easily vote for actions taken by other users.
 * 
 * @author hapm
 *
 */
public class VotaPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		VotingCommandsExecutor votesExecutor = new VotingCommandsExecutor(this);
		StatsCommandsExecutor statsExecutor = new StatsCommandsExecutor(this);
		getCommand("up").setExecutor(votesExecutor);
		getCommand("down").setExecutor(votesExecutor);
		getCommand("top10").setExecutor(statsExecutor);
		setupDb();
		super.onEnable();
	}
	
	private void setupDb() {
		try {
			getDatabase().find(Vote.class).findRowCount();
		}
		catch (PersistenceException ex) {
			installDDL();
		}
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> classes = super.getDatabaseClasses();
		classes.add(Vote.class);
		return classes;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
}
