package de.hapm.vota;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.persistence.PersistenceException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;

import de.hapm.bukkit.JavaPluginTest;
import de.hapm.vota.data.Vote;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PluginCommand.class, VotaPlugin.class, JavaPlugin.class})
public class VotaPluginTest extends JavaPluginTest {
	
	@Test
	public void testOnEnable() throws Exception {
		VotaPlugin plugin = PowerMock.createPartialMockForAllMethodsExcept(VotaPlugin.class, "onEnable");
		PluginCommand commandMock = PowerMock.createMock(PluginCommand.class);
		expect(plugin.getCommand("up")).andReturn(commandMock);
		expect(plugin.getCommand("down")).andReturn(commandMock);
		commandMock.setExecutor(anyObject(CommandExecutor.class));
		expectLastCall().times(2);
		expect(commandMock.getPlugin()).andReturn(plugin).anyTimes();
		PowerMock.expectPrivate(plugin, "setupDb");
		PowerMock.replay(plugin, commandMock);
		plugin.onEnable();
		PowerMock.verify(plugin, commandMock);
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
