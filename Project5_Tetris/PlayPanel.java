import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayPanel extends JPanel{
	private boolean[][] tilesFill = new boolean[20][10];
	private int currentX;
	private int currentY;
	
	public PlayPanel(){
		super();
		setPreferredSize(new Dimension(300, 600));
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 10; j++)
				tilesFill[i][j] = false;
		}
		tilesFill[0][0] = true;
		currentX = 0;
		currentY = 0;
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:
					if(currentY > 0){
						tilesFill[currentY][currentX] = false;
						currentY -= 1;
						tilesFill[currentY][currentX] = true;
						repaint();
					}
					break;
				case KeyEvent.VK_LEFT:
					if(currentX > 0){
						tilesFill[currentY][currentX] = false;
						currentX -= 1;
						tilesFill[currentY][currentX] = true;
						repaint();
					}
					break;
				case KeyEvent.VK_DOWN:
					if(currentY < 19){
						tilesFill[currentY][currentX] = false;
						currentY += 1;
						tilesFill[currentY][currentX] = true;
						repaint();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(currentX < 9){
						tilesFill[currentY][currentX] = false;
						currentX += 1;
						tilesFill[currentY][currentX] = true;
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
		g.setColor(Color.CYAN);
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 10; j++){
				if(tilesFill[i][j])
					g.fillRect(j*30, i*30, 30, 30);
			}
		}
	}
}
