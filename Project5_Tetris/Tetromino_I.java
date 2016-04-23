import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Tetromino_I extends JPanel {
	private boolean[][] tilesFill = new boolean[Main.ROWS][Main.COLS];
	private int[] xTiles = new int[4];
	private int[] yTiles = new int[4];
	
	public Tetromino_I(boolean[][] t){
		super();
		setPreferredSize(new Dimension(Main.COLS*25, Main.ROWS*25));
		tilesFill = t;
		
		for(int i = 0; i < 4; i++){
			yTiles[i] = 0;
			xTiles[i] = 3 + i;
			tilesFill[yTiles[i]][xTiles[i]] = true;
		}
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:
					if(yTiles[0] > 0){
						for(int i = 0; i < 4; i++){
							tilesFill[yTiles[i]][xTiles[i]] = false;
							yTiles[i] -= 1;
							tilesFill[yTiles[i]][xTiles[i]] = true;
						}
						repaint();
					}
					break;
				case KeyEvent.VK_LEFT:
					if(xTiles[0] > 0){
						for(int i = 0; i < 4; i++)
							xTiles[i] -= 1;
						tilesFill[yTiles[3]][xTiles[3] + 1] = false;
						tilesFill[yTiles[0]][xTiles[0]] = true;
						repaint();
					}
					break;
				case KeyEvent.VK_DOWN:
					if(yTiles[0] < Main.ROWS - 1){
						for(int i = 0; i < 4; i++){
							tilesFill[yTiles[i]][xTiles[i]] = false;
							yTiles[i] += 1;
							tilesFill[yTiles[i]][xTiles[i]] = true;
						}
						repaint();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(xTiles[3] < Main.COLS - 1){
						for(int i = 0; i < 4; i++)
							xTiles[i] += 1;
						tilesFill[yTiles[0]][xTiles[0] - 1] = false;
						tilesFill[yTiles[3]][xTiles[3]] = true;
						repaint();
					}
					break;
				default:
					break;
				}
			}
		});
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i = 0; i < Main.ROWS; i++){
			for(int j = 0; j < Main.COLS; j++){
				if(tilesFill[i][j]){
					g.setColor(Color.CYAN);
					g.fillRect(j*25 + 1, i*25 + 1, 23, 23);
				}else{
					g.setColor(Color.GRAY);
					g.fillRect(j*25 + 1, i*25 + 1, 23, 23);
				}
			}
		}
	}

}
