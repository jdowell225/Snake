import static org.junit.Assert.*;

import org.junit.Test;


public class SegmentListTest {

	@Test
	public void testAddnGet() {
		SegmentList myList = new SegmentList();
		Segment s = new Segment(1, 1);
		myList.add(s);
		
		assertNotNull(myList.get(0));
		
		if (myList.get(0).getX() != s.getX()){
			fail("Did not add or get properly");
		}
	}
	
	@Test
	public void testAddnGet2() {
		SegmentList myList = new SegmentList();
		myList.add(new Segment(1, 1));
		myList.add(new Segment(2, 2));
		
		if(myList.get(0).getX() != 2){
			fail("Did not add or get properly with two segments");
		}
	}
	
	@Test
	public void testSize() {
		SegmentList myList = new SegmentList();
		myList.add(new Segment(0, 0));
		myList.add(new Segment(1, 1));
		myList.add(new Segment(2, 2));
		
		if (myList.size() != 3){
			fail("Size should be three");
		}
	}
	
	@Test
	public void testEmpty() {
		SegmentList myList = new SegmentList();
		
		if(myList.size() != 0){
			fail("Size should be zero");
		}
	}

}
