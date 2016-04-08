import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ServerMain extends Thread{
	
	private Socket clientSocket;

	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = null;
		int p = 8778;
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
				OnlineUsers online = OnlineUsers.getInstance();
				User u = input.getUser();
				ArrayList<User> uList = online.getUserList();
				if(t == 1){
					if(!userExisted(u, uList)){
						Message successConnectRespond = new Message(5, u, uList, "Server: Connect success\n");
						out.writeObject(successConnectRespond);
						out.flush();
						requestAddUser(uList, online.getSocketList(), online.getOutStreamList(), u);
						online.addUser(u, clientSocket, out, in);
					}else{
						Message failConnectRespond = new Message(4, u, uList, "Server: Connect fail\n");
						out.writeObject(failConnectRespond);
						out.flush();
						break;
					}
				}
				if(t == 2){
					if(userExisted(u, uList)){
						online.removeUser(u);
						Message successDisconnectResp = new Message(7, u, uList, "Server: Disconnect success\n");
						out.writeObject(successDisconnectResp);
						out.flush();
						requestRemoveUser(online.getUserList(), online.getSocketList(), online.getOutStreamList(), u);
						break;
					}else{
						Message failDisconnectResp = new Message(6, u, uList, "Server: Disconnect fail\n");
						out.writeObject(failDisconnectResp);
						out.flush();
					}
				}
				if(t == 3){
					ArrayList<User> recs = input.getUserList();
					for(int i = 0; i < recs.size(); i++){
						for(int j = 0; j < online.getUserList().size(); j++){
							if(recs.get(i).getName().equals(online.getUserList().get(j).getName())){
								try {
									online.getOutStreamList().get(j).writeObject(input);
									online.getOutStreamList().get(j).flush();
								} catch (IOException e) {
									e.printStackTrace();
								}
								break;
							}
						}
					}
				}
			}
			in.close();
			out.close();
			clientSocket.close();
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
	
	public void requestAddUser(ArrayList<User> uL, ArrayList<Socket> sL, ArrayList<ObjectOutputStream> opList, User uS){
		for(int i = 0; i < uL.size(); i++){
			try {
				Message m = new Message(8, uS, uL, "");
				opList.get(i).writeObject(m);
				opList.get(i).flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void requestRemoveUser(ArrayList<User> uL, ArrayList<Socket> sL, ArrayList<ObjectOutputStream> opList, User uS){
		for(int i = 0; i < uL.size(); i++){
			try{
				Message m = new Message(9, uS, uL, "");
				opList.get(i).writeObject(m);
				opList.get(i).flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
