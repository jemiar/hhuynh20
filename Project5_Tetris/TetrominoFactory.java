
public class TetrominoFactory {
	public Tetromino getTetromino(int t){
		switch(t){
		case 1:
			return new Tetromino_I();
			
		case 2:
			return new Tetromino_T();
			
		case 3:
			return new Tetromino_O();
		
		case 4:
			return new Tetromino_L();
			
		case 5:
			return new Tetromino_J();
			
		case 6:
			return new Tetromino_S();
			
		case 7:
			return new Tetromino_Z();
		
		default:
			return null;
		}
	}
}
