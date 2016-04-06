import java.io.Serializable;

public class User implements Serializable{
	private String name;
	
	public User(String s){
		name = s;
	}
	
	public User(User u){
		name = u.getName();
	}
	
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}

}
