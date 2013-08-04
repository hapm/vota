package de.hapm.vota.data;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class PlayerMarksTest {

	@Test
	public void testConstructor() {
		PlayerMarks marks = new PlayerMarks("playerName", 100, -50);
		assertThat(marks.getPlayerName(), is("playerName"));
		assertThat(marks.getMark(), is(100));
		assertThat(marks.getApprovedMark(), is(-50));
		
		marks = new PlayerMarks("anotherPlayerName", 49, 10);
		assertThat(marks.getPlayerName(), is("anotherPlayerName"));
		assertThat(marks.getMark(), is(49));
		assertThat(marks.getApprovedMark(), is(10));
	}

}
