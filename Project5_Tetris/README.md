# Project 5 : Tetris  
Developed by: Hoang Minh Huynh Nguyen(hhuynh20) and Nikolay Zakharov(nzakha2)  
## How to play  
1. Arrow left/right to move the tetromino left/right (if possible)
2. Arrow down to move the tetromino down (soft drop) (if possible)  
3. Press A to rotate left (if possible)
4. Press D to rotate right (if possible)
5. Press SPACE for a hard drop
6. Press T to pause the falling down of the tetromino. Press T again to resume  
  
## Use of Design patterns  
1. Singleton pattern: Use of a single grid object to manage the board game. Refer to file Grid.java
2. Factory pattern: Used to produce tetrominoes. Refer to files TetrominoFactory.java and Tetromino.java  
  
## Issues with the current codes
1. Restart only when in play. If the game is over, you have to start the program
2. Due to the write up requirement: move left/right, rotate left/right can pause the falling down -> Hard to determine when user stops moving the tetromino. Therefore, we limit this by when the tetromino in the lowest row, user cannot move or rotate
3. The final piece drops down before game over cannot be painted
