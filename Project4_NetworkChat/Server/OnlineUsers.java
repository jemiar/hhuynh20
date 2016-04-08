//CS 342 - SPRING 2016
//Project 4: Network chat application
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) and Nikolay Zakharov (nzakha2)

//Class: OnlineUsers.java
//Responsibility: manage to online users list with their corresponding socket and streams
//Use singleton pattern to make sure no data collision

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class OnlineUsers {
	private static OnlineUsers onlineUsers = null;
	private ArrayList<User> userList;
	private ArrayList<Socket> socketList;
	private ArrayList<ObjectOutputStream> outputList;
	private ArrayList<ObjectInputStream> inputList;
	
	//Constructor
	private OnlineUsers(){
		userList = new ArrayList<User>();
		socketList = new ArrayList<Socket>();
		outputList = new ArrayList<ObjectOutputStream>();
		inputList = new ArrayList<ObjectInputStream>();
	}
	
	//Get an instance of this class
	public static synchronized OnlineUsers getInstance(){
		if(onlineUsers == null)
			onlineUsers = new OnlineUsers();
		return onlineUsers;
	}
	
	//Method to add new user to the list
	public synchronized void addUser(User u, Socket s, ObjectOutputStream oos, ObjectInputStream ois){
		userList.add(u);
		socketList.add(s);
		outputList.add(oos);
		inputList.add(ois);
	}
	
	//Method to remove user from the list
	public synchronized void removeUser(User u){
		int index = -1;
		for(int i = 0; i < userList.size(); i++)
			if(u.getName().equals(userList.get(i).getName())){
				index = i;
				break;
			}
		userList.remove(index);
		socketList.remove(index);
		outputList.remove(index);
		inputList.remove(index);
	}
	
	//Method to get the list of users
	public ArrayList<User> getUserList(){
		return userList;
	}
	
	//Method to get the list of sockets
	public ArrayList<Socket> getSocketList(){
		return socketList;
	}
	
	//Method to get the list of output streams
	public ArrayList<ObjectOutputStream> getOutStreamList(){
		return outputList;
	}
	
	//Method to get the list of input streams
	public ArrayList<ObjectInputStream> getInStreamList(){
		return inputList;
	}
}
