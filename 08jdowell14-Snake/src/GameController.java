import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * The game controller object is what controls the entire gamestate. It is created
 * by the main frame, and has a handle on a snake, integers representing points and
 * lives, an integer representing the speed of the snake, an integer representing the
 * number of strokes the snake is frozen for, the height and width of the frame, a list
 * of mushroom objects, a Random object, an integer representing how often mushrooms are spawned,
 * a timer that tells the snake to move, a timer that tells mushrooms to be planted, an
 * array of high scores, a separator string for file reading, a file object, and a boolean
 * representing if the game is over or not. It has algorithms to increase
 * the size and speed of the snake, change its direction, randomly select and plant
 * a mushroom on the screen, reset the gamestate if the snake dies, end the game, read
 * and write high scores from a file, and check to see if the player's score
 * is a high score, and changing the list appropriately. It also has two private
 * classes that extend actionListener and are used by the timers.
 * @author Jacob
 *
 */
public class GameController {
	private Snake mySnake;
	private int points;
	private int lives;

	private int moveSpeed;
	private int ignoreStrokes;	//used for when the player eats a blue mushroom

	private final int MAX_WIDTH = 300;
	private final int MAX_HEIGHT = 320;

	private ArrayList<Mushroom> shrooms;
	private Random rand;
	private final int BIRTH_RATE = 100;

	private Timer moveTimer;
	private Timer mushroomTimer;
	
	private boolean gameOver;
	
	private HighScore[] scores;
	private final String SEPARATOR = ":";
	
	private File file;

	/**
	 * The constructor tells the game to reset, and then sets up the
	 * variables not set up by that function, like the points, the
	 * game over, the two timers, the score, and the file. It also
	 * fills the list of high scores.
	 */
	public GameController() {
		//mySnake = null;
		lives = 5;
		reset();

		gameOver = false;

		moveTimer.start();
		
		points = 0;

		rand = new Random();
		mushroomTimer = new Timer(1000, new MushroomTimerHelper());
		mushroomTimer.start();
		
		scores = new HighScore[10];

		file = new File("highscore.txt");
		
		try{
			readScoresFromFile(file);
		} catch (Exception e){
			
		}
	}

	/**
	 * The snakeUp function tells the snake to speed up and increases the
	 * points.
	 */
	public void snakeUp(){
		speedUp();
		points += 100;
	}

	/**
	 * The speed up function is called by the purple mushroom to increase the
	 * speed of the snake without increasing the size as well, and by the
	 * snake up function when the snake is increasing in size.
	 */
	public void speedUp(){
		moveSpeed+=1;
		moveTimer.setDelay(1000/moveSpeed);
	}

	/**
	 * The reset function is called when the game is first constructed and
	 * every time that the snake dies. It resets the move timer, creates 
	 * a new snake, creates a new list of mushrooms, and resets the number
	 * of strokes to ignore. It also checks to see if the player is out of
	 * lives, and if so sets the game over boolean to be true and ends the game.
	 */
	public void reset(){
		if (moveTimer != null) moveTimer.stop();
		mySnake = new Snake(MAX_WIDTH/2, MAX_HEIGHT/2, MAX_WIDTH, MAX_HEIGHT);
		moveSpeed = mySnake.getList().size();
		moveTimer = new Timer(1000/moveSpeed, new MoveTimerHelper());
		moveTimer.start();
		shrooms = new ArrayList<Mushroom>();

		ignoreStrokes = 0;
		
		if (lives <= 0){
			gameOver = true;
			endGame();
		}
	}

	public Timer getMoveTimer() {
		return moveTimer;
	}

	public Timer getMushroomTimer() {
		return mushroomTimer;
	}

