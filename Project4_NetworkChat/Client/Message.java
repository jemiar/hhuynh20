import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{
	private int type;
	private User sender;
	private ArrayList<User> receivers;
	private String msg;
	
	public Message(int t, User s, ArrayList<User> rs, String m){
		type = t;
		sender = new User(s);
		receivers = new ArrayList<User>();
		for(int i = 0; i < rs.size(); i++)
			receivers.add(rs.get(i));
		msg = m;
	}
	
	public int getType(){
		return type;
	}
	
	public User getUser(){
		return sender;
	}
	
	public String getMessage(){
		return msg;
	}
	
	public ArrayList<User> getUserList(){
		return receivers;
	}
}
