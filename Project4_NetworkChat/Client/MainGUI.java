//CS 342 - SPRING 2016
//Project 4: Network chat application
//Developed by: Hoang Minh Huynh Nguyen (hhuynh20) Nikolay Zakharov (nzakha2)

//Class: MainGUI.java
//Responsibility: build GUI, handle network and interaction

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.StyledDocument;


public class MainGUI {
	
	private static JTextField ipInput;				//text field for input server's IP Address
	private static JTextField portInput;				//text field for input server's port number
	private static JTextField nameInput;				//text field for input username
	private static JButton connect;					//button: click to connect to or disconnect from server
	private static JButton send = new JButton("Send");		//button: click to send message
	private static JTextArea sendMsg = new JTextArea();		//text area: for composing message
	private static boolean connected = false;			//used for manage state of connection
	private static Socket client;					//store socket of client side
	private static ObjectOutputStream outgoing;			//store outgoing stream
	private static ObjectInputStream incoming;			//store incoming stream
	private static DefaultListModel<User> model;			//model used in list to display list of online users
	private static JTextPane msgPane;				//text pane for displaying incoming message
	private static StyledDocument doc;				//document in text pane
	private static User newUser;					//user of this client
	private static ArrayList<User> selectedUser;			//store list of users this client selects to send message
	private static JList onlineList;				//JList for list of online users

