//CS 342 - SPRING 2016
//Project 4: Network chat application
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) Nikolay Zakharov (nzakha2)

//Class: Message.java
//Responsibility: represent message object

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{
	private int type;					//type of message:1.Request to connect; 2.Request to disconnect; 3. Text message;
										//4. Server response connect fail; 5. Server response connect success
										//6. Server response disconnect fail; 7. Server response disconnect success
										//8. Server request to add user; 9. Server request to remove user
										//10. Request to disconnect without the need of server responding
	private User sender;				//Sender
	private ArrayList<User> receivers;	//List of receiver
	private String msg;					//text message
	
	//Constructor
	public Message(int t, User s, ArrayList<User> rs, String m){
		type = t;
		sender = new User(s);
		receivers = new ArrayList<User>();
		for(int i = 0; i < rs.size(); i++)
			receivers.add(rs.get(i));
		msg = m;
	}
	
	//Method to get the type of message
	public int getType(){
		return type;
	}
	
	//Method to get the sender of message
	public User getUser(){
		return sender;
	}
	
	//Method to get the text message
	public String getMessage(){
		return msg;
	}
	
	//Method to get the receivers
	public ArrayList<User> getUserList(){
		return receivers;
	}
}