	/**
	 * The plant mushroom function finds a random number between 0 and 200, and if 
	 * that number is less than the birth rate of the snake it selects a number between
	 * 0 and 5 and plants a mushroom accordingly. It has a higher chance of planting
	 * a green mushroom than any other color, as those are the only ones that are
	 * 'good' for the player to eat, and so some balancing was necessary.
	 */
	private void plantMushroom(){
		if (rand.nextInt(200) < this.BIRTH_RATE){
			int plant = rand.nextInt(5);

			Mushroom mush = null;

			switch (plant){
			case 0:
				mush = new GreenMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);

				for (int count = 0; count < mySnake.getList().size(); count++){
					if (count < shrooms.size()){
						/*
						 * These while functions make sure that the mushrooms are not spawned on top of any
						 * other mushroom or on top of the snake itself.
						 */
						while (mush.contains(mySnake.getList().get(count)) || mush.contains(shrooms.get(count))){
							mush = new GreenMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					} else {
						while (mush.contains(mySnake.getList().get(count))){
							mush = new GreenMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					}
				}
				break;
			case 1:
				mush = new RedMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);

				for (int count = 0; count < mySnake.getList().size(); count++){
					if (count < shrooms.size()){
						while (mush.contains(mySnake.getList().get(count)) || mush.contains(shrooms.get(count))){
							mush = new RedMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					} else {
						while (mush.contains(mySnake.getList().get(count))){
							mush = new RedMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					}
				}
				break;
			case 2:
				mush = new PurpleMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);

				for (int count = 0; count < mySnake.getList().size(); count++){
					if (count < shrooms.size()){
						while (mush.contains(mySnake.getList().get(count)) || mush.contains(shrooms.get(count))){
							mush = new PurpleMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					} else {
						while (mush.contains(mySnake.getList().get(count))){
							mush = new PurpleMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					}
				}
				break;
			case 3:
				mush = new BlueMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);

				for (int count = 0; count < mySnake.getList().size(); count++){
					if (count < shrooms.size()){
						while (mush.contains(mySnake.getList().get(count)) || mush.contains(shrooms.get(count))){
							mush = new BlueMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					} else {
						while (mush.contains(mySnake.getList().get(count))){
							mush = new BlueMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					}
				}
				break;
			default:
				mush = new GreenMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);

				for (int count = 0; count < mySnake.getList().size(); count++){
					if (count < shrooms.size()){
						while (mush.contains(mySnake.getList().get(count)) || mush.contains(shrooms.get(count))){
							mush = new GreenMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					} else {
						while (mush.contains(mySnake.getList().get(count))){
							mush = new GreenMushroom(rand.nextInt(MAX_WIDTH/10)*10, rand.nextInt(MAX_HEIGHT/10)*10);
						}
					}
				}
				break;
			}

			shrooms.add(mush);	//it adds the crated mushroom to the list of mushrooms
		}
	}

	//	public void setBorders(int width, int height){
	//		this.width = width;
	//		this.height = height;
	//		mySnake = new Snake(width/2, height/2, width, height);
	//	}

	/**
	 * It has four functions that let the snake know that it should
	 * be changing directions, and will ignore strokes if necessary.
	 */
	public void upPressed(){
		if (ignoreStrokes > 0){
			//ignoreStrokes--;
			return;
		} else {
			mySnake.goUp();
		}
	}

	public void downPressed(){
		if (ignoreStrokes > 0){
			//ignoreStrokes--;
			return;
		} else {
			mySnake.goDown();
		}
	}

	public void leftPressed(){
		if (ignoreStrokes > 0){
			//ignoreStrokes--;
			return;
		} else {
			mySnake.goLeft();
		}
	}

	public void rightPressed(){
		if (ignoreStrokes > 0){
			//ignoreStrokes--;
			return;
		} else {
			mySnake.goRight();
		}
	}
	
	/**
	 * The end game function opens up an input dialog
	 * with the list of high scores on it and asks the player
	 * to input their initials. It then calls the checkIfHighScore
	 * function to see if the player has a high score, and whether
	 * they do or not it writes the new list of high scores to the
	 * file.
	 */
	public void endGame(){		
		String inits = 
				JOptionPane.showInputDialog(
						"High Scores:\n"
						+scores[9].toString()
						+scores[8].toString()
						+scores[7].toString()
						+scores[6].toString()
						+scores[5].toString()
						+scores[4].toString()
						+scores[3].toString()
						+scores[2].toString()
						+scores[1].toString()
						+scores[0].toString()
						, "");
		
		checkIfHighScore(inits);
		
		try{
			this.writeScoresToFile(file);
		} catch (Exception e){
			
		}
	}
	
	/**
	 * The check if high score function takes in the initials of the
	 * player so that it can create a new high score object if necessary, 
	 * then it goes through the list of high scores the game has a handle
	 * on, and if the player has a score that is high enough to be on the list
	 * it fits it into the list in the proper place and bumps the other high scores
	 * down a notch, dropping the bottom one.
	 * @param inits
	 */
	private void checkIfHighScore(String inits){
		for(int count = 0; count < scores.length; count++){
			if (count == 9 && points > scores[count].getScore()){	//If the player has a score higher than the highest score
				for (int counter = 0; counter < 8; counter++){
					scores[counter] = scores[counter++];	//bumps down the other scores
				}
				scores[9] = new HighScore(inits, points);	//creates a new high score for the player on the lsit
			} else if (points > scores[count].getScore() && points <= scores[1+count].getScore()){	//if the playe rhas a score that is higher than one score but lower than or equal to the next score
				for (int counter = 0; counter < count-1; counter++){
					scores[counter] = scores[counter++];	//bumps down the other scores
				}
				scores[count] = new HighScore(inits, points);
			}
		}
	}

	public HighScore[] getScores() {
		return scores;
	}

	public File getFile() {
		return file;
	}

	/**
	 * The drawOn function sets the graphics to be white if the snake
	 * is not frozen, and if they are it draws the snake as cyan instead
	 * to represent that. It then draws all of the mushrooms in its list.
	 * @param g
	 */
	public void drawOn(Graphics g){
		if (ignoreStrokes > 0){
			g.setColor(Color.cyan); 	//to show the player that they are frozen
		} else {
			g.setColor(Color.white);
		}
		mySnake.drawOn(g);
		for (Mushroom m : shrooms){
			m.drawOn(g);
		}
	}
	
	/**
	 * The read scores from file function looks at a file of high scores and
	 * fills the list of high scores found in the game with these high scores.
	 * @param f
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void readScoresFromFile(File f) throws IOException, FileNotFoundException{
		BufferedReader br = null;
		try{
			FileReader fr = new FileReader(f);
			br = new BufferedReader(fr);
			
			String str = br.readLine();
			
			for (int count = 0; count < scores.length; count++){
				scores[9-count] = new HighScore(str, SEPARATOR);
				str = br.readLine();
			}
		} catch (Exception ex){
			System.err.println("Trouble with file: "+ex.getMessage());
		} finally {
			try{
				if (br != null){
					br.close();
				}
			} catch (Exception ex1){
				ex1.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	/**
	 * The write scores to file function writes down the list of high
	 * scores that the game has a handle on back to the file, replacing
	 * whatever was there before.
	 * @param f
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void writeScoresToFile(File f) throws IOException, FileNotFoundException{
		BufferedWriter bw = null;
		try{
			FileWriter fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			
			for (int count = 0; count < scores.length; count++){
				bw.write(scores[9-count].getInitials()+SEPARATOR+scores[9-count].getScore());
				bw.newLine();
			}
		} catch (Exception ex){
			System.err.println("Trouble with file: " +ex.getMessage());
		} finally {
			try{
				if (bw != null){
					bw.close();
				}
			} catch (Exception ex1) {
				ex1.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public Snake getMySnake() {
		return mySnake;
	}

	public void setMySnake(Snake mySnake) {
		this.mySnake = mySnake;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public ArrayList<Mushroom> getShrooms(){
		return this.shrooms;
	}

	public void setStrokes(int strokes){
		this.ignoreStrokes = strokes;
	}
	
	public boolean isGameOver(){
		return gameOver;
	}
	
	public int getSpeed(){
		return this.moveSpeed;
	}

	/**
	 * The move timer helper function implements action listener
	 * and tells the snake to move every time the timer goes off. Then,
	 * if the snake is off of the screen or eating itself it decrements
	 * the lives of the player and resets the game. It also looks to see 
	 * if the head of the snake is overlapping with any mushrooms and if so
	 * consumes those mushrooms, causing the appropriate effect. It also decrements
	 * the ignore strokes integer, so that the snake moves closer to unfreezing. This
	 * can go below zero, as it is set by the blue mushroom when consumed, rather than
	 * adding to it.
	 * @author Jacob
	 *
	 */
	private class MoveTimerHelper implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				mySnake.move();
			} catch (OffScreenException ex){
				System.out.println("Off the screen!");
				reset();
				lives--;
			} catch (OverlapException ex){
				System.out.println("You're eating yourself!");
				reset();
				lives--;
			}
			Segment head = mySnake.getList().getHead();
			for(Mushroom mush: shrooms){
				if (mush.contains(head)){
					mush.whenConsumed(GameController.this, mySnake); 
					return;
				}
			}
			
			ignoreStrokes--;	
		}
	}

	/**
	 * The mushroom timer helper goes off whenever the mushroom timer does. 
	 * It tells the game to plant a mushroom, which is explained in the
	 * plant mushroom function.
	 * @author Jacob
	 *
	 */
	private class MushroomTimerHelper implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//if (rand.nextInt(200) < BIRTH_RATE){
			plantMushroom();
			//}
		}

	}

}
