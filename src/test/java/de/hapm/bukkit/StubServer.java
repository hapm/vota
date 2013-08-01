package de.hapm.bukkit;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

import com.avaje.ebean.TxIsolation;
import com.avaje.ebean.config.ServerConfig;

public class StubServer implements Server {

	public void sendPluginMessage(Plugin source, String channel, byte[] message) {

	}

	public Set<String> getListeningPluginChannels() {
		return null;
	}

	public String getName() {
		return null;
	}

	public String getVersion() {
		return null;
	}

	public String getBukkitVersion() {
		return null;
	}

	public Player[] getOnlinePlayers() {
		return null;
	}

	public int getMaxPlayers() {
		return 0;
	}

	public int getPort() {
		return 0;
	}

	public int getViewDistance() {
		return 0;
	}

	public String getIp() {
		return null;
	}

	public String getServerName() {
		return null;
	}

	public String getServerId() {
		return null;
	}

	public String getWorldType() {
		return null;
	}

	public boolean getGenerateStructures() {
		return false;
	}

	public boolean getAllowEnd() {
		return false;
	}

	public boolean getAllowNether() {
		return false;
	}

	public boolean hasWhitelist() {
		return false;
	}

	public void setWhitelist(boolean value) {

	}

	public Set<OfflinePlayer> getWhitelistedPlayers() {
		return null;
	}

	public void reloadWhitelist() {

	}

	public int broadcastMessage(String message) {
		return 0;
	}

	public String getUpdateFolder() {
		return null;
	}

	public File getUpdateFolderFile() {
		return null;
	}

	public long getConnectionThrottle() {
		return 0;
	}

	public int getTicksPerAnimalSpawns() {
		return 0;
	}

	public int getTicksPerMonsterSpawns() {
		return 0;
	}

	public Player getPlayer(String name) {
		return null;
	}

	public Player getPlayerExact(String name) {
		return null;
	}

	public List<Player> matchPlayer(String name) {
		return null;
	}

	public PluginManager getPluginManager() {
		return null;
	}

	public BukkitScheduler getScheduler() {
		return null;
	}

	public ServicesManager getServicesManager() {
		return null;
	}

	public List<World> getWorlds() {
		return null;
	}

	public World createWorld(WorldCreator creator) {
		return null;
	}

	public boolean unloadWorld(String name, boolean save) {
		return false;
	}

	public boolean unloadWorld(World world, boolean save) {
		return false;
	}

	public World getWorld(String name) {
		return null;
	}

	public World getWorld(UUID uid) {
		return null;
	}

	public MapView getMap(short id) {
		return null;
	}

	public MapView createMap(World world) {
		return null;
	}

	public void reload() {

	}

	public Logger getLogger() {
		return null;
	}

	public PluginCommand getPluginCommand(String name) {
		return null;
	}

	public void savePlayers() {

	}

	public boolean dispatchCommand(CommandSender sender, String commandLine)
			throws CommandException {
		return false;
	}

	public void configureDbConfig(ServerConfig config) {
		ServerConfig fileConfig = new ServerConfig();
		fileConfig.loadFromProperties();
		fileConfig.getDataSourceConfig().setUrl("jdbc:sqlite::memory:");
		fileConfig.getDataSourceConfig().setUsername("sa");
		fileConfig.getDataSourceConfig().setPassword("");
		fileConfig.getDataSourceConfig().setDriver("org.sqlite.JDBC");
		fileConfig.getDataSourceConfig().setIsolationLevel(TxIsolation.READ_UNCOMMITTED.getLevel());
		//config.setDdlGenerate(true);
		//config.setDdlRun(true);
		config.setDataSourceConfig(fileConfig.getDataSourceConfig());
	}

	public boolean addRecipe(Recipe recipe) {
		return false;
	}

	public List<Recipe> getRecipesFor(ItemStack result) {
		return null;
	}

	public Iterator<Recipe> recipeIterator() {
		return null;
	}

	public void clearRecipes() {

	}

	public void resetRecipes() {

	}

	public Map<String, String[]> getCommandAliases() {
		return null;
	}

	public int getSpawnRadius() {
		return 0;
	}

	public void setSpawnRadius(int value) {

	}

	public boolean getOnlineMode() {
		return false;
	}

	public boolean getAllowFlight() {
		return false;
	}

	public boolean isHardcore() {
		return false;
	}

	public boolean useExactLoginLocation() {
		return false;
	}

	public void shutdown() {

	}

	public int broadcast(String message, String permission) {
		return 0;
	}

	public OfflinePlayer getOfflinePlayer(String name) {
		return null;
	}

	public Set<String> getIPBans() {
		return null;
	}

	public void banIP(String address) {

	}

	public void unbanIP(String address) {

	}

	public Set<OfflinePlayer> getBannedPlayers() {
		return null;
	}

	public Set<OfflinePlayer> getOperators() {
		return null;
	}

	public GameMode getDefaultGameMode() {
		return null;
	}

	public void setDefaultGameMode(GameMode mode) {

	}

	public ConsoleCommandSender getConsoleSender() {
		return null;
	}

	public File getWorldContainer() {
		return null;
	}

	public OfflinePlayer[] getOfflinePlayers() {
		return null;
	}

	public Messenger getMessenger() {
		return null;
	}

	public HelpMap getHelpMap() {
		return null;
	}

	public Inventory createInventory(InventoryHolder owner, InventoryType type) {
		return null;
	}

	public Inventory createInventory(InventoryHolder owner, int size) {
		return null;
	}

	public Inventory createInventory(InventoryHolder owner, int size,
			String title) {
		return null;
	}

	public int getMonsterSpawnLimit() {
		return 0;
	}

	public int getAnimalSpawnLimit() {
		return 0;
	}

	public int getWaterAnimalSpawnLimit() {
		return 0;
	}

	public int getAmbientSpawnLimit() {
		return 0;
	}

	public boolean isPrimaryThread() {
		return false;
	}

	public String getMotd() {
		return null;
	}

	public String getShutdownMessage() {
		return null;
	}

	public WarningState getWarningState() {
		return null;
	}

	public ItemFactory getItemFactory() {
		return null;
	}

	public ScoreboardManager getScoreboardManager() {
		return null;
	}

}
