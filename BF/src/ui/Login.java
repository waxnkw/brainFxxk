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
import ui.Register.EnsureActionListener;
import ui.Register.InputActionListener;
public class Login extends JFrame{
	
	public String userId;
	private JFrame loginFrame;
	private JLabel headLabel;
	private JPanel pl1;
	private JPanel pl2;
	private JPanel pl3;
	private JTextField username;
	private JPasswordField password;
	private JLabel username_l;
	private JLabel password_l;
	private JLabel show;
	private JButton ensure;
	private JButton regis;
	
	public boolean changeToRegis=false;//change to registerFrame
	public boolean changeToMain=false;//change to mainFrame
	
	//constructor
	public Login(){
		//-----------------------------------main frame---------------------------------------------//
		loginFrame=new JFrame("Log In");
		GridLayout gl=new GridLayout(5,1,10,40);
		loginFrame.setLayout(gl);
		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//-----------------------------------head label---------------------------------------------//
		headLabel=new JLabel("",JLabel.CENTER);
		final Font font=new Font(Font.DIALOG,Font.PLAIN,40);
		headLabel.setFont(font);
		headLabel.setText("µÇÂ¼");
		headLabel.setOpaque(true);
		headLabel.setForeground(Color.red);
		headLabel.setBackground(Color.gray);
		loginFrame.add(headLabel);
		
		//----------------------------------- panels ---------------------------------------------//
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
		password=new JPasswordField(6);
		password.setSize(100, 100);
		
		username.addActionListener(new InputActionListener());
		password.addActionListener(new InputActionListener());
		
		pl1.add(username_l);
		pl1.add(username);
		pl2.add(password_l);
		pl2.add(password);
		
		loginFrame.add(pl1);
		loginFrame.add(pl2);
		
		//-------------------------------------------
		show=new JLabel("µÇÂ¼Ê§°Ü",JLabel.CENTER);
		show.setFont(font1);
		show.setForeground(Color.red);
		show.setSize(100,100);
		show.setVisible(false);
		loginFrame.add(show);
		
		//----------------------------
		ensure=new JButton();
		final Font font2=new Font(Font.DIALOG,Font.PLAIN,20);
		ensure.setFont(font2);
		ensure.setSize(20,20);
		ensure.setText("È·¶¨");
		ensure.addActionListener(new EnsureActionListener());
		
		regis=new JButton();
		regis.setFont(font2);
		regis.setSize(20,20);
		regis.setText("×¢²á");
		regis.addActionListener(new RegisActionListener());
		
		pl3=new JPanel();
		pl3.setLayout(new FlowLayout());
		pl3.add(ensure);
		pl3.add(regis);
		loginFrame.add(pl3);
		//-------------------------------------
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(500, 400);
		loginFrame.setLocation(400, 200);
		loginFrame.setVisible(true);
	}
	
	public void Close(){
		loginFrame.dispose();
	}
	
	class EnsureActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String userIdn=username.getText();
			char[] userp=password.getPassword();
			String userpw="";
			for(int i=0;i<userp.length;i++){
				userpw+=userp[i];
			}
			try{
				boolean logsuccess=RemoteHelper.getInstance().getUserService().login(userIdn, userpw);
				if(logsuccess){//login success
					System.out.println("success");
					//loginFrame.setVisible(false);
					//Login.this.setVisible(false);
					userId=userIdn;
					changeToMain=true;
				}else{//login failed
					show.setText("µÇÂ¼Ê§°Ü");
					show.setForeground(Color.RED);;
					show.setVisible(true);
				}
			}catch(RemoteException e1){
				e1.printStackTrace();
			}
		}
	}

	class RegisActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			changeToRegis=true;
		//	System.out.println(changeToRegis);
		}
	}
	class InputActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			show.setVisible(false);
		}
	}

}
