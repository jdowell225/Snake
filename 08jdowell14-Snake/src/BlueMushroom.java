import java.awt.Color;
import java.awt.Graphics;

/**
 * The blue mushroom class extends the abstract mushroom class and
 * represents a mushroom in the world of the game. When consumed it
 * prevents the player from changing directions for an amount of time
 * determined by the speed of the player.
 * @author Jacob
 *
 */
public class BlueMushroom extends Mushroom {

	public BlueMushroom(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * When the blue mushroom is consumed it tells the game to ignore the next
	 * number of strokes equal to the speed of the snake. This decision was made
	 * due to input from my own testing of the game but also from a few friends of 
	 * mine that I had play the game to get some default high scores. It then removes
	 * itself from the list of mushrooms that the game has.
	 */
	@Override
	protected void whenConsumed(GameController game, Snake s) {
		game.setStrokes(game.getSpeed());	//causes the game to ignore the next n keystrokes

		
		for(int count = 0; count < game.getShrooms().size(); count++){
			Mushroom mush = game.getShrooms().get(count);
			if (mush == this){
				game.getShrooms().remove(count);
			}
		}
	}

	/**
	 * The drawOn function draws a blue circle because I could not
	 * get the mushroom polygon to function properly.
	 */
	@Override
	protected void drawOn(Graphics g) {
		g.setColor(Color.cyan);
		//g.drawPolygon(super.p);
		g.drawOval(x, y, width, height);

	}

}
