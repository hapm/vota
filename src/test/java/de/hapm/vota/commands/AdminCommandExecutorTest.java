package de.hapm.vota.commands;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.Calendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.avaje.ebean.QueryIterator;

import de.hapm.vota.VotaPlugin;
import de.hapm.vota.data.Vote;

@RunWith(PowerMockRunner.class)
public class AdminCommandExecutorTest {
	@Test
	public void testConstructor() {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		AdminCommandExecutor cmdEx = new AdminCommandExecutor(plugin);
		assertNotNull(cmdEx);
		assertEquals(plugin, cmdEx.getPlugin());
	}

	@Test
	public void testOnCommand() {
		VotaPlugin plugin = PowerMock.createMock(VotaPlugin.class);
		AdminCommandExecutor cmdEx = new AdminCommandExecutor(plugin);
		IMocksControl ctrl = createControl();
		CommandSender sender = ctrl.createMock(CommandSender.class);
		Command cmd = ctrl.createMock(Command.class);
		expect(cmd.getName()).andReturn("nonsense").anyTimes();
		ctrl.replay();
		assertFalse(cmdEx.onCommand(sender, cmd, "nonesence", new String[0]));
		ctrl.verify();
		
		ctrl.reset();
		expect(cmd.getName()).andReturn("appr").anyTimes();
		@SuppressWarnings("unchecked")
		QueryIterator<Vote> it = ctrl.createMock(QueryIterator.class);
		Calendar calendar = Calendar.getInstance();
		expect(plugin.findVotes(true)).andReturn(it);
		Vote vote = new Vote();
		vote.setMark(100);
		vote.setSubject("user1");
		vote.setVoterName("v1");
		calendar.set(2013, 9, 10, 13, 0, 0);
		vote.setTimestamp(calendar.getTime());
		vote.setTimeSpan(1800);
		expect(it.hasNext()).andReturn(true);
		expect(it.next()).andReturn(vote);
		vote = new Vote();
		vote.setMark(100);
		vote.setSubject("anotherUser");
		vote.setVoterName("voter2");
		calendar.set(2013, 9, 10, 15, 10, 0);
		vote.setTimestamp(calendar.getTime());
		vote.setTimeSpan(600);
		expect(it.hasNext()).andReturn(true);
		expect(it.next()).andReturn(vote);
		vote = new Vote();
		vote.setMark(-100);
		vote.setSubject("user1");
		vote.setVoterName("voter2");
		calendar.set(2013, 10, 9, 11, 10, 0);
		vote.setTimestamp(calendar.getTime());
		vote.setTimeSpan(600);
		expect(it.hasNext()).andReturn(true);
		expect(it.next()).andReturn(vote);
		expect(it.hasNext()).andReturn(false);
		sender.sendMessage("1. user1, voter: v1, mark: 100, time: 10.10. 12:30-13:00");
		expectLastCall();
		sender.sendMessage("2. anotherUser, voter: voter2, mark: 100, time: 10.10. 15:00-15:10");
		expectLastCall();
		sender.sendMessage("3. user1, voter: voter2, mark: -100, time: 09.11. 11:00-11:10");
		expectLastCall();
		PowerMock.replay(plugin);
		ctrl.replay();
		assertTrue(cmdEx.onCommand(sender, cmd, "appr", new String[0]));
		PowerMock.verify(plugin);
		ctrl.verify();
	}

}
