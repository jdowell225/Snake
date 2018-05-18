import java.util.LinkedList;

/**
 * The SegmentList class holds onto a Segment, which by definition
 * holds onto another segment, and so on. Thus it becomes a linked list
 * of segments. This class has a simple constructor, calling the super class
 * and setting the tail to be null. The class has an add function, that adds a
 * segment as the new tail and adds the tail to that segment, so that the list
 * always holds one end of the 'snake'. It also has a get function, that returns
 * the segment at the position that is input into the algorithm. In addition, it holds
 * onto the head segment to make checking for overlap simple, as that will be the only
 * part of the snake that moves to a new "square" on the canvas.
 * @author Jacob
 *
 * @param <E>
 */
public class SegmentList{
	private Segment tail;
	private Segment head;

	/**
	 * The constructor calls the super and sets the tail to be null;
	 */
	public SegmentList(){
		super();
		this.tail = null;
		this.head = null;
	}

	/**
	 * The add function takes in a segment to be added and sets the
	 * next segment of that segment to be the tail and then sets the
	 * tail handle to be the taken segment.
	 * @param s
	 */
	public void add(Segment s){
		s.setNext(tail);
		this.tail = s;
		if (s.getNext() == null){
			this.head = s;
		}
	}

	/**
	 * The get function takes in an integer and goes through the 
	 * segments until it finds the segment at that integer, returning
	 * that segment. If the program looks for a segment beyond the
	 * ones that the list holds, it returns a null.
	 */
	public Segment get(int which){
		Segment returnSeg = null;

		for (int count = 0; count <= which; count++){
			if (count == 0){
				returnSeg = tail;
			} else {
				returnSeg = returnSeg.getNext();
			}

			if (returnSeg.getNext() == null && count < which){
				return null;
			}
		}

		return returnSeg;
	}

	/**
	 * The size function returns the number of segments the list holds onto
	 */
	public int size(){
		int returnInt = 0;

		if(tail == null){
			return returnInt;
		} else {
			Segment temp = tail;
			returnInt++;		//these lines are necessary for the temporary segment to exist and for the returnInt to count the tail in the number
			while(temp.getNext() != null){
				returnInt++;
				temp = temp.getNext();
			}
		}

		return returnInt;
	}

	/**
	 * Because my silly way of moving seems to have failed me, I have made a function devoted to moving.
	 * It makes each segment move one at a time taking the place of the last one, except for the head
	 * which moves in a particular direction given from the snake function. 
	 */
	public void move(int xDir, int yDir){
		Segment moving = null;
		for (int count = 0; count < this.size(); count++){
			moving = this.get(count);
			if (count == this.size()-1){
				moving.setLocation((int)moving.getLocation().getX()+xDir*Segment.SIZE, 
						(int)moving.getLocation().getY()+yDir*Segment.SIZE);
			} else {
				moving.setLocation(moving.getNext().getLocation());
			}
		}
	}
	
	public Segment getHead(){
		return this.head;
	}

}
