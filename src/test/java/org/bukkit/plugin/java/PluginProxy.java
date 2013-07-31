package org.bukkit.plugin.java;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

public abstract class PluginProxy {
	public static void initialize(JavaPlugin plugin, PluginLoader loader, Server server, 
			PluginDescriptionFile desc, File dataFolder, File file, ClassLoader classLoader) {
		plugin.initialize(loader, server, desc, dataFolder, file, classLoader);
	}
}
