//CS 342 - SPRING 2016
//Project 5: Tetris
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) Nikolay Zakharov (nzakha2)

//Class: Tetromino_O.java
//Responsibility: Respresent the O Tetromino

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

@SuppressWarnings("serial")
public class Tetromino_O extends Tetromino{
	public Timer timer;
	private static final int SIZE = 25;
	private int pausePressed;
	
	//Constructor
	public Tetromino_O(int delay){
		super();
		setPreferredSize(new Dimension(Main.COLS*SIZE, Main.ROWS*SIZE));
		Grid.getInstance().resetPiece();
		Grid.getInstance().setType(3);
		Grid.getInstance().setPiece(Grid.getInstance().getType());
		Grid.getInstance().setDirection(0);

		pausePressed = 0;
		
		timer = new Timer(delay, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(Grid.getInstance().canMoveDown()){
					Grid.getInstance().pieceDown();
				}
				repaint();
			}
		});
		timer.start();
		
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){

				case KeyEvent.VK_LEFT:
					if(Grid.getInstance().canMoveLeft()){
						timer.restart();
						Grid.getInstance().pieceLeft();
						repaint();
					}
					break;
					
				case KeyEvent.VK_DOWN:
					if(Grid.getInstance().canMoveDown()){
						timer.restart();
						Grid.getInstance().pieceDown();
						repaint();
					}
					break;
					
				case KeyEvent.VK_RIGHT:
					if(Grid.getInstance().canMoveRight()){
						timer.restart();
						Grid.getInstance().pieceRight();
						repaint();
					}
					
					break;
					
				case KeyEvent.VK_SPACE:
					timer.setDelay(1);
					break;
					
				case KeyEvent.VK_R:
					pausePressed = (pausePressed + 1) % 2;
					if(pausePressed == 1)
						timer.stop();
					else
						timer.start();
					break;
					
				default:
					break;
				}
			}
		});
	}
	
	//Function to get the Timer object
	public Timer getTimer(){
		return timer;
	}
	
	//Function to paint graphic
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		boolean[][] tiles = new boolean[Main.ROWS][Main.COLS];
		for(int i = 0; i < Main.ROWS; i++)
			tiles[i] = Arrays.copyOf(Grid.getInstance().getTiles()[i], Grid.getInstance().getTiles()[i].length);
		for(int i = 0; i < Main.ROWS; i++){
			for(int j = 1; j < Main.COLS + 1; j++){
				if(tiles[i][j]){
					g.setColor(Color.CYAN);
					g.fillRect((j - 1)*SIZE + 1, i*SIZE + 1, SIZE - 2, SIZE - 2);
				}else{
					g.setColor(Color.GRAY);
					g.fillRect((j - 1)*SIZE + 1, i*SIZE + 1, SIZE - 2, SIZE - 2);
				}
			}
		}
		int[] xP = Arrays.copyOf(Grid.getInstance().getXPos(), Grid.getInstance().getXPos().length);
		int[] yP = Arrays.copyOf(Grid.getInstance().getYPos(), Grid.getInstance().getYPos().length);
		for(int i = 0; i < Grid.getInstance().getXPos().length; i++){
			g.setColor(Color.CYAN);
			g.fillRect((xP[i] - 1)*SIZE + 1, yP[i]*SIZE + 1, SIZE - 2, SIZE - 2);
		}
	}

}
