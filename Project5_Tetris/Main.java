import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class Main {
	
	public static final int ROWS = 20;
	public static final int COLS = 10;

	public static void main(String[] args) {
		JFrame app = new JFrame("Tetris 1.0");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Random r = new Random();
		TetrominoFactory factory = new TetrominoFactory();

		while(true){
			int type = r.nextInt(7) + 1;
			Tetromino tetro = factory.getTetromino(type);
			tetro.setFocusable(true);
			app.add(tetro, BorderLayout.CENTER);
			app.setResizable(false);
			app.pack();
			app.setVisible(true);
			while(Grid.getInstance().canMoveDown()){
				tetro.requestFocusInWindow();
			}
			tetro.getTimer().stop();
			app.remove(tetro);
			Grid.getInstance().setTiles();
		}
	}

}