import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

@SuppressWarnings("serial")
public class Tetromino_S extends Tetromino{
	public Timer timer;
	private static final int SIZE = 25;
	
	public Tetromino_S(){
		super();
		setPreferredSize(new Dimension(Main.COLS*SIZE, Main.ROWS*SIZE));
		Grid.getInstance().resetPiece();
		Grid.getInstance().setType(6);
		Grid.getInstance().setPiece(Grid.getInstance().getType());
		Grid.getInstance().setDirection(0);

		
		timer = new Timer(1000, new ActionListener(){
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
				case KeyEvent.VK_A:
					if(Grid.getInstance().canRotateLeft(Grid.getInstance().getType())){
						timer.restart();
						Grid.getInstance().rotateLeft(Grid.getInstance().getType());
						repaint();
					}
					break;
				case KeyEvent.VK_D:
					if(Grid.getInstance().canRotateRight(Grid.getInstance().getType())){
						timer.restart();
						Grid.getInstance().rotateRight(Grid.getInstance().getType());
						repaint();
					}
					break;

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
					
				default:
					break;
				}
			}
		});
	}
	
	public Timer getTimer(){
		return timer;
	}
	
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
