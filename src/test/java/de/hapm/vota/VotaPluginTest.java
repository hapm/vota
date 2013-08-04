package de.hapm.vota;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.persistence.PersistenceException;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;

import de.hapm.bukkit.JavaPluginTest;
import de.hapm.vota.commands.StatsCommandsExecutor;
import de.hapm.vota.commands.VotingCommandsExecutor;
import de.hapm.vota.data.Vote;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PluginCommand.class, VotaPlugin.class, JavaPlugin.class})
public class VotaPluginTest extends JavaPluginTest {
	
	@Test
	public void testOnEnable() throws Exception {
		VotaPlugin plugin = PowerMock.createPartialMockForAllMethodsExcept(VotaPlugin.class, "onEnable");
		PluginCommand command = PowerMock.createMock(PluginCommand.class);
		expect(plugin.getCommand("up")).andReturn(command);
		expect(plugin.getCommand("down")).andReturn(command);
		expect(plugin.getCommand("top10")).andReturn(command);
		command.setExecutor(EasyMock.isA(VotingCommandsExecutor.class));
		expectLastCall().times(2);
		command.setExecutor(EasyMock.isA(StatsCommandsExecutor.class));
		expectLastCall();
		expect(command.getPlugin()).andReturn(plugin).anyTimes();
		PowerMock.expectPrivate(plugin, "setupDb");
		PowerMock.replay(plugin, command);
		plugin.onEnable();
		PowerMock.verify(plugin, command);
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
		assertThat(plugin.getDatabaseClasses(), contains(new Class<?>[] {Vote.class}));
	}
}
