import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * The abstract class mushroom is the superclass of the four different mushrooms that
 * can spawn in the snake game. It has a polygon in the shape of a mushroom that is
 * created when it is constructed, and has a few variables that are used to establish
 * that polygon. It also has two abstract algorithms, whenConsumed and drawOn, which are
 * further defined by its subclasses.
 * @author Jacob
 *
 */
public abstract class Mushroom extends Rectangle{
	protected Polygon p;
	private final int RADIUS = Segment.SIZE/2;
	private int cX, cY;		//the center of the mushroom
	
	/**
	 * The constructor sets the center of the mushroom to figure the polygon,
	 * and then adds points to the polygon to create a mushroom-shaped polygon
	 * in the world of the game. I could never get this polygon to work, so if you
	 * see what I did wrong please let me know because I think that tiny mushrooms
	 * would look cool.
	 * @param x
	 * @param y
	 */
	public Mushroom(int x, int y){
		super(x, y, Segment.SIZE, Segment.SIZE);
		p = new Polygon();
		cX = x+RADIUS;
		cY = y+RADIUS;
		/*
		 * Making the 'head' of the mushroom
		 */
		for(int count = 0; count < 4; count++){
			p.addPoint((int)((cX+RADIUS)*Math.cos(Math.acos(count*Math.PI/3))), 
					(int)((cY+RADIUS)*Math.sin(Math.acos(count*Math.PI/3))));
		}
		/*
		 * Making the 'stem' of the mushroom
		 */
		p.addPoint((int)((cX-RADIUS/3)), (int)((cY-RADIUS/3)));
		p.addPoint((int)((cX+RADIUS)*Math.cos(Math.PI+Math.acos(Math.PI/3))), (int)((cY+RADIUS)*Math.sin((Math.PI+Math.acos(Math.PI/3)))));
		p.addPoint((int)((cX+RADIUS)*Math.cos(Math.PI*2-Math.acos(Math.PI/3))), (int)((cY+RADIUS)*Math.sin(Math.PI*2-Math.acos(Math.PI/3))));
		p.addPoint((int)((cX+RADIUS/3)), (int)((cY+RADIUS/3)));
	}
	
	protected abstract void whenConsumed(GameController game, Snake s);
	
	protected abstract void drawOn(Graphics g);
	
}
