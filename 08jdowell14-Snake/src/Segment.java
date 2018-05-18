import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * An instance of this class represents a segment inside of a snake. It has an x and a y
 * coordinate as well as a handle on the segment that comes next in the snake chain. This
 * handle could also be null, thus making the segment the head of the snake. It knows how to
 * draw itself onto the world as a circle with width and height represented by the segment's size
 * variable. There are also getters and setters for each variable other than the size.
 * 
 * @author Jacob
 *
 */
public class Segment extends Rectangle{
	private Segment next;
	public static final int SIZE = 10;
	
	/**
	 * The constructor just sets the location of the segment
	 */
	public Segment(int x, int y){
		super(x, y, SIZE, SIZE);
		this.next = null;
	}
	
	/**
	 * The drawOn function sets the color of the graphics object to be white and
	 * then draws an oval at the location of the segment of the size of the segment
	 * @param gfx
	 */
	public void drawOn(Graphics gfx){
		gfx.drawOval((int)x, (int)y, SIZE, SIZE);
	}

	
	
	public Segment getNext() {
		return next;
	}

	public void setNext(Segment next) {
		this.next = next;
	}
	
}
