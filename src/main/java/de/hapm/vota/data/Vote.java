package de.hapm.vota.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A vote saves the data associated with the vote. 
 * 
 * @author hapm
 */
@Entity
public class Vote {
	/**
	 * Internal id used as the primary key for persistence.
	 */
	@Id
	private int voteId;
	
	/**
	 * Time stamp when the vote occurred in server time.
	 */
	private Date timestamp;
	
	/**
	 * The name of the user that voted this vote.
	 */
	private String voterName;
	
	/**
	 * The user, that is the subject of this vote.
	 */
	private String subject;
	
	/**
	 * Saves the approver of the vote, or null if the Vote wasn't approved yet.
	 */
	private String approver;
	
	/**
	 * Saves how much time in seconds before the vote should be tracked for the approval. 
	 */
	private int timeSpan;
	
	/**
	 * Saves if the vote is a good or bad vote.
	 */
	private int mark;
	
	/**
	 * Initializes a new instance of the Vote class.
	 * 
	 * All fields are set to their defaults.
	 */
	public Vote() {
		
	}

	/**
	 * Gets the subject, the user voted on.
	 * 
	 * @return The name of the subject user.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject, the vote is for.
	 * 
	 * @param subject The name of the subject user.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the name of the user, who voted this vote.
	 * 
	 * @return The users name.
	 */
	public String getVoterName() {
		return voterName;
	}

	/**
	 * Sets the name of the user, who voted this vote.
	 * 
	 * @param voterName The name of the voter.
	 */
	public void setVoterName(String voterName) {
		this.voterName = voterName;
	}

	/**
	 * Gets the server time, when the vote appeared.
	 * 
	 * @return The time, when the vote appeared.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the server time, when the vote appeared.
	 * 
	 * @param timestamp The new time to set.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the approver of this vote.
	 * 
	 * @return The user name of the approver, or null if the vote wasn't approved yet.
	 */
	public String getApprover() {
		return approver;
	}

	/**
	 * Sets the name of the approver for this vote.
	 * 
	 * @param approver The user name of the approver or null to unapprove this vote.
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}
	
	/**
	 * Gets a value indicating whether the vote is already approved or not.
	 * 
	 * @return true, if the message was already approved
	 *         false otherwise.
	 */
	public boolean isApproved() {
		return approver != null;
	}

	/**
	 * Gets the time, that should be looked at, before the vote appeared.
	 * 
	 * @return The time in seconds.
	 */
	public int getTimeSpan() {
		return timeSpan;
	}

	/**
	 * Sets the time, that should be looked at, before the vote appeared.
	 * 
	 * @param timeSpan The time in seconds.
	 */
	public void setTimeSpan(int timeSpan) {
		this.timeSpan = timeSpan;
	}

	/**
	 * Gets the mark given by this vote.
	 * 
	 * The value can be greater 0 for good votes or lower 0 for bad votes.
	 * The bigger the value the more influence the vote has on the resulting voting level.
	 * 
	 * @return The mark of this voting.
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * Sets the mark given by this vote.
	 * 
	 * The value can be greater 0 for good votes or lower 0 for bad votes.
	 * The bigger the value the more influence the vote has on the resulting voting level.
	 * 
	 * @param mark The new mark to set.
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}
	
	/**
	 * Gets a value indicating whether the vote is a good vote.
	 * 
	 * If mark is bigger than 0, its a good vote, if its smaller 0 its a bad vote.
	 * 
	 * @return true, if the vote is a good vote, false otherwise.
	 */
	public boolean isGood() {
		return mark > 0;
	}
	
	/**
	 * Gets a value indicating whether the vote is a bad vote.
	 * 
	 * If mark is bigger than 0, its a good vote, if its smaller 0 its a bad vote.
	 * 
	 * @return true, if the vote is a bad vote, false otherwise.
	 */
	public boolean isBad() {
		return mark < 0;
	}
}
