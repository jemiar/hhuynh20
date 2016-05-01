//CS 342 - SPRING 2016
//Project 5: Tetris
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) Nikolay Zakharov (nzakha2)

//Class: TetrominoFactory.java
//Responsibility: used to produce Tetromino objects. Factory design pattern

public class TetrominoFactory {
	public Tetromino getTetromino(int t, int delay){
		switch(t){
		case 1:
			return new Tetromino_I(delay);
			
		case 2:
			return new Tetromino_T(delay);
			
		case 3:
			return new Tetromino_O(delay);
		
		case 4:
			return new Tetromino_L(delay);
			
		case 5:
			return new Tetromino_J(delay);
			
		case 6:
			return new Tetromino_S(delay);
			
		case 7:
			return new Tetromino_Z(delay);
		
		default:
			return null;
		}
	}
}
