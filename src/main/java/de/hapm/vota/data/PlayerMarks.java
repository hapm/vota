package de.hapm.vota.data;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.annotation.Sql;

/**
 * The PlayerMarks class combines the user name, the associated mark and the approved mark in one object.
 * 
 * @author hapm
 */
@Entity
@Sql
public class PlayerMarks {
	/**
	 * Saves the player name of the player, the marks are for.
	 */
	@Id
	private String playerName;
	
	/**
	 * Saves the mark the users has over all votes.
	 */
	private Integer mark;
	
	/**
	 * Saves the mark the user has over all approved votes.s
	 */
	private Integer approvedMark;

	/**
	 * Initializes a new instance of the PlayerMarks class.
	 * 
	 * @param playerName The name of the player, which marks are saved in this instance.
	 * @param mark The overall mark of the given player over all votes.
	 * @param approvedMark The overall mark of the given player over all approved marks.
	 */
	public PlayerMarks(String playerName, int mark, int approvedMark) {
		this.playerName = playerName;
		this.mark = mark;
		this.approvedMark = approvedMark;
	}

	/**
	 * Gets the name of the player, which marks are saved in the PlayerMarks instance.
	 * 
	 * @return The name of the player.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Gets the mark of the player, that is calculated over all votes.
	 * 
	 * @return The mark as an integer. Values > 0 are good, < 0 are bad.
	 */
	public int getMark() {
		if (mark == null)
			return 0;
		
		return mark;
	}


	/**
	 * Gets the mark of the player, that is calculated over all approved votes.
	 * 
	 * @return The mark as an integer. Values > 0 are good, < 0 are bad.
	 */
	public int getApprovedMark() {
		if (approvedMark == null)
			return 0;
		
		return approvedMark;
	}

}