	public static void main(String[] args) {

//Setup GUI
		//Setup frame
		JFrame frame = new JFrame("Messenger Client 1.0");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Add window close action listener: Make sure if user is currently connected, send a request to server to close streams, socket.
		//For this type of request, no need for server's response
		//Then close streams, socket on client side. Finally, exit program
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				if(connected){
					Message closeRequest = new Message(10, newUser, new ArrayList<User>(), "");
					try {
						outgoing.writeObject(closeRequest);
						outgoing.flush();
						outgoing.close();
						incoming.close();
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(0);
				}else{
					System.exit(0);
				}
			}
		});
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		
		//Setup menu
		JMenu menu = new JMenu("File");
		menu.setMnemonic('F');
		
		//Setup menu items
		//About
		JMenuItem about = new JMenuItem("About");
		about.setMnemonic('A');
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = "Messenger Client 1.0\n"
						+ "Developed by Hoang Minh Huynh Nguyen and Nikolay Zakharov.\n"
						+ "GNU License. 2016";
				JOptionPane.showMessageDialog(null, s);
			}
		});
		
		//Quit
		JMenuItem quit = new JMenuItem("Quit");
		quit.setMnemonic('Q');
		//Same as when user closes the frame, make sure all streams, socket are closed before user exits.
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(connected){
					Message closeRequest = new Message(10, newUser, new ArrayList<User>(), "");
					try {
						outgoing.writeObject(closeRequest);
						outgoing.flush();
						outgoing.close();
						incoming.close();
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(0);
				}else{
					System.exit(0);
				}
			}
		});
		
		//Add menu items to menu
		menu.add(about);
		menu.add(quit);
		
		//Setup menu bar, add menu to menu bar, and then add menu bar to frame
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		//IP Address Label
		JLabel ipAddress = new JLabel(" Server IP Address:");
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 0;
		c1.gridy = 0;
		frame.add(ipAddress, c1);
		
		//IP Address input textfield
		ipInput = new JTextField();
		ipInput.setColumns(15);
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 1;
		c2.gridy = 0;
		frame.add(ipInput, c2);
		
		//Port # input panel
		JPanel panel1 = new JPanel();
		JLabel port = new JLabel("Port: ");
		panel1.add(port);
		portInput = new JTextField();
		portInput.setColumns(10);
		panel1.add(portInput);
		GridBagConstraints c3 = new GridBagConstraints();
		c3.fill = GridBagConstraints.HORIZONTAL;
		c3.gridx = 3;
		c3.gridy = 0;
		frame.add(panel1, c3);
		
		//Username label
		JLabel userLabel = new JLabel(" Username:");
		GridBagConstraints c4 = new GridBagConstraints();
		c4.fill = GridBagConstraints.HORIZONTAL;
		c4.gridx = 0;
		c4.gridy = 1;
		frame.add(userLabel, c4);
		
		//Username input text field
		nameInput = new JTextField();
		nameInput.setColumns(15);
		GridBagConstraints c5 = new GridBagConstraints();
		c5.fill = GridBagConstraints.HORIZONTAL;
		c5.gridx = 1;
		c5.gridy = 1;
		frame.add(nameInput, c5);
		
		//Connect/Disconnect button
		connect = new JButton("Connect");
		//Connect/Disconnect button. When click, start new thread to handle the connection
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!connected){
					Thread connectThread = new Thread(){
						public void run(){
							tryConnect();
						}
					};
					connectThread.start();
				}else{
					Thread disconnectThread = new Thread(){
						public void run(){
							disconnect();
						}
					};
					disconnectThread.start();
				}
			}
		});
		GridBagConstraints c6 = new GridBagConstraints();
		c6.fill = GridBagConstraints.HORIZONTAL;
		c6.gridx = 3;
		c6.gridy = 1;
		frame.add(connect, c6);
		
		//Message area label
		JLabel msgAreaLabel = new JLabel(" Incoming messages:");
		c6.gridx = 0;
		c6.gridy = 2;
		frame.add(msgAreaLabel, c6);
		
		//Online users label
		JLabel onlineUser = new JLabel("    Online:");
		c6.gridx = 3;
		c6.gridy = 2;
		frame.add(onlineUser, c6);
		
		//Message area
		msgPane = new JTextPane();
		msgPane.setEditable(false);
		JScrollPane msgScroll = new JScrollPane(msgPane);
		msgScroll.setPreferredSize(new Dimension(250, 45));
		msgScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints c7 = new GridBagConstraints();
		c7.fill = GridBagConstraints.HORIZONTAL;
		c7.ipady = 250;
		c7.gridwidth = 2;
		c7.gridx = 0;
		c7.gridy = 3;
		c7.insets = new Insets(0, 5, 0, 0);
		frame.add(msgScroll, c7);
		
		//Online users list area
		model = new DefaultListModel<User>();
		onlineList = new JList(model);
		onlineList.setLayoutOrientation(JList.VERTICAL);
		onlineList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//add listener to the list: make sure the send message is only available when user selects one or more elements in the list
		onlineList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					if (onlineList.getSelectedIndex() == -1) {
						send.setEnabled(false);
		            } else {
		            	send.setEnabled(true);
		            }
		        }
			}
		});
		
		JScrollPane onlineScroll = new JScrollPane(onlineList);
		onlineScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		onlineScroll.setPreferredSize(new Dimension(150, 270));
		GridBagConstraints c8 = new GridBagConstraints();
		c8.gridx = 3;
		c8.gridy = 3;
		frame.add(onlineScroll, c8);
		
		//Composing message label
		JLabel composLabel = new JLabel(" Compose message:");
		GridBagConstraints c9 = new GridBagConstraints();
		c9.gridx = 0;
		c9.gridy = 4;
		frame.add(composLabel, c9);
		
		//Message input field
		sendMsg.setLineWrap(true);
		sendMsg.setWrapStyleWord(true);
		JScrollPane sendScroll = new JScrollPane(sendMsg);
		sendScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sendScroll.setPreferredSize(new Dimension(350, 50));
		GridBagConstraints ca = new GridBagConstraints();
		ca.insets = new Insets(0, 5, 5, 0);
		ca.gridx = 0;
		ca.gridy = 5;
		ca.gridwidth = 3;
		ca.ipady = 50;
		frame.add(sendScroll, ca);
		
		//Send button
		//When button is clicked, get the list of selected receivers in the online list
		//start new thread to send. Also make sure user doesn't send blank message.
		//after sending the message, change the online list to original state(no selected),
		//clear the composing area, and put focus to this field
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(sendMsg.getText().length() > 0){
					selectedUser = new ArrayList<User>();
					int[] selected = onlineList.getSelectedIndices();
					for(int i = 0; i < selected.length; i++)
						selectedUser.add((User)onlineList.getModel().getElementAt(selected[i]));
					Thread sendMsgThread = new Thread(){
						public void run(){
							sendMessage();
						}
					};
					sendMsgThread.start();
				}else{
					JOptionPane.showMessageDialog(null, "Please input message for sending.");
				}
				onlineList.clearSelection();
				sendMsg.setText("");
				sendMsg.requestFocusInWindow();
			}
		});
		send.setPreferredSize(new Dimension(15, 35));
		send.setEnabled(false);
		GridBagConstraints cb = new GridBagConstraints();
		cb.fill = GridBagConstraints.HORIZONTAL;
		cb.gridx = 3;
		cb.gridy = 5;
		cb.insets = new Insets(0, 10, 0, 10);
		frame.add(send, cb);

		frame.pack();
		frame.setVisible(true);
		
