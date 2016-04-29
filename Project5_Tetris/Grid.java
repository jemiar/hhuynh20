public class Grid {
	private static Grid board = null;
	private boolean[][] tiles = new boolean[Main.ROWS + 3][Main.COLS + 2];
	private int[] xPos = new int[4];
	private int[] yPos = new int[4];
	private int direction;
	private int type;
	
	private Grid(){
		for(int i = 0; i < Main.ROWS; i++)
			for(int j = 1; j < Main.COLS + 1; j++)
				tiles[i][j] = false;
		
		for(int j = 0; j < Main.COLS + 2; j++){
			tiles[Main.ROWS][j] = true;
			tiles[Main.ROWS + 1][j] = true;
			tiles[Main.ROWS + 2][j] = true;
		}
		
		for(int i = 0; i < Main.ROWS + 1; i++){
			tiles[i][0] = true;
			tiles[i][Main.COLS + 1] = true;
		}
	}
	
	public boolean canMoveDown(){
		for(int i = 0; i < 4; i++){
			if(tiles[yPos[i] + 1][xPos[i]])
				return false;
		}
		return true;
	}
	
	public boolean canMoveLeft(){
		if(tiles[yPos[0]][xPos[0] - 1])
			return false;
		return true;
	}
	
	public boolean canMoveRight(){
		if(tiles[yPos[3]][xPos[3] + 1])
			return false;
		return true;
	}
	
	public static synchronized Grid getInstance(){
		if(board == null)
			board = new Grid();
		return board;
	}
	
	public synchronized void setType(int t){
		type = t;
	}
	
	public int getType(){
		return type;
	}
	
	//check if a piece can rotate left, depend on parameter t(type of tetromino)
	//I have create one example below for type I, (t = 1)
	public boolean canRotateLeft(int t){
		boolean b = false;
		switch(t){
		case 1:
			if(direction % 2 == 0){
				if(tiles[yPos[3] + 3][xPos[3] - 2] || tiles[yPos[2] + 2][xPos[2] - 1] || 
						tiles[yPos[1] + 1][xPos[1]])
					b = false;
				else
					b = true;
			}else{
				if(tiles[yPos[0]][xPos[0] - 1] || tiles[yPos[2] - 2][xPos[2] + 1]
						|| tiles[yPos[3] - 3][xPos[3] + 2])
					b = false;
				else
					b = true;
			}
			break;
			
		case 2:
			switch(direction){
			case 0:
				if(tiles[yPos[0] + 2][xPos[0] + 1] || tiles[yPos[3] + 1][xPos[3]])
					b = false;
				else
					b = true;
				break;
			case 1:
				if(tiles[yPos[0] - 1][xPos[0] - 1])
					b = false;
				else
					b = true;
				break;
			case 2:
				if(tiles[yPos[3] + 1][xPos[3] - 1])
					b = false;
				else
					b = true;
				break;
			case 3:
				if(tiles[yPos[0] - 1][xPos[0]] || tiles[yPos[3] - 2][xPos[3] + 1])
					b = false;
				else
					b = true;
				break;
			}
			break;
			
		case 4:
			switch(direction){
			case 0:
				if(tiles[yPos[0] + 1][xPos[0] - 1] || tiles[yPos[2] - 1][xPos[2] + 1] ||
						tiles[yPos[3] - 2][xPos[3]])
					b = false;
				else
					b = true;
				break;
			case 1:
				if(tiles[yPos[0] - 1][xPos[0] - 1] || tiles[yPos[1] + 1][xPos[1] + 1])
					b = false;
				else
					b = true;
				break;
			case 2:
				if(tiles[yPos[0] + 1][xPos[0] - 1] || tiles[yPos[1] - 2][xPos[1] - 2])
					b = false;
				else
					b = true;
				break;
			case 3:
				if(tiles[yPos[1] + 1][xPos[1] + 1] || tiles[yPos[2] + 2][xPos[2]] ||
						tiles[yPos[3] + 2][xPos[3]])
					b = false;
				else
					b = true;
				break;
			}
			break;
			
		case 5:
			switch(direction){
			case 0:
				if(tiles[yPos[0] - 1][xPos[0]] || tiles[yPos[1] - 1][xPos[1] + 1] ||
						tiles[yPos[3] + 2][xPos[3] + 1])
					b = false;
				else
					b = true;
				break;
			case 1:
				if(tiles[yPos[1] + 1][xPos[1] - 1] || tiles[yPos[2] - 1][xPos[2] - 2] ||
						tiles[yPos[3] - 2][xPos[3] - 1])
					b = false;
				else
					b = true;
				break;
			case 2:
				if(tiles[yPos[2] + 2][xPos[2] + 1] || tiles[yPos[3] + 2][xPos[3] + 1])
					b = false;
				else
					b = true;
				break;
			case 3:
				if(tiles[yPos[0]][xPos[0] + 1] || tiles[yPos[3] - 2][xPos[3] - 1] ||
						tiles[yPos[3] + 2][xPos[3]])
					b = false;
				else
					b = true;
				break;
			}
			break;
			
		case 6:
			if(direction % 2 == 0){
				if(tiles[yPos[0]][xPos[0] + 2] || tiles[yPos[3] + 2][xPos[3]])
					b = false;
				else
					b = true;
			}else{
				if(tiles[yPos[2]][xPos[2] - 2] || tiles[yPos[3] - 2][xPos[3]])
					b = false;
				else
					b = true;
			}
			break;
			
		case 7:
			if(direction % 2 == 0){
				if(tiles[yPos[0] + 2][xPos[0]] || tiles[yPos[3]][xPos[3] - 2])
					b = false;
				else
					b = true;
			}else{
				if(tiles[yPos[0] - 2][xPos[0]] || tiles[yPos[1]][xPos[1] + 2])
					b = false;
				else
					b = true;
			}
			break;
			
		default:
			break;
		}
		return b;
	}
	
	//same as above function
	public boolean canRotateRight(int t){
		boolean b = false;
		switch(t){
		case 1:
			if(direction % 2 == 0){
				if(tiles[yPos[3] + 3][xPos[3] - 2] || tiles[yPos[2] + 2][xPos[2] - 1] || 
						tiles[yPos[1] + 1][xPos[1]])
					b = false;
				else
					b = true;
			}else{
				if(tiles[yPos[0]][xPos[0] - 1] || tiles[yPos[2] - 2][xPos[2] + 1]
						|| tiles[yPos[3] - 3][xPos[3] + 2])
					b = false;
				else
					b = true;
			}
			break;
		case 2:
			switch(direction){
			case 0:
				if(tiles[yPos[0] + 1][xPos[0]] || tiles[yPos[3] + 2][xPos[3] - 1])
					b = false;
				else
					b = true;
				break;
			case 1:
				if(tiles[yPos[0] - 2][xPos[0] - 1] || tiles[yPos[3] - 1][xPos[3]])
					b = false;
				else
					b = true;
				break;
			case 2:
				if(tiles[yPos[0] + 1][xPos[0] + 1])
					b = false;
				else
					b = true;
				break;
			case 3:
				if(tiles[yPos[3] - 1][xPos[3] + 1])
					b = false;
				else
					b = true;
				break;
			}
			break;
			
		case 4:
			switch(direction){
			case 0:
				if(tiles[yPos[0] + 1][xPos[0] - 1] || tiles[yPos[1] - 1][xPos[1] - 1] ||
						tiles[yPos[3] - 2][xPos[3]])
					b = false;
				else
					b = true;
				break;
			case 1:
				if(tiles[yPos[0] - 1][xPos[0] + 1] || tiles[yPos[2] + 1][xPos[2] - 1] ||
						tiles[yPos[3] + 2][xPos[3]])
					b = false;
				else
					b = true;
				break;
			case 2:
				if(tiles[yPos[0] + 1][xPos[0] - 1] || tiles[yPos[1] - 1][xPos[1] - 1])
					b = false;
				else
					b = true;
				break;
			case 3:
				if(tiles[yPos[0] + 1][xPos[0] + 1] || tiles[yPos[1] + 1][xPos[1] + 2])
					b = false;
				else
					b = true;
				break;
			}
			break;
			
		case 5:
			switch(direction){
			case 0:
				if(tiles[yPos[1]][xPos[1] - 1] || tiles[yPos[3] + 2][xPos[3] + 1])
					b = false;
				else
					b = true;
				break;
			case 1:
				if(tiles[yPos[0] - 1][xPos[0] + 1] || tiles[yPos[2] + 1][xPos[2] - 2] ||
						tiles[yPos[3]][xPos[3] - 1])
					b = false;
				else
					b = true;
				break;
			case 2:
				if(tiles[yPos[0] - 1][xPos[0] + 1] || tiles[yPos[2] + 1][xPos[2] + 2] ||
						tiles[yPos[3] + 2][xPos[3] + 1])
					b = false;
				else
					b = true;
				break;
			case 3:
				if(tiles[yPos[2] - 2][xPos[2] - 1] || tiles[yPos[3] - 2][xPos[3] - 1])
					b = false;
				else
					b = true;
				break;
			}
			break;
			
		case 6:
			if(direction % 2 == 0){
				if(tiles[yPos[0]][xPos[0] + 2] || tiles[yPos[3] + 2][xPos[3]])
					b = false;
				else
					b = true;
			}else{
				if(tiles[yPos[2]][xPos[2] - 2] || tiles[yPos[3] - 2][xPos[3]])
					b = false;
				else
					b = true;
			}
			break;
			
		case 7:
			if(direction % 2 == 0){
				if(tiles[yPos[0] + 2][xPos[0]] || tiles[yPos[3]][xPos[3] - 2])
					b = false;
				else
					b = true;
			}else{
				if(tiles[yPos[0] - 2][xPos[0]] || tiles[yPos[1]][xPos[1] + 2])
					b = false;
				else
					b = true;
			}
			break;
			
		default:
			break;
		}
		return b;
	}
	
	//set up initial coordinate of the piece, based on type t
	//I create one example for type I,
	public synchronized void setPiece(int t){
		switch(t){
		case 1:
			for(int i = 0; i < 4; i++){
				yPos[i] = 0;
				xPos[i] = i + 4;
			}
			break;
			
		case 2:
			yPos[0] = 0;
			xPos[0] = 4;
			yPos[1] = 0;
			xPos[1] = 5;
			yPos[2] = 1;
			xPos[2] = 5;
			yPos[3] = 0;
			xPos[3] = 6;
			break;
			
		case 3:
			yPos[0] = 0;
			xPos[0] = 5;
			yPos[1] = 0;
			xPos[1] = 6;
			yPos[2] = 1;
			xPos[2] = 5;
			yPos[3] = 1;
			xPos[3] = 6;
			break;
			
		case 4:
			yPos[0] = 0;
			xPos[0] = 5;
			yPos[1] = 1;
			xPos[1] = 5;
			yPos[2] = 2;
			xPos[2] = 5;
			yPos[3] = 2;
			xPos[3] = 6;
			break;
			
		case 5:
			yPos[0] = 2;
			xPos[0] = 5;
			yPos[1] = 2;
			xPos[1] = 6;
			yPos[2] = 1;
			xPos[2] = 6;
			yPos[3] = 0;
			xPos[3] = 6;
			break;
			
		case 6:
			yPos[0] = 1;
			xPos[0] = 4;
			yPos[1] = 1;
			xPos[1] = 5;
			yPos[2] = 0;
			xPos[2] = 5;
			yPos[3] = 0;
			xPos[3] = 6;
			break;
			
		case 7:
			yPos[0] = 0;
			xPos[0] = 4;
			yPos[1] = 0;
			xPos[1] = 5;
			yPos[2] = 1;
			xPos[2] = 5;
			yPos[3] = 1;
			xPos[3] = 6;
			break;
			
		default:
			break;
		}
	}
	
	public synchronized void setDirection(int d){
		direction = d;
	}
	
	public synchronized void resetPiece(){
		for(int i = 0; i < 4; i++){
			yPos[i] = 0;
			xPos[i] = 0;
		}
	}
	
	public synchronized void pieceDown(){
		for(int i = 0; i < 4; i++){
			yPos[i] += 1;
		}
	}
	
	public synchronized void pieceLeft(){
		for(int i = 0; i < 4; i++){
			xPos[i] -= 1;
		}
	}
	
	public synchronized void pieceRight(){
		for(int i = 0; i < 4; i++){
			xPos[i] += 1;
		}
	}
	
	//rotate left function, based on type t
	public synchronized void rotateLeft(int t){
		switch(t){
		case 1:
			switch(direction){
			case 0:
				xPos[0] += 1;
				yPos[1] += 1;
				xPos[2] -= 1;
				yPos[2] += 2;
				xPos[3] -= 2;
				yPos[3] += 3;
				direction = 1;
				break;
				
			case 1:
				xPos[0] -= 1;
				yPos[1] -= 1;
				xPos[2] += 1;
				yPos[2] -= 2;
				xPos[3] += 2;
				yPos[3] -= 3;
				direction = 2;
				break;
				
			case 2:
				xPos[0] += 1;
				yPos[1] += 1;
				xPos[2] -= 1;
				yPos[2] += 2;
				xPos[3] -= 2;
				yPos[3] += 3;
				direction = 3;
				break;
				
			case 3:
				xPos[0] -= 1;
				yPos[1] -= 1;
				xPos[2] += 1;
				yPos[2] -= 2;
				xPos[3] += 2;
				yPos[3] -= 3;
				direction = 0;
				break;
				
			default:
				break;
			}
			break;
			
		case 2:
			switch(direction){
			case 0:
				yPos[0] += 2;
				xPos[0] += 1;
				yPos[3] += 1;
				direction = 1;
				break;
				
			case 1:
				yPos[0] -= 1;
				xPos[0] -= 1;
				direction = 2;
				break;
				
			case 2:
				yPos[3] += 1;
				xPos[3] -= 1;
				direction = 3;
				break;
				
			case 3:
				yPos[0] -= 1;
				yPos[3] -= 2;
				xPos[3] += 1;
				direction = 0;
				break;
				
			default:
				break;
			}
			break;
			
		case 4:
			switch(direction){
			case 0:
				yPos[0] += 1;
				xPos[0] -= 1;
				yPos[2] -= 1;
				xPos[2] += 1;
				yPos[3] -= 2;
				direction = 1;
				break;
				
			case 1:
				yPos[0] -= 1;
				xPos[0] += 1;
				yPos[1] += 1;
				xPos[1] += 1;
				direction = 2;
				break;
				
			case 2:
				yPos[0] += 1;
				xPos[0] -= 1;
				yPos[1] -= 2;
				xPos[1] -= 2;
				yPos[2] -= 1;
				xPos[2] -= 1;
				direction = 3;
				break;
				
			case 3:
				yPos[0] -= 1;
				xPos[0] += 1;
				yPos[1] += 1;
				xPos[1] += 1;
				yPos[2] += 2;
				yPos[3] += 2;
				direction = 0;
				break;
				
			default:
				break;
			}
			break;
			
		case 5:
			switch(direction){
			case 0:
				yPos[0] -= 1;
				yPos[1] -= 1;
				xPos[2] += 1;
				yPos[3] += 2;
				xPos[3] += 1;
				direction = 1;
				break;
				
			case 1:
				yPos[0] += 1;
				xPos[1] -= 1;
				yPos[2] -= 1;
				xPos[2] -= 2;
				yPos[3] -= 2;
				xPos[3] -= 1;
				direction = 2;
				break;
				
			case 2:
				yPos[0] -= 1;
				yPos[1] += 1;
				yPos[2] += 2;
				xPos[2] += 1;
				yPos[3] += 2;
				xPos[3] += 1;
				direction = 3;
				break;
				
			case 3:
				yPos[0] += 1;
				xPos[1] += 1;
				yPos[2] -= 1;
				yPos[3] -= 2;
				xPos[3] -= 1;
				direction = 0;
				break;
				
			default:
				break;
			}
			break;
			
		case 6:
			switch(direction){
			case 0:
				yPos[0] -= 1;
				xPos[0] += 1;
				yPos[2] += 1;
				xPos[2] += 1;
				yPos[3] += 2;
				direction = 1;
				break;
				
			case 1:
				yPos[0] += 1;
				xPos[0] -= 1;
				yPos[2] -= 1;
				xPos[2] -= 1;
				yPos[3] -= 2;
				direction = 2;
				break;
				
			case 2:
				yPos[0] -= 1;
				xPos[0] += 1;
				yPos[2] += 1;
				xPos[2] += 1;
				yPos[3] += 2;
				direction = 3;
				break;
				
			case 3:
				yPos[0] += 1;
				xPos[0] -= 1;
				yPos[2] -= 1;
				xPos[2] -= 1;
				yPos[3] -= 2;
				direction = 0;
				break;
				
			default:
				break;
			}
			break;
			
		case 7:
			switch(direction){
			case 0:
				yPos[0] += 2;
				yPos[1] += 1;
				xPos[1] -= 1;
				yPos[3] -= 1;
				xPos[3] -= 1;
				direction = 1;
				break;
				
			case 1:
				yPos[0] -= 2;
				yPos[1] -= 1;
				xPos[1] += 1;
				yPos[3] += 1;
				xPos[3] += 1;
				direction = 2;
				break;
				
			case 2:
				yPos[0] += 2;
				yPos[1] += 1;
				xPos[1] -= 1;
				yPos[3] -= 1;
				xPos[3] -= 1;
				direction = 3;
				break;
				
			case 3:
				yPos[0] -= 2;
				yPos[1] -= 1;
				xPos[1] += 1;
				yPos[3] += 1;
				xPos[3] += 1;
				direction = 0;
				break;
				
			default:
				break;
			}
			break;
			
		default:
			break;
		}
	}
	
	//rotate right function: based on type t
	public synchronized void rotateRight(int t){
		switch(t){
		case 1:
			switch(direction){
			case 0:
				xPos[0] += 1;
				yPos[1] += 1;
				xPos[2] -= 1;
				yPos[2] += 2;
				xPos[3] -= 2;
				yPos[3] += 3;
				direction = 3;
				break;
				
			case 1:
				xPos[0] -= 1;
				yPos[1] -= 1;
				xPos[2] += 1;
				yPos[2] -= 2;
				xPos[3] += 2;
				yPos[3] -= 3;
				direction = 0;
				break;
				
			case 2:
				xPos[0] += 1;
				yPos[1] += 1;
				xPos[2] -= 1;
				yPos[2] += 2;
				xPos[3] -= 2;
				yPos[3] += 3;
				direction = 1;
				break;
				
			case 3:
				xPos[0] -= 1;
				yPos[1] -= 1;
				xPos[2] += 1;
				yPos[2] -= 2;
				xPos[3] += 2;
				yPos[3] -= 3;
				direction = 2;
				break;
				
			default:
				break;
			}
			break;
			
		case 2:
			switch(direction){
			case 0:
				yPos[0] += 1;
				yPos[3] += 2;
				xPos[3] -= 1;
				direction = 3;
				break;
				
			case 1:
				yPos[0] -= 2;
				xPos[0] -= 1;
				yPos[3] -= 1;
				direction = 0;
				break;
				
			case 2:
				yPos[0] += 1;
				xPos[0] += 1;
				direction = 1;
				break;
				
			case 3:
				yPos[3] -= 1;
				xPos[3] += 1;
				direction = 2;
				break;
				
			default:
				break;
			}
			break;
			
		case 4:
			switch(direction){
			case 0:
				yPos[0] += 1;
				xPos[0] -= 1;
				yPos[1] -= 1;
				xPos[1] -= 1;
				yPos[2] -= 2;
				yPos[3] -= 2;
				direction = 3;
				break;
				
			case 1:
				yPos[0] -= 1;
				xPos[0] += 1;
				yPos[2] += 1;
				xPos[2] -= 1;
				yPos[3] += 2;
				direction = 0;
				break;
				
			case 2:
				yPos[0] += 1;
				xPos[0] -= 1;
				yPos[1] -= 1;
				xPos[1] -= 1;
				direction = 1;
				break;
				
			case 3:
				yPos[0] -= 1;
				xPos[0] += 1;
				yPos[1] += 2;
				xPos[1] += 2;
				yPos[2] += 1;
				xPos[2] += 1;
				direction = 2;
				break;
				
			default:
				break;
			}
			break;
			
		case 5:
			switch(direction){
			case 0:
				yPos[0] -= 1;
				xPos[1] -= 1;
				yPos[2] += 1;
				yPos[3] += 2;
				xPos[3] += 1;
				direction = 3;
				break;
				
			case 1:
				yPos[0] += 1;
				yPos[1] += 1;
				xPos[2] -= 1;
				yPos[3] -= 2;
				xPos[3] -= 1;
				direction = 0;
				break;
				
			case 2:
				yPos[0] -= 1;
				xPos[1] += 1;
				yPos[2] += 1;
				xPos[2] += 2;
				yPos[3] += 2;
				xPos[3] += 1;
				direction = 1;
				break;
				
			case 3:
				yPos[0] += 1;
				yPos[1] -= 1;
				yPos[2] -= 2;
				xPos[2] -= 1;
				yPos[3] -= 2;
				xPos[3] -= 1;
				direction = 2;
				break;
				
			default:
				break;
			}
			break;
			
		case 6:
			switch(direction){
			case 0:
				yPos[0] -= 1;
				xPos[0] += 1;
				yPos[2] += 1;
				xPos[2] += 1;
				yPos[3] += 2;
				direction = 3;
				break;
				
			case 1:
				yPos[0] += 1;
				xPos[0] -= 1;
				yPos[2] -= 1;
				xPos[2] -= 1;
				yPos[3] -= 2;
				direction = 0;
				break;
				
			case 2:
				yPos[0] -= 1;
				xPos[0] += 1;
				yPos[2] += 1;
				xPos[2] += 1;
				yPos[3] += 2;
				direction = 1;
				break;
				
			case 3:
				yPos[0] += 1;
				xPos[0] -= 1;
				yPos[2] -= 1;
				xPos[2] -= 1;
				yPos[3] -= 2;
				direction = 2;
				break;
				
			default:
				break;
			}
			break;
			
		case 7:
			switch(direction){
			case 0:
				yPos[0] += 2;
				yPos[1] += 1;
				xPos[1] -= 1;
				yPos[3] -= 1;
				xPos[3] -= 1;
				direction = 3;
				break;
				
			case 1:
				yPos[0] -= 2;
				yPos[1] -= 1;
				xPos[1] += 1;
				yPos[3] += 1;
				xPos[3] += 1;
				direction = 0;
				break;
				
			case 2:
				yPos[0] += 2;
				yPos[1] += 1;
				xPos[1] -= 1;
				yPos[3] -= 1;
				xPos[3] -= 1;
				direction = 1;
				break;
				
			case 3:
				yPos[0] -= 2;
				yPos[1] -= 1;
				xPos[1] += 1;
				yPos[3] += 1;
				xPos[3] += 1;
				direction = 2;
				break;
				
			default:
				break;
			}
			break;
			
		default:
			break;
		}
	}
	
	public int[] getXPos(){
		return xPos;
	}
	
	public int[] getYPos(){
		return yPos;
	}
	
	public boolean[][] getTiles(){
		return tiles;
	}
	
	public synchronized void setTiles(){
		for(int i = 0; i < 4; i++)
			tiles[yPos[i]][xPos[i]] = true;
	}
}