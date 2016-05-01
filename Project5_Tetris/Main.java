//CS 342 - SPRING 2016
//Project 5: Tetris
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) Nikolay Zakharov (nzakha2)

//Class: Main.java
//Responsibility: where the game is initialized. Setup GUI

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;

public class Main {
	
	//Number of rows
	public static final int ROWS = 20;
	//Number of columns
	public static final int COLS = 10;
	//Represent a full line
	public static final boolean[] fullLine = {true, true, true, true, true, true,
												true, true, true, true, true, true};
	//Represent an empty line
	public static final boolean[] emptyLine = {true, false, false, false, false, false,
												false, false, false, false, false, true};
	//Score
	private static long score = 0;
	//Number of lines cleard
	private static int clearedLineNo = 0;
	//Swing Timer class for manage the elapse time
	private static Timer elapseTime;
	//Time count variable
	private static int time = 0;
	//Used for check if game is over
	private static boolean isGameOver = false;
	//A tetromino object
	private static Tetromino tetro;
	//Check if user clicks restart
	private static boolean reset = false;
	
	//Labels to display info of the game
	private static JLabel scoreLabel;
	private static JLabel lineLabel;
	private static JLabel levelLabel;
	private static JLabel timeLabel;
	private static JLabel statusLabel;
	private static JPanel playPanel;