//End of GUI setup
	}
	//End of main method
	
	//Method: try to connect to server
	private static void tryConnect(){
		//Check server's IP address, port number and username are input correctly
		String ipAdd = ipInput.getText();
		if(ipAdd.length() > 0){
			String portNo = portInput.getText();
			if(portNo.length() > 0){
				try{
					int portInt = Integer.parseInt(portNo);
					String name = nameInput.getText();
					if(name.length() > 0){
						//Try to connect to server
						client = new Socket(ipAdd, portInt);
						outgoing = new ObjectOutputStream(client.getOutputStream());
						newUser = new User(name);
						//Send request to connect to server. Have to do this step, in order to prevent from 2 users have the same username
						ArrayList<User> emptyUserList = new ArrayList<User>();
						Message connectRequest = new Message(1, newUser, emptyUserList, "");
						try{
							outgoing.writeObject(connectRequest);
							outgoing.flush();
						}catch(IOException ioe){
							JOptionPane.showMessageDialog(null, "Request connect error.");
						}
						//Run the listening thread, this thread waits for incoming message
						Thread listenThread = new Thread(){
							public void run(){
								try {
									incoming = new ObjectInputStream(client.getInputStream());
									Message input;
									//Read the message and check the type of the message. Respond based on type of message.
									while((input = (Message)incoming.readObject()) != null){
										int t = input.getType();
										//Type 3: regular message sent from other users
										if(t == 3){
											doc.insertString(doc.getLength(), input.getMessage(), doc.getStyle("regular"));
										}
										//Type 4: response from server for connection request. Fail connection due to username has already been used
										if(t == 4){
											JOptionPane.showMessageDialog(null, "This username has already been used. Please choose another one.");
											break;
										}
										//Type 5: response from server for connection request. Successful connection
										//Add server response to message area.
										//Add online users to list
										//Set IP address, port # and username fields to uneditable
										if(t == 5){
											connected = true;
											doc = msgPane.getStyledDocument();
											doc.insertString(doc.getLength(), input.getMessage(), doc.getStyle("regular"));
											ArrayList<User> usList = input.getUserList();
											for(int i = 0; i < usList.size(); i++)
												model.addElement(usList.get(i));
											connect.setText("Disconnect");
											ipInput.setEditable(false);
											portInput.setEditable(false);
											nameInput.setEditable(false);
										}
										//Type 6: response from server for disconnect request. Fail to disconnect
										if(t == 6){
											doc.insertString(doc.getLength(), input.getMessage(), doc.getStyle("regular"));
										}
										//Type 7: Response from server for disconnect request. Successful disconnect.
										//Add server response to message area.
										//Clear online users list
										//Change IP address, port #, and username field to editable
										if(t == 7){
											connected = false;
											doc.insertString(doc.getLength(), input.getMessage(), doc.getStyle("regular"));
											model.clear();
											connect.setText("Connect");
											ipInput.setEditable(true);
											portInput.setEditable(true);
											nameInput.setEditable(true);
											break;
										}
										//Type 8: Server request to add new user to online list as a new user has just connected to the server
										if(t == 8){
											User newUS = input.getUser();
											model.addElement(newUS);
										}
										//Type 9: Server request to remove a user from online list as this user has just disconnected from the server
										//Clear the online list, then adding the new list(currently online users)
										if(t == 9){
											model.clear();
											for(int i = 0; i < input.getUserList().size(); i++)
												if(!newUser.getName().equals(input.getUserList().get(i).getName()))
													model.addElement(input.getUserList().get(i));
										}
									}
									//Close streams, socket properly if user disconnect or not be able to connect
									outgoing.close();
									incoming.close();
									client.close();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};
						listenThread.start();
					}else{
						JOptionPane.showMessageDialog(null, "Please input your username.");
					}
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Please input a numeric value for server's port number.");
				}
			}else{
				JOptionPane.showMessageDialog(null, "Please input server's port number.");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Please input server's IP address.");
		}
	}
	//End of tryConnect() method
	
	//Method to send request to disconnect
	//Send a request to disconnect, then the listening thread will listen to server's response and disconnect properly
	private static void disconnect(){
		Message disconnectRequest = new Message(2, newUser, new ArrayList<User>(), "");
		try {
			outgoing.writeObject(disconnectRequest);
			outgoing.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//End of disconnect() method
	
	//Method to send a message to other users.
	//Send a request to server. Server will then process the message and forward it to the receivers.
	private static void sendMessage(){
		Message sendMsgRequest = new Message(3, newUser, selectedUser, newUser.getName() + ": " + sendMsg.getText() + "\n");
		try {
			outgoing.writeObject(sendMsgRequest);
			outgoing.flush();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	//End of sendMessage()

}
//End of MainGUI class
