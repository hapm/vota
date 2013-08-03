package de.hapm.vota;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.bukkit.command.PluginCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.hapm.bukkit.JavaPluginTest;
import de.hapm.vota.commands.VotingCommandsExecutor;
import de.hapm.vota.data.Vote;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PluginCommand.class)
public class VotaPluginTest extends JavaPluginTest {

	@Test
	public void testLifecycle() {
		VotaPlugin plugin = new VotaPlugin();
		PluginCommand commandMock = PowerMock.createMock(PluginCommand.class);
		commandMock.setExecutor(anyObject(VotingCommandsExecutor.class));
		expectLastCall().times(2);
		expect(commandMock.getPlugin()).andReturn(plugin).anyTimes();
		prepare(plugin);
		expect(server.getPluginCommand("up")).andReturn(commandMock);
		expect(server.getPluginCommand("down")).andReturn(commandMock);
		replay(server);
		replay(commandMock);
		initialize(plugin);
		assertThat(plugin.getDatabaseClasses(), contains(new Class<?>[] {Vote.class}));
		plugin.onEnable();
		plugin.onDisable();
		verify(commandMock);
		verify(server);
	}

}
