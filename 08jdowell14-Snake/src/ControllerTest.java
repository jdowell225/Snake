import static org.junit.Assert.*;

import org.junit.Test;


public class ControllerTest {

	@Test
	public void testFileRead() {
		GameController game = new GameController();
		try{
			game.readScoresFromFile(game.getFile());
		} catch (Exception e) {
			
		}
		
		HighScore[] scores = game.getScores();
		System.out.println(scores[0].getInitials());
		if (! scores[0].getInitials().equalsIgnoreCase("MAW")){
			fail("Did not read initials");
		}
	}

}
