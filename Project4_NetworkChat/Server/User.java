//CS 342 - SPRING 2016
//Project 4: Network chat application
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) Nikolay Zakharov (nzakha2)

//Class: User.java
//Responsibility: represent user on client machine

import java.io.Serializable;

public class User implements Serializable{
	
	private String name;
	
	//Constructor, parameter is a string
	public User(String s){
		name = s;
	}
	
	//Constructor, parameter is a User object
	public User(User u){
		name = u.getName();
	}
	
	//override toString method, used in list element display
	public String toString(){
		return name;
	}
	
	//Method to get name instance variable of user
	public String getName(){
		return name;
	}
	
}
