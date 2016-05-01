//CS 342 - SPRING 2016
//Project 5: Tetris
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) Nikolay Zakharov (nzakha2)

//Class: Grid.java
//Responsibility: manage the grid of the play board, position of the active tetromino and its direction
//Implemented with Singleton design pattern.

import java.util.Arrays;

public class Grid {
	private static Grid board = null;
	//Matrix of the grid, add 3 more rows at the bottom, and 2 columns on the side to make
	//checking tetromino getting outside of the board more easy
	private boolean[][] tiles = new boolean[Main.ROWS + 3][Main.COLS + 2];
	//Array: x coordinates of the tetromino
	private int[] xPos = new int[4];
	//Array: y coordinates of the tetromino
	private int[] yPos = new int[4];
	//Direction of the tetromino: 0.N, 1.W, 2.S, 3.E
	private int direction;
	//Type of tetromino: 1.I, 2.T, 3.O, 4.L, 5.J, 6.S, 7.Z
	private int type;
	//Variable to manage the height of tetromino pile
	private int minY;
	
	//Private construction for the singleton pattern
	private Grid(){
		//Setup the grid
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
		
		//Setup the height
		minY = Main.ROWS;
	}
	
	//Reset grid when user click restart button
	public synchronized void resetGrid(){
		for(int i = 0; i < Main.ROWS; i++)
			for(int j = 1; j < Main.COLS + 1; j++)
				tiles[i][j] = false;
		minY = Main.ROWS;
	}
	
	//Function to clear line when it is full
	public void drop(int a){
		if(a == minY){
			tiles[a] = Arrays.copyOf(Main.emptyLine, Main.emptyLine.length);
			minY += 1;
		}else{
			for(int i = a; i > minY; i--){
				tiles[i] = Arrays.copyOf(tiles[i - 1], tiles[i - 1].length);
			}
			tiles[minY] = Arrays.copyOf(Main.emptyLine, Main.emptyLine.length);
			minY += 1;
		}
	}
	
	//Function to get the height of the pile
	public int getMinY(){
		return minY;
	}
	
	//Function to set the height of the pile
	public void setMinY(int m){
		minY = m;
	}
	
	//Function to check if the tetromino can move down
	public boolean canMoveDown(){
		for(int i = 0; i < 4; i++){
			if(tiles[yPos[i] + 1][xPos[i]])
				return false;
		}
		return true;
	}
	
	//Function to check if the tetromino can move left
	public boolean canMoveLeft(){
		if(tiles[yPos[0]][xPos[0] - 1])
			return false;
		return true;
	}
	
	//Function to check if the tetromino can move right
	public boolean canMoveRight(){
		if(tiles[yPos[3]][xPos[3] + 1])
			return false;
		return true;
	}
	
	//Function: get an instance of the Grid class. Singleton pattern
	public static synchronized Grid getInstance(){
		if(board == null)
			board = new Grid();
		return board;
	}
	
	//Function to set the type of the tetromino
	public synchronized void setType(int t){
		type = t;
	}
	
	//Function to get the type of the tetromino
	public int getType(){
		return type;
	}
	
	//Function to check if a tetromino can rotate left base on its position, its type and its direction
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
			
		case 3:
			b = true;
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
	
	//Function to check if a tetromino can rotate right based on its position, its type and its direction
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
			
		case 3:
			b = true;
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
	
	//Function to setup the initial position of the tetromino based on its type
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
	
	//Function to set the direction of the tetromino
	public synchronized void setDirection(int d){
		direction = d;
	}
	
	//Function to reset the position of the tetromino
	public synchronized void resetPiece(){
		for(int i = 0; i < 4; i++){
			yPos[i] = 0;
			xPos[i] = 0;
		}
	}
	
	//Function to move the tetromino down 1 step
	public synchronized void pieceDown(){
		for(int i = 0; i < 4; i++){
			yPos[i] += 1;
		}
	}
	
	//Function to move the tetromino left 1 step
	public synchronized void pieceLeft(){
		for(int i = 0; i < 4; i++){
			xPos[i] -= 1;
		}
	}
	
	//Function to move the tetromino right 1 step
	public synchronized void pieceRight(){
		for(int i = 0; i < 4; i++){
			xPos[i] += 1;
		}
	}
	
	//Function to rotate the tetromino left based on its type and its position, and also its direction
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
			
		case 3:
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
	
	//Function to rotate the tetromino right based on its type and its position, and also its direction
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
			
		case 3:
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
	
	//Function to get the array of x coordinates of the tetromino
	public int[] getXPos(){
		return xPos;
	}
	
	//Function to get the array of y coordinates of the tetromino
	public int[] getYPos(){
		return yPos;
	}
	
	//Function to get the grid matrix of the Grid object
	public boolean[][] getTiles(){
		return tiles;
	}
	
	//Function to set the grid when the the tetromino is locked into place
	public synchronized void setTiles(){
		for(int i = 0; i < 4; i++)
			tiles[yPos[i]][xPos[i]] = true;
	}
}
