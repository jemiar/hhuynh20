//CS 342 - SPRING 2016
//Project 5: Tetris
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) Nikolay Zakharov (nzakha2)

//Class: Tetromino.java
//Responsibility: An abstract class, that is used as a super class for all types of Tetromino

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public abstract class Tetromino extends JPanel{
	public abstract Timer getTimer();
}
