import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

public class Main {
	
	public static final int ROWS = 20;
	public static final int COLS = 10;
	public static final boolean[] fullLine = {true, true, true, true, true, true,
												true, true, true, true, true, true};
	public static final boolean[] emptyLine = {true, false, false, false, false, false,
												false, false, false, false, false, true};

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
			
			int min = Grid.getInstance().getMinY();
			int[] temp = Arrays.copyOf(Grid.getInstance().getYPos(), Grid.getInstance().getYPos().length);
			Arrays.sort(temp);
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
			//To update score, you can use the size of the arraylist (how many full line?)
			if(lineClear.size() > 0){
				for(int i = 0; i < lineClear.size(); i++){
					int line = lineClear.get(i);
					Grid.getInstance().drop(line);
				}
			}
		}
	}

}
