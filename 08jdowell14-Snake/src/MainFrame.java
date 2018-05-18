import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;

/**
 * The main frame is the window that the game takes place in, and it extends JFrame.
 * It has a game controller with which it fills the game's universe. It also has a legend
 * on the side represented by the legendPanel, which has some simple descriptions of what the
 * four kinds of mushrooms do when eaten. It has a private canvas class that paints the objects
 * in the game unless the game is over in which case it will only paint a black screen. Finally, 
 * it has a panel at the bottom that has the player's lives, points, and buttons to pause and
 * quit the game. It also contains a timer that tells the frame to repaint that goes off fifteen
 * times a second.
 * @author Jacob
 *
 */
public class MainFrame extends JFrame {
	private GameController myController;
	private Canvas gamePanel;

	private Timer paintTimer;
	private JLabel lblPoints;
	private JLabel lblLivesleft;
	private JLabel lblGameOver;


	/**
	 * The constructor sets up the various panels, creates the
	 * game controller, sets up and starts the paint timer, and
	 * sets up the listeners for the timer and the two buttons.
	 * @throws HeadlessException
	 */
	public MainFrame() throws HeadlessException {
		this.myController = new GameController();

		setSize(new Dimension(400, 400));
		setResizable(false);		//not resizable so that the game can easily know how large the canvas is

		/*
		 * Setting up the legend panel and giving it the five mushroom effects
		 */
		JPanel legendPanel = new JPanel();
		legendPanel.setMinimumSize(new Dimension(300, 10));
		legendPanel.setMaximumSize(new Dimension(300, 300));
		getContentPane().add(legendPanel, BorderLayout.EAST);
		legendPanel.setLayout(new GridLayout(2, 1, 0, 0));	//this was an easy way to get the text to the top of the panel

		JPanel mushPanel = new JPanel();
		legendPanel.add(mushPanel);
		mushPanel.setLayout(new GridLayout(5, 1, 0, 0));	//a grid layout so the text is easy to read

		JLabel lblMush0 = new JLabel("Mushrooms:");
		mushPanel.add(lblMush0);

		JLabel lblMush1 = new JLabel("Green grows");
		mushPanel.add(lblMush1);

		JLabel lblMush2 = new JLabel("Red kills");
		mushPanel.add(lblMush2);

		JLabel lblMush3 = new JLabel("Blue stupifies");
		mushPanel.add(lblMush3);

		JLabel lblMush4 = new JLabel("Purple speeds");
		mushPanel.add(lblMush4);

		/*
		 * Creating the display of the points and lives, as well
		 * as the two buttons. Is a flow layout for efficiency of space.
		 */
		JPanel displayPanel = new JPanel();
		getContentPane().add(displayPanel, BorderLayout.SOUTH);
		displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblPoints = new JLabel("Points: "+this.myController.getPoints());
		displayPanel.add(lblPoints);

		lblLivesleft = new JLabel("Lives: "+this.myController.getLives());
		displayPanel.add(lblLivesleft);
		
		JButton btnPause = new JButton("Pause");		//the pause button tells the three timers to stop if they are running and vice versa
		btnPause.setFocusable(false);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (paintTimer.isRunning()){
					paintTimer.stop();
					myController.getMoveTimer().stop();
					myController.getMushroomTimer().stop();
				} else {
					paintTimer.start();
					myController.getMoveTimer().start();
					myController.getMushroomTimer().start();
				}
				
			}
		});
		displayPanel.add(btnPause);

		JButton btnQuit = new JButton("Quit");		//the quit button exits the game
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		displayPanel.add(btnQuit);

		lblGameOver = new JLabel();		//this label tells the user clearly that the game is over when they run out of lives
		if (myController.isGameOver()){
			lblGameOver.setText("Game Over");
		}
		displayPanel.add(lblGameOver);

		/*
		 * creating the canvas, setting it focusable, and giving it a key helper object
		 */
		gamePanel = new Canvas();
		gamePanel.setFocusable(true);
		gamePanel.setRequestFocusEnabled(true);

		gamePanel.addKeyListener(new KeyHelper());
		gamePanel.setPreferredSize(new Dimension(300, 300));
		gamePanel.setMinimumSize(new Dimension(300, 300));
		gamePanel.setMaximumSize(new Dimension(300, 300));
		gamePanel.setBackground(Color.BLACK);
		getContentPane().add(gamePanel, BorderLayout.CENTER);

		/*
		 * creating the paint timer and giving it a timer helper object 
		 */
		paintTimer = new Timer(1000/15, new TimerHelper());
		paintTimer.start();
	}

	



	/**
	 * The main function creates a MainFrame, sets the default close 
	 * operation, and sets it to be visible.
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame main = new MainFrame();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}

	/**
	 * The canvas object acts exactly like a JPanel, except the paint
	 * function draws the game state, with a black rectangle over the back
	 * and the game objects in front. If the game is over, it tells the timers
	 * to stop and sets the game over label text to be game over.
	 * @author Jacob
	 *
	 */
	private class Canvas extends JPanel{
		@Override
		public void paint(Graphics g){
			super.paint(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.WIDTH, this.HEIGHT);
			
			if (! myController.isGameOver()){
				myController.drawOn(g);
			} else {
				lblGameOver.setText("Game Over");
				paintTimer.stop();
				myController.getMoveTimer().stop();
				myController.getMushroomTimer().stop();
			}
		}
	}

	/**
	 * The private timer helper class implements actionListener
	 * and is attached to the paint timer. It tells the canvas to
	 * repaint and updates the lives and points of the player.
	 * @author Jacob
	 *
	 */
	private class TimerHelper implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
			lblLivesleft.setText("Lives: "+myController.getLives());
			lblPoints.setText("Points: "+myController.getPoints());
		}

	}

	/**
	 * The private key helper class implements keyListener and checks
	 * to see if the user has pushed one of the arrow keys to change
	 * the direction of the snake. When a key is pressed, it tells
	 * the controller that the respective key has been pushed and it
	 * lets the snake know that it should change direction.
	 * @author Jacob
	 *
	 */
	private class KeyHelper implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()){
			case KeyEvent.VK_UP:
				myController.downPressed();	//the up and down functions are switched because the y is upside down
				break;
			case KeyEvent.VK_DOWN:
				myController.upPressed();
				break;
			case KeyEvent.VK_LEFT:
				myController.leftPressed();
				break;
			case KeyEvent.VK_RIGHT:
				myController.rightPressed();
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
