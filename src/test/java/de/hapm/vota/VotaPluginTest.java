package de.hapm.vota;

import org.bukkit.command.PluginCommand;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.hapm.bukkit.JavaPluginTest;
import de.hapm.vota.commands.VotingCommandsExecutor;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PluginCommand.class)
public class VotaPluginTest extends JavaPluginTest {

	@Test
	public void testLifecycle() {
		VotaPlugin plugin = new VotaPlugin();
		PluginCommand commandMock = PowerMock.createMock(PluginCommand.class);
		commandMock.setExecutor(EasyMock.anyObject(VotingCommandsExecutor.class));
		EasyMock.expectLastCall().times(2);
		EasyMock.expect(commandMock.getPlugin()).andReturn(plugin).anyTimes();
		prepare(plugin);
		EasyMock.expect(server.getPluginCommand("up")).andReturn(commandMock);
		EasyMock.expect(server.getPluginCommand("down")).andReturn(commandMock);
		EasyMock.replay(server);
		EasyMock.replay(commandMock);
		initialize(plugin);
		plugin.onEnable();
		plugin.onDisable();
		EasyMock.verify(commandMock);
		EasyMock.verify(server);
	}

}
