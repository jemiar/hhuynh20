import java.util.ArrayList;

public class OnlineUsers {
	private static OnlineUsers onlineUsers = null;
	private ArrayList<User> userList;
	
	private OnlineUsers(){
		userList = new ArrayList<User>();
	}
	
	public static synchronized OnlineUsers getInstance(){
		if(onlineUsers == null)
			onlineUsers = new OnlineUsers();
		return onlineUsers;
	}
	
	public synchronized void addUser(User u){
		userList.add(u);
	}
	
	public ArrayList<User> getList(){
		return userList;
	}
}
