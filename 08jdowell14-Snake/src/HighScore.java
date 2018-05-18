/**
 * This class represents a single player's high score.
 * It contains only the initials of the player and their
 * high score in the game of snake.
 */
public class HighScore {
	private String initials;
	private int score;

	/**
	 * This constructor is the basic one, taking the initials and
	 * score of the player and creating a high score for them.
	 * @param initials
	 * @param score
	 */
	public HighScore(String initials, int score) {
		this.initials = initials;
		this.score = score;
	}
	
	/**
	 * This constructor is one specifically used when reading high
	 * scores off of a file, and creates a high score from
	 * the given data of the file.
	 * @param line
	 * @param sep
	 */
	public HighScore(String line, String sep){
		String[] lines = line.split(sep);
		this.initials = lines[0];
		this.score = Integer.parseInt(lines[1]);
	}
	
	/**
	 * The to string function is used for when displaying
	 * the high score to the player, with the initials, score
	 * and a character symbolizing a line break for the JOptionPane.
	 */
	public String toString(){
		return this.initials+" : "+this.score+"\n";
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}
