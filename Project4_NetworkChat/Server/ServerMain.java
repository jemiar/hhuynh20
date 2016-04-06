import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ServerMain extends Thread{
	
	private Socket clientSocket;

	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = null;
		int p = 8746;
		try{
			serverSocket = new ServerSocket(p);
			System.out.println("Server run at IP Address: " + InetAddress
					.getLocalHost().getHostAddress() + ",Port: " + serverSocket.getLocalPort());
			try{
				while(true){
					new ServerMain(serverSocket.accept());
				}
			}catch(IOException e){
				System.err.println("Accept failed");
			}
		}catch(IOException e){
			System.err.println("Could not listen on port: " + p);
		}finally{
			try{
				serverSocket.close();
			}catch(IOException e){
				System.err.println("Could not close port: " + p);
			}
		}
	}
	
	private ServerMain(Socket s){
		clientSocket = s;
		start();
	}
	
	public void run(){
		try{
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			Message input;
			while((input = (Message)in.readObject()) != null){
				int t = input.getType();
				switch(t){
				case 1:
					OnlineUsers online = OnlineUsers.getInstance();
					User u = input.getUser();
					ArrayList<User> uList = online.getList();
					if(!userExisted(u, uList)){
						Message successConnectRespond = new Message(5, u, uList, "Server: Connect success");
						out.writeObject(successConnectRespond);
						out.flush();
						online.addUser(u);
					}else{
						Message failConnectRespond = new Message(4, u, uList, "Server: Connect fail");
						out.writeObject(failConnectRespond);
						out.flush();
					}
//					online.addUser(input.getUser());
//					ArrayList<User> uList = online.getList();
//					for(int i = 0; i < uList.size(); i++)
//						System.out.println(uList.get(i).toString());
					break;
				default:
					System.out.println("Unknown message type");
					break;
				}
//				System.out.println( input.getUser().toString() + input.getType());
//				OnlineUsers online = OnlineUsers.getInstance();
//				online.addUser(input.getUser());
//				ArrayList<User> uList = online.getList();
//				for(int i = 0; i < uList.size(); i++)
//					System.out.println(uList.get(i).toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public boolean userExisted(User us, ArrayList<User> uL){
		for(int i = 0; i < uL.size(); i++)
			if(us.getName().equals(uL.get(i).getName()))
				return true;
		return false;
	}

}
