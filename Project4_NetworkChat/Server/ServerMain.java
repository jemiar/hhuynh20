//CS 342 - SPRING 2016
//Project 4: Network chat application
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) and Nikolay Zakharov (nzakha2)

//Class: ServerMain.java
//Responsibility: run the server

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ServerMain extends Thread{
	
	private Socket clientSocket;

	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket = null;
		int p = 8778;
		//Create new server socket, and listening for connection request.
		//Run each connection on their own threads.
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
	
	//Constructor, create new socket and run the thread
	private ServerMain(Socket s){
		clientSocket = s;
		start();
	}
	
	//Override run() function of Thread
	public void run(){
		try{
			//Create output and input streams
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			Message input;
			//Listen for message
			while((input = (Message)in.readObject()) != null){
				//Get type of message
				int t = input.getType();
				//Get current online list, OnlineUsers class(Singleton pattern) store the list of online users
				OnlineUsers online = OnlineUsers.getInstance();
				//Get the sender from input
				User u = input.getUser();
				ArrayList<User> uList = online.getUserList();
				//Type 1: User request to connect
				//If username has already been used, send fail response and close streams, socket
				//If username is new, send successful response to requester, request other users to add this user to online list
				//Add this user to server's online list
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
				//Type 2: User request to disconnect
				//If for some reasons, this user doesn't exist in online list, send fail response
				//If this user exists, remove from current online list
				//Send successful response
				//Ask other users to remove this user
				//Close streams and socket
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
				//Type 3: Text message
				//Go through the online list and the destination list, forward the message accordingly
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
				//Type 10: User request to disconnect without the need of response
				//Remove the user
				//Ask other users to remove this user
				if(t == 10){
					online.removeUser(u);
					requestRemoveUser(online.getUserList(), online.getSocketList(), online.getOutStreamList(), u);
					break;
				}
			}
			in.close();
			out.close();
			clientSocket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//Method to check if user exists in online list
	public boolean userExisted(User us, ArrayList<User> uL){
		for(int i = 0; i < uL.size(); i++)
			if(us.getName().equals(uL.get(i).getName()))
				return true;
		return false;
	}
	
	//Method to send request to all users to add new online user
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
	
	//Method to send request to all users to remove a disconnected user
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
