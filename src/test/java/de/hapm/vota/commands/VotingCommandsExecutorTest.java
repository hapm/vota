package de.hapm.vota.commands;

import static org.junit.Assert.*;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import static org.easymock.EasyMock.*;

import org.easymock.IMocksControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.hapm.vota.VotaPlugin;
import de.hapm.vota.commands.VotingCommandsExecutor;

@RunWith(PowerMockRunner.class)
@PrepareForTest(VotaPlugin.class)
public class VotingCommandsExecutorTest {
	@Test
	public void testConstructor() {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		VotingCommandsExecutor cmds = new VotingCommandsExecutor(plugin);
		assertNotNull(cmds);
	}
	
	@Test
	public void onCommand() {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		IMocksControl control = createControl();
		Player p = control.createMock(Player.class);
		Command command = control.createMock(Command.class);
		Server server = control.createMock(Server.class);
		//expect(downCommand.getName()).andReturn("down");
		
		control.replay();
		PowerMock.replay(plugin);
		VotingCommandsExecutor cmds = new VotingCommandsExecutor(plugin);
		assertFalse(cmds.onCommand(p, command, "up", new String[0]));
		control.verify();
		PowerMock.verify(plugin);
		control.reset();
		PowerMock.reset(plugin);
		
		expect(plugin.getServer()).andReturn(server).once();
		expect(server.getPlayer("blah")).andReturn(null).once();
		expect(command.getName()).andReturn("up").anyTimes();
		control.replay();
		PowerMock.replay(plugin);
		assertFalse(cmds.onCommand(p, command, "up", new String[] {"blah"}));
		control.verify();
		PowerMock.verify(plugin);
		control.reset();
		PowerMock.reset(plugin);
		
		expect(plugin.getServer()).andReturn(server).once();
		expect(server.getPlayer("noPlayer")).andReturn(null).once();
		expect(command.getName()).andReturn("down").anyTimes();
		control.replay();
		PowerMock.replay(plugin);
		assertFalse(cmds.onCommand(p, command, "down", new String[] {"noPlayer"}));
		control.verify();
		PowerMock.verify(plugin);
		control.reset();
		PowerMock.reset(plugin);
		
		Player p2 = control.createMock(Player.class);
		expect(plugin.getServer()).andReturn(server).once();
		expect(server.getPlayer("existingPlayer")).andReturn(p2).once();
		expect(command.getName()).andReturn("down").anyTimes();
		control.replay();
		PowerMock.replay(plugin);
		assertTrue(cmds.onCommand(p, command, "down", new String[] {"existingPlayer"}));
		control.verify();
		PowerMock.verify(plugin);
		control.reset();
		PowerMock.reset(plugin);
	}
}
