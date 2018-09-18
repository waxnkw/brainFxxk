package ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import rmi.RemoteHelper;
public class Register extends JFrame{
	private JFrame registerFrame;
	private JPanel pl1;
	private JPanel pl2;
	private JTextField username;
	private JTextField password;
	private JLabel username_l;
	private JLabel password_l;
	private JLabel regis;
	private JLabel wrong;
	private JButton ensure;
	
	public boolean changeToLogin=false;
	
	//constructor
	public Register(){
		//linkToServer();
		registerFrame=new JFrame("register");
		GridLayout gl=new GridLayout(5,1,10,40);
		registerFrame.setLayout(gl);
		
		//register headlabel
		regis=new JLabel("",JLabel.CENTER);
		//regis.setSize(1000,1000);
		final Font font=new Font(Font.DIALOG,Font.PLAIN,40);
		regis.setFont(font);
		regis.setText("×¢²á");
		regis.setOpaque(true);
		regis.setForeground(Color.red);
		regis.setBackground(Color.gray);
		registerFrame.add(regis);
		
		//username and password
		pl1=new JPanel();
		pl1.setLayout(new FlowLayout());
		pl2=new JPanel();
		pl2.setLayout(new FlowLayout());
		
		username_l=new JLabel("username",JLabel.LEFT);
		final Font font1=new Font(Font.DIALOG,Font.PLAIN,20);
		username_l.setFont(font1);
		password_l=new JLabel("password",JLabel.CENTER);
		password_l.setFont(font1);
		
		username=new JTextField(6);
		username.setSize(100, 100);
		password=new JTextField(6);
		password.setSize(100, 100);
		
		username.addActionListener(new InputActionListener());
		password.addActionListener(new InputActionListener());
		
		pl1.add(username_l);
		pl1.add(username);
		pl2.add(password_l);
		pl2.add(password);
		
		registerFrame.add(pl1);
		registerFrame.add(pl2);
		
		//register failed label
		wrong=new JLabel("×¢²áÊ§°Ü",JLabel.CENTER);
		wrong.setFont(font1);
		wrong.setForeground(Color.red);
		wrong.setSize(100,100);
		wrong.setVisible(false);
		registerFrame.add(wrong);
		
		//button
		ensure=new JButton();
		final Font font2=new Font(Font.DIALOG,Font.PLAIN,20);
		ensure.setFont(font2);
		ensure.setSize(20,20);
		ensure.setText("È·¶¨");
		ensure.addActionListener(new EnsureActionListener());
		registerFrame.add(ensure);
		
		registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		registerFrame.setSize(500, 400);
		registerFrame.setLocation(400, 200);
		registerFrame.setVisible(true);
		
	}
	
	public void Close(){
		registerFrame.dispose();
	}
	
	class EnsureActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String userId=username.getText();
			String userpw=password.getText();
			try{
				String userInformation=RemoteHelper.getInstance().getIOService().readFile("", "user",0);
				if(!userInformation.contains(userId)){
					RemoteHelper.getInstance().getIOService().writeFile((userInformation+System.lineSeparator()+userId+" "+userpw),  "", "user",0);
					//RemoteHelper.getInstance().getIOService().writeFile("safsdgfds",  "", "user");
					RemoteHelper.getInstance().getIOService().creatUser(userId);
					wrong.setText("×¢²á³É¹¦");
					wrong.setForeground(Color.green);
					wrong.setVisible(true);
					changeToLogin=true;
				}else{
					wrong.setText("×¢²áÊ§°Ü");
					wrong.setForeground(Color.RED);;
					wrong.setVisible(true);
				}
			}catch(RemoteException e1){
				e1.printStackTrace();
			}
		}
	}
	
	class InputActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			wrong.setVisible(false);
		}
	}
	
}
