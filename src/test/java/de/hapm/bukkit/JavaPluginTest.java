package de.hapm.bukkit;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;


import org.bukkit.Server;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.PluginProxy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import com.avaje.ebean.config.ServerConfig;

public class JavaPluginTest {
	protected Server server;
	private PluginDescriptionFile desc;
	
	@Rule
	public TemporaryFolder temp = new TemporaryFolder();

	@Before
	public void setUp() {
		server = createMock(Server.class);
		Logger log = Logger.getLogger("test");
		expect(server.getLogger()).andReturn(log).atLeastOnce();
	}
	
	protected void prepare(JavaPlugin plugin) {
		desc = new PluginDescriptionFile("test", "1.0", plugin.getClass().getName());
		try {
			InputStream ymlStream = plugin.getClass().getResourceAsStream("/plugin.yml");
			assertNotNull(ymlStream);
			desc = new PluginDescriptionFile(ymlStream);
		} catch (InvalidDescriptionException e) {
			fail("plugin.yml invalide");
		}
		if (desc.isDatabaseEnabled()) {
			server.configureDbConfig(anyObject(ServerConfig.class));
			expectLastCall().andStubDelegateTo(new StubServer());
		}
		assertEquals(plugin.getClass().getName(), desc.getMain());
	}
	
	protected void initialize(JavaPlugin plugin) {
		File dataFolder = null;
		if (desc == null) {
			fail("Call prepare() first");
		}
		
		try {
			dataFolder = temp.newFolder();
		} catch (IOException e) {
			fail("Couldn't create temp data folder");
		}
		
		PluginProxy.initialize(plugin, null, server, desc, dataFolder, null, ClassLoader.getSystemClassLoader());
	}
	
}