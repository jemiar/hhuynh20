import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainGUI {
	
	private static JTextField ipInput;
	private static JTextField portInput;
	private static JTextField nameInput;
	private static JButton send = new JButton("Send");
	private static JTextArea sendMsg = new JTextArea();
	private static boolean connected = false;
	private static Socket client;
	private static ObjectOutputStream outgoing;
	private static ObjectInputStream incoming;

	public static void main(String[] args) {

//Setup GUI
		//Setup frame
		JFrame frame = new JFrame("Messenger Client 1.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int respond = JOptionPane.showConfirmDialog(null, "Are you sure to quit the program?", "", JOptionPane.YES_NO_OPTION);
				if(respond == JOptionPane.YES_OPTION){
					disconnect();
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
		JButton connect = new JButton("Connect");
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!connected){
					Thread connectThread = new Thread(){
						public void run(){
							tryConnect();
						}
					};
					connectThread.start();
				}else
					disconnect();
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
		JTextPane msgPane = new JTextPane();
		msgPane.setEditable(false);
		JScrollPane msgScroll = new JScrollPane(msgPane);
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
		DefaultListModel<User> model = new DefaultListModel<User>();
		JList onlineList = new JList(model);
		onlineList.setLayoutOrientation(JList.VERTICAL);
		onlineList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
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
		
		model.addElement(new User("Minh"));
		model.addElement(new User("Tran"));
		model.addElement(new User("Hoai Mi"));
		model.addElement(new User("Khanh Mi"));
		model.addElement(new User("Jemiar"));
		
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
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(sendMsg.getText().length() > 0){
					int[] selected = onlineList.getSelectedIndices();
					for(int i = 0; i < selected.length; i++)
						System.out.println(((User)onlineList.getModel().getElementAt(selected[i])).getName());
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
	
	private static void tryConnect(){
		String ipAdd = ipInput.getText();
		if(ipAdd.length() > 0){
			String portNo = portInput.getText();
			if(portNo.length() > 0){
				try{
					int portInt = Integer.parseInt(portNo);
					String name = nameInput.getText();
					if(name.length() > 0 && !nameExisted(name)){
						System.out.println("Connecting");
						client = new Socket(ipAdd, portInt);
						System.out.println("Connected");
						outgoing = new ObjectOutputStream(client.getOutputStream());
						incoming = new ObjectInputStream(client.getInputStream());
						User newUser = new User(name);
						ArrayList<User> emptyUserList = new ArrayList<User>();
						Message connectRequest = new Message(1, newUser, emptyUserList, "");
						try{
							outgoing.writeObject(connectRequest);
							outgoing.flush();
						}catch(IOException ioe){
							JOptionPane.showMessageDialog(null, "Request connect error.");
						}
					}else{
						JOptionPane.showMessageDialog(null, "Blank username field or username has already been used. Please input a new username.");
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
	
//	private static void connect(String ip, int p, String n){
//		try{
//			System.out.println("Connecting");
//			client = new Socket(ip, p);
//			System.out.println("Connected");
//			System.out.println(client.isConnected());
//			outgoing = new ObjectOutputStream(client.getOutputStream());
//			incoming = new ObjectInputStream(client.getInputStream());
//		}catch(UnknownHostException uhe){
//			JOptionPane.showMessageDialog(null, "Server unknown error.");
//		}catch(IOException ioe){
//			JOptionPane.showMessageDialog(null, "Cannot get I/O stream.");
//		}
//		User newUser = new User(n);
//		ArrayList<User> emptyUserList = new ArrayList<User>();
//		Message connectRequest = new Message(1, newUser, emptyUserList, "");
//		try{
//			outgoing.writeObject(connectRequest);
//			outgoing.flush();
//		}catch(IOException ioe){
//			JOptionPane.showMessageDialog(null, "Request connect error.");
//		}
//	}
	
	private static void disconnect(){
		
	}
	
	private static boolean nameExisted(String s){
		return false;
	}

}
