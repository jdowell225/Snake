import java.awt.Color;
import java.awt.Graphics;

/**
 * The green mushroom class extends from the abstract mushroom class
 * and represents a green mushroom in the world of the game. When consumed
 * it tells the game that the snake is supposed to increase in size, and the
 * game takes it from there.
 * @author Jacob
 *
 */
public class GreenMushroom extends Mushroom {

	public GreenMushroom(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * The when consumed algorithm tells the game controller that the snake
	 * needs to increase in size, which also increases the speed of the snake
	 * and adds points to the player's score. It then removes itself from the 
	 * game's list of mushrooms.
	 */
	@Override
	protected void whenConsumed(GameController game, Snake s) {
		s.extend();
		//System.out.println("You ate me!");
		game.snakeUp();
		
		for(int count = 0; count < game.getShrooms().size(); count++){
			Mushroom mush = game.getShrooms().get(count);
			if (mush == this){
				game.getShrooms().remove(count);
			}
		}
	}

	/**
	 * The drawOn algorithm draws a green circle because I could not get the
	 * mushroom polygon to work properly.
	 */
	@Override
	protected void drawOn(Graphics g) {
		g.setColor(Color.green);
		//g.drawPolygon(super.p);
		g.drawOval(x, y, width, height);

	}

}
