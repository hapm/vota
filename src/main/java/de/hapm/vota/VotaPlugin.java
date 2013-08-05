package de.hapm.vota;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

import de.hapm.vota.commands.StatsCommandsExecutor;
import de.hapm.vota.commands.VotingCommandsExecutor;
import de.hapm.vota.data.PlayerMarks;
import de.hapm.vota.data.Vote;

/**
 * The Vota plugin allows users to easily vote for actions taken by other users.
 * 
 * @author hapm
 *
 */
public class VotaPlugin extends JavaPlugin {	
	private static final String SQL_SELECT_PLAYER_MARKS = "SELECT v.subject AS playerName, SUM(v.mark) AS mark, v2.mark AS approvedMark FROM Vote v LEFT OUTER JOIN (SELECT v3.subject, SUM(v3.mark) AS mark FROM Vote v3 GROUP BY v3.subject HAVING NOT v3.approver IS NULL) v2 ON v.subject = v2.subject GROUP BY v.subject, v2.mark";

	@Override
	public void onEnable() {
		VotingCommandsExecutor votesExecutor = new VotingCommandsExecutor(this);
		StatsCommandsExecutor statsExecutor = new StatsCommandsExecutor(this);
		getCommand("up").setExecutor(votesExecutor);
		getCommand("down").setExecutor(votesExecutor);
		getCommand("top10").setExecutor(statsExecutor);
		getCommand("stats").setExecutor(statsExecutor);
		setupDb();
		super.onEnable();
	}
	
	private void setupDb() {
		try {
			getDatabase().find(Vote.class).findRowCount();
		}
		catch (PersistenceException ex) {
			installDDL();
		}
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> classes = super.getDatabaseClasses();
		classes.add(Vote.class);
		classes.add(PlayerMarks.class);
		return classes;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}

	public PlayerMarks findMarks(String playerName) {
		EbeanServer db = getDatabase();
		Query<PlayerMarks> qry = db.find(PlayerMarks.class);
		RawSql sql = RawSqlBuilder.parse(SQL_SELECT_PLAYER_MARKS).create();
		qry.setRawSql(sql);
		qry.having(db.getExpressionFactory().eq("v.subject", playerName));
		PlayerMarks result = qry.findUnique();
		return result;
	}

	public QueryIterator<PlayerMarks> findMarks() {
		RawSql sql = RawSqlBuilder.parse(SQL_SELECT_PLAYER_MARKS).create();
		return getDatabase().find(PlayerMarks.class).setRawSql(sql).order().desc("mark").findIterate();
	}
}
