import java.awt.Graphics;

/**
 * An instance of this class represents a snake in a game of snake. It contains
 * a list of segments that it uses to represent itself in the main frame,
 * it also has a pair of integers that represent the direction it is moving, the 
 * xDirection and the yDirection. It also has a handle on the width and height
 * of the canvas it is being painted on.
 * @author Jacob
 *
 */
public class Snake {
	private SegmentList myList;
	private int xDir, yDir;	//these will be either -1, 0, or 1
	private int maxWidth;
	private int maxHeight;
	
	/**
	 * The constructor adds two segments to the list and starts moving right.
	 * @param x
	 * @param y
	 * @param maxWidth
	 * @param maxHeight
	 */
	public Snake(int x, int y, int maxWidth, int maxHeight) {	//takes in a starting x and y
		myList = new SegmentList();
		myList.add(new Segment(x, y));
		myList.add(new Segment(x-Segment.SIZE, y));
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		xDir = 1;	//starts off moving to the right
		yDir = 0;
	}
	
	/**
	 * The drawOn function draws the segments in the list
	 * @param g
	 */
	public void drawOn(Graphics g){
		for (int count = 0; count < myList.size(); count++){
			myList.get(count).drawOn(g);
		}
	}
	
	/**
	 * The move function tells the list to move, and can throw an overlap exception if
	 * the snake is eating itself and an offscreen exception if the snake has moved beyond
	 * the borde4rs of the screen.
	 * @throws OffScreenException
	 * @throws OverlapException
	 */
	public void move() throws OffScreenException, OverlapException{
		myList.move(xDir, yDir);
		for (int count = 0; count < myList.size()-1; count++){
			if (myList.getHead().getX() == myList.get(count).getX() && myList.getHead().getY() == myList.get(count).getY()){
				throw new OverlapException();
			}
		}
		if (myList.getHead().getX() > this.maxWidth ||
				myList.getHead().getX() < 0 ||
				myList.getHead().getY() > this.maxHeight ||
				myList.getHead().getY() < 0){
			throw new OffScreenException();
		}
	}
	
	/**
	 * The extend function is called when the snake needs to increase in length, and
	 * adds another segment onto the end of the list
	 */
	public void extend(){
		myList.add(new Segment((int)myList.get(0).getX()-Segment.SIZE*xDir, (int)myList.get(0).getY()-Segment.SIZE*yDir));
	}
	
	/**
	 * The four following functions set the direction of the
	 * snake as either up, down, left, or right. It adjusts the
	 * xDir and yDir integers as appropriate for the direction the snake
	 * moves in. They also prevent the player from moving backwards and
	 * eating themselves by accident.
	 */
	public void goUp(){
		if (yDir == -1){
			return;
		}
		xDir = 0;
		yDir = 1;
	}
	
	public void goDown(){
		if (yDir == 1){
			return;
		}
		xDir = 0;
		yDir = -1;
	}
	
	public void goRight(){
		if (xDir == -1){
			return;
		}
		xDir = 1;
		yDir = 0;
	}
	
	public void goLeft(){
		if (xDir == 1){
			return;
		}
		xDir = -1;
		yDir = 0;
	}
	
	public SegmentList getList(){
		return myList;
	}

}
