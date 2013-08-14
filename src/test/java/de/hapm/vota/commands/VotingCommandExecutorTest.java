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

import com.avaje.ebean.EbeanServer;

import de.hapm.vota.VotaPlugin;
import de.hapm.vota.commands.VotingCommandExecutor;
import de.hapm.vota.data.Vote;

@RunWith(PowerMockRunner.class)
@PrepareForTest({VotaPlugin.class, VotingCommandExecutor.class})
public class VotingCommandExecutorTest {
	@Test
	public void testConstructor() {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		VotingCommandExecutor cmds = new VotingCommandExecutor(plugin);
		assertNotNull(cmds);
	}
	
	@Test
	public void onCommandUp() throws Exception {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		IMocksControl control = createControl();
		Player p = control.createMock(Player.class);
		Command command = control.createMock(Command.class);
		Server server = control.createMock(Server.class);
		//expect(downCommand.getName()).andReturn("down");
		
		control.replay();
		PowerMock.replay(plugin);
		VotingCommandExecutor cmds = new VotingCommandExecutor(plugin);
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
		
		Player p2 = control.createMock(Player.class);
		EbeanServer ebean = control.createMock(EbeanServer.class);
		Vote vote = PowerMock.createMock(Vote.class);
		expect(plugin.getServer()).andReturn(server).once();
		expect(server.getPlayer("existingPlayer")).andReturn(p2).once();
		expect(p2.getName()).andReturn("existingPlayer").anyTimes();
		expect(command.getName()).andReturn("up").anyTimes();
		expect(plugin.getDatabase()).andReturn(ebean);
		PowerMock.expectNew(Vote.class).andReturn(vote).once();
		vote.setMark(100);
		expectLastCall();
		vote.setSubject("existingPlayer");
		expectLastCall();
		expect(p.getName()).andReturn("votingPlayer");
		vote.setVoterName("votingPlayer");
		expectLastCall();
		vote.setTimeSpan(600);
		expectLastCall();
		ebean.save(vote);
		expectLastCall();
		p.sendMessage("You voted for the actions of existingPlayer in the last 10 minutes");
		expectLastCall();
		PowerMock.replay(plugin, vote, Vote.class);
		control.replay();
		assertTrue(cmds.onCommand(p, command, "up", new String[] {"existingPlayer"}));
		PowerMock.verify(plugin, vote);
		control.verify();
		control.reset();
		PowerMock.reset(plugin, vote, Vote.class);
	}

	@Test
	public void testOnCommandDown() throws Exception {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		IMocksControl control = createControl();
		Player p = control.createMock(Player.class);
		Command command = control.createMock(Command.class);
		Server server = control.createMock(Server.class);
		expect(plugin.getServer()).andReturn(server).once();
		expect(server.getPlayer("noPlayer")).andReturn(null).once();
		expect(command.getName()).andReturn("down").anyTimes();
		control.replay();
		PowerMock.replay(plugin);
		VotingCommandExecutor cmds = new VotingCommandExecutor(plugin);
		assertFalse(cmds.onCommand(p, command, "down", new String[] {"noPlayer"}));
		control.verify();
		PowerMock.verify(plugin);
		control.reset();
		PowerMock.reset(plugin);

		Player p2 = control.createMock(Player.class);
		EbeanServer ebean = control.createMock(EbeanServer.class);
		Vote vote = PowerMock.createMock(Vote.class);
		expect(plugin.getServer()).andReturn(server).once();
		expect(server.getPlayer("anotherPlayer")).andReturn(p2).once();
		expect(p2.getName()).andReturn("anotherPlayer2").anyTimes();
		expect(command.getName()).andReturn("down").anyTimes();
		expect(plugin.getDatabase()).andReturn(ebean);
		PowerMock.expectNew(Vote.class).andReturn(vote).once();
		vote.setMark(-100);
		expectLastCall();
		vote.setSubject("anotherPlayer2");
		expectLastCall();
		expect(p.getName()).andReturn("votingPlayer2");
		vote.setVoterName("votingPlayer2");
		expectLastCall();
		vote.setTimeSpan(600);
		expectLastCall();
		ebean.save(vote);
		expectLastCall();
		p.sendMessage("You voted for the actions of anotherPlayer2 in the last 10 minutes");
		expectLastCall();

		PowerMock.replay(plugin, vote, Vote.class);
		control.replay();
		assertTrue(cmds.onCommand(p, command, "down", new String[] {"anotherPlayer"}));
		PowerMock.verify(plugin, vote);
		control.verify();
		control.reset();
		PowerMock.reset(plugin, vote, Vote.class);
	}
}
