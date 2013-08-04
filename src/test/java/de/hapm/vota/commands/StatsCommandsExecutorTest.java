package de.hapm.vota.commands;

import static org.junit.Assert.*;

import static org.easymock.EasyMock.*;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.hapm.vota.VotaPlugin;

@RunWith(PowerMockRunner.class)
@PrepareForTest({VotaPlugin.class, PluginCommand.class})
public class StatsCommandsExecutorTest {

	@Test
	public void testOnCommandTop10() {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		IMocksControl control = createControl();
		CommandSender sender = control.createMock(CommandSender.class);
		PluginCommand command = PowerMock.createMock(PluginCommand.class);
		PowerMock.replay(plugin, command);
		StatsCommandsExecutor exec = new StatsCommandsExecutor(plugin);
		assertTrue(exec.onCommand(sender, command, "top10", new String[0]));
		PowerMock.verify(plugin, command);
	}

}
