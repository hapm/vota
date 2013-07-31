package de.hapm.vota;
import org.bukkit.plugin.java.JavaPlugin;

import de.hapm.vota.commands.VotingCommandsExecutor;

/**
 * The Vota plugin allows users to easily vote for actions taken by other users.
 * 
 * @author hapm
 *
 */
public class VotaPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		VotingCommandsExecutor cmdExecutor = new VotingCommandsExecutor(this);
		getCommand("up").setExecutor(cmdExecutor);
		getCommand("down").setExecutor(cmdExecutor);
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
}
