package de.hapm.vota;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.persistence.PersistenceException;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.OrderBy;
import com.avaje.ebean.Query;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.RawSql;

import de.hapm.bukkit.JavaPluginTest;
import de.hapm.vota.commands.StatsCommandExecutor;
import de.hapm.vota.commands.VotingCommandExecutor;
import de.hapm.vota.data.PlayerMarks;
import de.hapm.vota.data.Vote;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PluginCommand.class, VotaPlugin.class, JavaPlugin.class, OrderBy.class})
public class VotaPluginTest extends JavaPluginTest {
	
	@Test
	public void testOnEnable() throws Exception {
		VotaPlugin plugin = PowerMock.createPartialMockForAllMethodsExcept(VotaPlugin.class, "onEnable");
		PluginCommand voteCommand = PowerMock.createMock(PluginCommand.class);
		PluginCommand statsCommand = PowerMock.createMock(PluginCommand.class);
		expect(plugin.getCommand("up")).andReturn(voteCommand);
		expect(plugin.getCommand("down")).andReturn(voteCommand);
		expect(plugin.getCommand("top10")).andReturn(statsCommand);
		expect(plugin.getCommand("stats")).andReturn(statsCommand);
		voteCommand.setExecutor(EasyMock.isA(VotingCommandExecutor.class));
		expectLastCall().times(2);
		statsCommand.setExecutor(EasyMock.isA(StatsCommandExecutor.class));
		expectLastCall().times(2);
		expect(voteCommand.getPlugin()).andReturn(plugin).anyTimes();
		expect(statsCommand.getPlugin()).andReturn(plugin).anyTimes();
		PowerMock.expectPrivate(plugin, "setupDb");
		PowerMock.replay(plugin, voteCommand, statsCommand);
		plugin.onEnable();
		PowerMock.verify(plugin, voteCommand, statsCommand);
	}
	
	@Test
	public void testSetupDb() throws Exception {
		VotaPlugin plugin = PowerMock.createPartialMockForAllMethodsExcept(VotaPlugin.class, "setupDb");
		EbeanServer ebean = createMock(EbeanServer.class);
		@SuppressWarnings("unchecked")
		Query<Vote> qry = createMock(Query.class);
		expect(plugin.getDatabase()).andReturn(ebean);
		expect(ebean.find(Vote.class)).andReturn(qry);
		expect(qry.findRowCount()).andThrow(new PersistenceException());
		PowerMock.expectPrivate(plugin, "installDDL", JavaPlugin.class);
		PowerMock.replay(plugin);
		replay(ebean, qry);
		Whitebox.invokeMethod(plugin, "setupDb");
		verify(ebean,qry);
		PowerMock.verify(plugin);
		
		reset(ebean, qry);
		PowerMock.reset(plugin);
		expect(plugin.getDatabase()).andReturn(ebean);
		expect(ebean.find(Vote.class)).andReturn(qry);
		expect(qry.findRowCount()).andReturn(0);
		PowerMock.replay(plugin);
		replay(ebean, qry);
		Whitebox.invokeMethod(plugin, "setupDb");
		verify(ebean, qry);
		PowerMock.verify(plugin);
	}

	@Test
	public void testPluginYml() {
		VotaPlugin plugin = new VotaPlugin();
		checkDescriptionFile(plugin);
	}
	
	@Test
	public void testGetDatabaseClasses() {
		VotaPlugin plugin = new VotaPlugin();
		assertThat(plugin.getDatabaseClasses(), contains(new Class<?>[] {Vote.class, PlayerMarks.class}));
	}
	
	@Test
	public void testFindMarksForPlayer() {
		VotaPlugin plugin = PowerMock.createPartialMockForAllMethodsExcept(VotaPlugin.class, "findMarks");
		IMocksControl control = EasyMock.createControl();
		EbeanServer ebean = control.createMock(EbeanServer.class);
		ExpressionFactory expFct = control.createMock(ExpressionFactory.class);
		Expression exp = control.createMock(Expression.class);
		PlayerMarks mark = new PlayerMarks("testPlayerName", 10, -10);
		@SuppressWarnings("unchecked")
		Query<PlayerMarks> qry = control.createMock(Query.class);
		expect(plugin.getDatabase()).andReturn(ebean);
		expect(ebean.find(PlayerMarks.class)).andReturn(qry);
		expect(qry.setRawSql(anyObject(RawSql.class))).andReturn(qry);
		expect(ebean.getExpressionFactory()).andReturn(expFct);
		expect(expFct.eq("v.subject", "testPlayerName")).andReturn(exp);
		expect(qry.having(exp)).andReturn(qry);
		expect(qry.findUnique()).andReturn(mark);
		PowerMock.replay(plugin);
		control.replay();
		assertThat(plugin.findMarks("testPlayerName"), is(mark));
		PowerMock.verify(plugin);
		control.verify();
	}
	
	@Test
	public void testFindMarks() {
		VotaPlugin plugin = PowerMock.createPartialMockForAllMethodsExcept(VotaPlugin.class, "findMarks");
		IMocksControl control = EasyMock.createControl();
		EbeanServer ebean = control.createMock(EbeanServer.class);
		@SuppressWarnings("unchecked")
		QueryIterator<PlayerMarks> it = control.createMock(QueryIterator.class);
		@SuppressWarnings("unchecked")
		OrderBy<PlayerMarks> order = control.createMock(OrderBy.class);
		@SuppressWarnings("unchecked")
		Query<PlayerMarks> qry = control.createMock(Query.class);
		expect(plugin.getDatabase()).andReturn(ebean);
		expect(ebean.find(PlayerMarks.class)).andReturn(qry);
		expect(qry.setRawSql(anyObject(RawSql.class))).andReturn(qry);
		expect(qry.order()).andReturn(order);
		expect(order.desc("mark")).andReturn(qry);
		expect(qry.findIterate()).andReturn(it);
		PowerMock.replay(plugin);
		control.replay();
		assertThat(plugin.findMarks(), is(it));
		PowerMock.verify(plugin);
		control.verify();
	}
}
