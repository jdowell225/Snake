import java.awt.Color;
import java.awt.Graphics;

/**
 * The purple mushroom class extends from the abstract mushroom
 * class and represents a mushroom in the field of the game. When
 * consumed by the player it increases the speed of the snake.
 * @author Jacob
 *
 */
public class PurpleMushroom extends Mushroom {

	public PurpleMushroom(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * When consumed the mushroom tells the game to speed up the
	 * snake and then removes itself from the list of mushrooms the
	 * game holds onto.
	 */
	@Override
	protected void whenConsumed(GameController game, Snake s) {
		game.speedUp();

		for(int count = 0; count < game.getShrooms().size(); count++){
			Mushroom mush = game.getShrooms().get(count);
			if (mush == this){
				game.getShrooms().remove(count);
			}
		}
	}

	/**
	 * The drawOn function draws a magenta circle, because I could not get my
	 * mushroom polygon to work.
	 */
	@Override
	protected void drawOn(Graphics g) {
		g.setColor(Color.MAGENTA);
		//g.drawPolygon(super.p);
		g.drawOval(x, y, width, height);

	}

}
