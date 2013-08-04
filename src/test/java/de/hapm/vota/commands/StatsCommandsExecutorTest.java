package de.hapm.vota.commands;

import static org.junit.Assert.*;

import static org.easymock.EasyMock.*;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.hapm.vota.VotaPlugin;
import de.hapm.vota.data.PlayerMarks;

@RunWith(PowerMockRunner.class)
@PrepareForTest({VotaPlugin.class, PluginCommand.class})
public class StatsCommandsExecutorTest {

	@Test
	public void testOnCommandTop10() {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		IMocksControl control = createControl();
		CommandSender sender = control.createMock(CommandSender.class);
		PluginCommand command = PowerMock.createMock(PluginCommand.class);
		expect(command.getName()).andReturn("top10").anyTimes();
		PowerMock.replay(plugin, command);
		StatsCommandsExecutor exec = new StatsCommandsExecutor(plugin);
		assertTrue(exec.onCommand(sender, command, "top10", new String[0]));
		PowerMock.verify(plugin, command);
	}

	@Test
	public void testOnCommandStats() {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		IMocksControl control = createControl();
		Player sender = control.createMock(Player.class);
		PluginCommand command = PowerMock.createMock(PluginCommand.class);
		PlayerMarks marks = new PlayerMarks("player1", 10, 20);
		expect(command.getName()).andReturn("stats").anyTimes();
		expect(sender.getName()).andReturn("player1");
		expect(plugin.findMarks("player1")).andReturn(marks);
		sender.sendMessage("player1's current mark is 10, his approved mark is 20");
		expectLastCall();
		PowerMock.replay(plugin, command);
		control.replay();
		StatsCommandsExecutor exec = new StatsCommandsExecutor(plugin);
		assertTrue(exec.onCommand(sender, command, "stats", new String[0]));
		PowerMock.verify(plugin, command);
		control.verify();
		
		PowerMock.reset(plugin, command);
		control.reset();
		expect(command.getName()).andReturn("stats").anyTimes();
		expect(sender.getName()).andReturn("noPlayerWithStats");
		expect(plugin.findMarks("noPlayerWithStats")).andReturn(null);
		sender.sendMessage("noPlayerWithStats never got a vote so far");
		expectLastCall();
		PowerMock.replay(plugin, command);
		control.replay();
		exec = new StatsCommandsExecutor(plugin);
		assertTrue(exec.onCommand(sender, command, "stats", new String[0]));
		PowerMock.verify(plugin, command);
		control.verify();
	}
}
