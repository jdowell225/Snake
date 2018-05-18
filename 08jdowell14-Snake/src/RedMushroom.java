import java.awt.Color;
import java.awt.Graphics;

/**
 * The RedMushroom extends from the abstract Mushroom class and 
 * represents a mushroom in the world of the game. When consumed
 * by the snake, it will kill the snake and reset the game.
 * @author Jacob
 *
 */
public class RedMushroom extends Mushroom {

	public RedMushroom(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * When consumed by the snake, the red mushroom decreases the number
	 * of lives left and resets the game, because the snake died. It then
	 * removes itself from the list of mushrooms still in the game.
	 */
	@Override
	protected void whenConsumed(GameController game, Snake s) {
		game.setLives(game.getLives()-1);
		game.reset();
		
		for(int count = 0; count < game.getShrooms().size(); count++){
			Mushroom mush = game.getShrooms().get(count);
			if (mush == this){
				game.getShrooms().remove(count);
			}
		}
	}

	/**
	 * The drawOn function draws a red circle because I could not get my 
	 * polygon mushroom to work.
	 */
	@Override
	protected void drawOn(Graphics g) {
		g.setColor(Color.RED);
		//g.drawPolygon(super.p);
		g.drawOval(x, y, width, height);

	}

}