	public static void main(String[] args) {
		//Setup the frame
		JFrame app = new JFrame("Tetris 1.0");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setLayout(new GridLayout(1, 2));
		
		//Setup the menu
		JMenu menu = new JMenu("File");
		menu.setMnemonic('F');
		
		//Setup menu item
		JMenuItem restart = new JMenuItem("resTart");
		restart.setMnemonic('T');
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//When user clicks the restart button, drop down the current tetromino
				//And clear the grid
				//Then reset the info: score, number of lines cleared, level, time, status of the game
				tetro.getTimer().setDelay(1);
				reset = true;
				score = 0;
				clearedLineNo = 0;
				time = 0;
				scoreLabel.setText(" Score: " + score);
				lineLabel.setText(" Number of lines cleared: " + clearedLineNo);
				levelLabel.setText(" Level: " + ((clearedLineNo / 10) + 1));
				if(isGameOver){
					isGameOver = false;
				}
				if(!elapseTime.isRunning())
					elapseTime.start();
				timeLabel.setText(" Elapsed time: " + time);
				statusLabel.setText(" Game playing");
				
			}
		});
		
		//About menu item
		JMenuItem about = new JMenuItem("About");
		about.setMnemonic('A');
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String h = "Tetris 1.0\n"
						+ "Created by Hoang Minh Huynh Nguyen and Nikolay Zakharov.\n"
						+ "GNU License. 2016";
				JOptionPane.showMessageDialog(null, h);
			}
		});
		
		//Help menu item
		JMenuItem help = new JMenuItem("HeLp", 'L');
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String h = "Left arrow for move left.\n"
						+ "Right arrow for move right.\n"
						+ "Down arrow for soft move down.\n"
						+ "Space for hard drop down.\n"
						+ "A for rotate left.\n"
						+ "D for rotate rigt.\n"
						+ "R for pause.\n"
						+ "R again for continue.\n";
				JOptionPane.showMessageDialog(null, h);
			}
		});
		
		//Quit menu item
		JMenuItem quit = new JMenuItem("Quit");
		quit.setMnemonic('Q');
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int quit = JOptionPane.showConfirmDialog(null, "Are you sure to quit the program?", "", JOptionPane.YES_NO_OPTION);
				if(quit == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		
		//Add menu item into menu
		menu.add(restart);
		menu.add(about);
		menu.add(help);
		menu.add(quit);
		
		//Add menu into frame
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		app.setJMenuBar(menuBar);
		
		//Panel to display info
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(11, 1));
		
		//Score label
		scoreLabel = new JLabel();
		scoreLabel.setText(" Score: " + score);
		infoPanel.add(scoreLabel);
		
		//Number of lines cleared label
		lineLabel = new JLabel();
		lineLabel.setText(" Number of lines cleared: " + clearedLineNo);
		infoPanel.add(lineLabel);
		
		//Level label
		levelLabel = new JLabel();
		levelLabel.setText(" Level: " + ((clearedLineNo / 10) + 1));
		infoPanel.add(levelLabel);
		
		//Elapse time label
		timeLabel = new JLabel();
		timeLabel.setText(" Elapsed time: " + time);
		infoPanel.add(timeLabel);
		
		//Status of the game label
		statusLabel = new JLabel();
		statusLabel.setText(" Game playing");
		infoPanel.add(statusLabel);
		
		//Button to move tetromino left
		JButton leftButton = new JButton("Move Left");
		leftButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(Grid.getInstance().canMoveLeft()){
					tetro.getTimer().restart();
					Grid.getInstance().pieceLeft();
					tetro.repaint();
				}
			}
		});
		infoPanel.add(leftButton);
		
		//Button to move tetromino right
		JButton rightButton = new JButton("Move Right");
		rightButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(Grid.getInstance().canMoveRight()){
					tetro.getTimer().restart();
					Grid.getInstance().pieceRight();
					tetro.repaint();
				}
			}
		});
		infoPanel.add(rightButton);
		
		//Button to move tetromino down (soft drop)
		JButton downButton = new JButton("Move Down");
		downButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(Grid.getInstance().canMoveDown()){
					tetro.getTimer().restart();
					Grid.getInstance().pieceDown();
					tetro.repaint();
				}
			}
		});
		infoPanel.add(downButton);
		
		//Button to hard drop the tetromino
		JButton hardDown = new JButton("Hard Drop");
		hardDown.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tetro.getTimer().setDelay(1);
			}
		});
		infoPanel.add(hardDown);
		
		//Button to rotate left
		JButton rotateLeft = new JButton("Rotate Left");
		rotateLeft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(Grid.getInstance().canRotateLeft(Grid.getInstance().getType())){
					tetro.getTimer().restart();
					Grid.getInstance().rotateLeft(Grid.getInstance().getType());
					tetro.repaint();
				}
			}
		});
		infoPanel.add(rotateLeft);
		
		//Button to rotate right
		JButton rotateRight = new JButton("Rotate Right");
		rotateRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(Grid.getInstance().canRotateRight(Grid.getInstance().getType())){
					tetro.getTimer().restart();
					Grid.getInstance().rotateRight(Grid.getInstance().getType());
					tetro.repaint();
				}
			}
		});
		infoPanel.add(rotateRight);
		
		//Add the info panel into the frame
		app.add(infoPanel);
		
		//Initialize the timer
		elapseTime = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				time ++;
				timeLabel.setText(" Elapsed time: " + time);
			}
		});
		elapseTime.start();
		
		//Play panel to add the board
		playPanel = new JPanel();
		playPanel.setPreferredSize(new Dimension(COLS*25, ROWS*25 + 10));
		app.add(playPanel);
		app.setResizable(false);
		app.pack();
		app.setVisible(true);
		
		//Random number generator
		Random r = new Random();
		//Tetromino factory to produce tetrominos
		TetrominoFactory factory = new TetrominoFactory();

		while(!isGameOver){
			//Get a random number
			int type = r.nextInt(7) + 1;
			//Produce a tetromino with corresponding type and speed of falling down based on level
			tetro = factory.getTetromino(type, 1000 - (clearedLineNo / 10 + 1)*50);
			//Add the tetromino to the panel
			playPanel.add(tetro, BorderLayout.CENTER);
			tetro.setFocusable(true);
			
			//When the tetromino can still move down, make it focusable
			while(Grid.getInstance().canMoveDown()){
				tetro.requestFocusInWindow();
			}
			
			//when the tetromino is locked into place, stop its timer, remove its from the panel
			//and set the matrix of the grid
			tetro.getTimer().stop();
			playPanel.remove(tetro);
			Grid.getInstance().setTiles();
			
			//Check if the user clicks Restart to clear the grid
			if(reset){
				Grid.getInstance().resetGrid();
				reset = false;
			}
			
			//get the current height of the pile
			int min = Grid.getInstance().getMinY();
			int[] temp = Arrays.copyOf(Grid.getInstance().getYPos(), Grid.getInstance().getYPos().length);
			Arrays.sort(temp);
			//Compare the current height with the position of the current tetromino
			//If there is a new height, update the height of the pile
			if(temp[0] <= min)
				Grid.getInstance().setMinY(temp[0]);
			
			//After setting the piece in place, check if there are any full lines
			//If there are, add to the arrayList lineClear
			ArrayList<Integer> lineClear = new ArrayList<Integer>();
			for(int i = temp[0]; i <= temp[3]; i++){
				if(Arrays.equals(Grid.getInstance().getTiles()[i], fullLine))
					lineClear.add(i);
			}
			
			//Loop through the arraylist to clear line
			if(lineClear.size() > 0){
				for(int i = 0; i < lineClear.size(); i++){
					int line = lineClear.get(i);
					Grid.getInstance().drop(line);
				}
				
				//Update the score and level based on the number of lines cleared
				switch(lineClear.size()){
				case 1:
					score += 40*(clearedLineNo / 10 + 1);
					scoreLabel.setText(" Score: " + score);
					clearedLineNo += 1;
					lineLabel.setText(" Number of lines cleared: " + clearedLineNo);
					levelLabel.setText(" Level: " + ((clearedLineNo / 10) + 1));
					break;
					
				case 2:
					score += 100*(clearedLineNo / 10 + 1);
					scoreLabel.setText(" Score: " + score);
					clearedLineNo += 2;
					lineLabel.setText(" Number of lines cleared: " + clearedLineNo);
					levelLabel.setText(" Level: " + ((clearedLineNo / 10) + 1));
					break;
					
				case 3:
					score += 300*(clearedLineNo / 10 + 1);
					scoreLabel.setText(" Score: " + score);
					clearedLineNo += 3;
					lineLabel.setText(" Number of lines cleared: " + clearedLineNo);
					levelLabel.setText(" Level: " + ((clearedLineNo / 10) + 1));
					break;
					
				case 4:
					score += 1200*(clearedLineNo / 10 + 1);
					scoreLabel.setText(" Score: " + score);
					clearedLineNo += 4;
					lineLabel.setText(" Number of lines cleared: " + clearedLineNo);
					levelLabel.setText(" Level: " + ((clearedLineNo / 10) + 1));
					break;
					
				default:
					break;
				}
			}
			
			//Check if the new height reach the top of the board
			//If yes, set game over
			if(Grid.getInstance().getMinY() == 0){
				statusLabel.setText(" Game Over");
				elapseTime.stop();
				isGameOver = true;
			}
			
		}
	}

}
