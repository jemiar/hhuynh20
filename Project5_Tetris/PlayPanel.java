
public class PlayPanel {
	private static PlayPanel board = null;
	private boolean[][] tiles = new boolean[Main.ROWS][Main.COLS];
	
	private PlayPanel(){
		for(int i = 0; i < Main.ROWS; i++)
			for(int j = 0; j < Main.COLS; j++)
				tiles[i][j] = false;
	}
	
	public static synchronized PlayPanel getInstance(){
		if(board == null)
			board = new PlayPanel();
		return board;
	}
	
	public boolean[][] getTiles(){
		return tiles;
	}
	
	public void setTiles(boolean[][] t){
		tiles = t;
	}
}
