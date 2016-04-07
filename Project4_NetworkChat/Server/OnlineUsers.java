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
	
	private OnlineUsers(){
		userList = new ArrayList<User>();
		socketList = new ArrayList<Socket>();
		outputList = new ArrayList<ObjectOutputStream>();
		inputList = new ArrayList<ObjectInputStream>();
	}
	
	public static synchronized OnlineUsers getInstance(){
		if(onlineUsers == null)
			onlineUsers = new OnlineUsers();
		return onlineUsers;
	}
	
	public synchronized void addUser(User u, Socket s, ObjectOutputStream oos, ObjectInputStream ois){
		userList.add(u);
		socketList.add(s);
		outputList.add(oos);
		inputList.add(ois);
	}
	
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
	
	public ArrayList<User> getUserList(){
		return userList;
	}
	
	public ArrayList<Socket> getSocketList(){
		return socketList;
	}
	
	public ArrayList<ObjectOutputStream> getOutStreamList(){
		return outputList;
	}
	
	public ArrayList<ObjectInputStream> getInStreamList(){
		return inputList;
	}
}
