package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import rmi.RemoteHelper;


public class MainFrame extends JFrame {
	public String userId;
	public String fileName;
	public String code;
	
	public String []stack=new String[5];
	public int index=2;
	
	public New open;
	private JMenu versionsMenu;
	private JFrame frame;
	private JTextArea textArea;
	private JTextArea inputArea;
	private JLabel resultLabel;
	private JPanel p1;
	public boolean openNew=false;
	public boolean changeToLogin=false;
	public MainFrame(String userId) {
		
		this.userId=userId;
		
		//-----------------------------
		frame = new JFrame("”√ªß  £∫ "+this.userId);
		frame.setLayout(new BorderLayout());
		//----------------------------
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		fileMenu.add(quitMenuItem);
		
		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		quitMenuItem.addActionListener(new QuitActionListener());
		//-------------------------------------------
		versionsMenu = new JMenu("Versions");
		menuBar.add(versionsMenu);
		versionsMenu.setVisible(false);
		//-----------------------------------------

		JMenu operateMenu = new JMenu("Operate");
		menuBar.add(operateMenu);
		JMenuItem exeMenuItem = new JMenuItem("execuate");
		operateMenu.add(exeMenuItem);
		JMenuItem undoMenuItem = new JMenuItem("undo");
		operateMenu.add(undoMenuItem);
		JMenuItem redoMenuItem = new JMenuItem("redo");
		operateMenu.add(redoMenuItem);
		
		undoMenuItem.addActionListener(new UndoActionListener());
		redoMenuItem.addActionListener(new RedoActionListener());
		exeMenuItem.addActionListener(new ExeActionListener());
		
		frame.setJMenuBar(menuBar);
		
		//--------------------------------------------
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		textArea.addKeyListener(new textKeyListener());
		textArea.setMargin(new Insets(10, 10, 10, 10));
		final Font inputFont=new Font(Font.DIALOG,Font.PLAIN,30);
		textArea.setFont(inputFont);
		textArea.setOpaque(true);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setEnabled(false);
		frame.add(textArea, BorderLayout.CENTER);
		
		
		inputArea = new JTextArea();
		inputArea.setMargin(new Insets(10, 10, 10, 10));
		inputArea.setColumns(10);
		inputArea.setBackground(Color.white);
		inputArea.setVisible(true);
		p1.add(inputArea,BorderLayout.WEST);
		
		resultLabel = new JLabel();
		resultLabel.setText("result");
		p1.add(resultLabel,BorderLayout.EAST);
		frame.add(p1, BorderLayout.SOUTH);
		//--------------------------------------------
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLocation(400, 200);
		frame.setVisible(true);
	}
	
	public void Close(){
		frame.dispose();
	}
	
	//get and show different versions 
	public void SetVersions(){
		versionsMenu.removeAll();
		for(int i=0;i<6;i++){
			JMenuItem versionMenuItem = new JMenuItem(fileName+i);
			versionMenuItem.setVisible(true);
			versionMenuItem.addActionListener(new VersionActionListener(i));
			versionsMenu.add(versionMenuItem);
		}
		versionsMenu.setVisible(true);
		for(int i=0;i<stack.length;i++){
			stack[i]=null;
		}
		stack[index]=code;
	}
	
	public void SetCode(String code){
		textArea.setEnabled(true);
		textArea.setText(code);
	}
	
	//use for moving stack[5]
	public void leftShift(){
		for(int i=0;i<stack.length-1;i++){
			stack[i]=stack[i+1];
		}
		stack[stack.length-1]=null;
	} 
	public void rightShift(){
		for(int i=stack.length-1;i>0;i--){
			stack[i]=stack[i-1];
		}
		stack[0]=null;
	}
	
	class MenuItemActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				openNew=true;
			} else if (cmd.equals("New")) {
				openNew=true;
			} else if (cmd.equals("Run")) {
				resultLabel.setText("Hello, result");
			}
		}
	}

	
	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = textArea.getText();
			System.out.println(code);
			try {
				RemoteHelper.getInstance().getIOService().updateFiles(code, userId, fileName);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	class ExeActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			code = textArea.getText();
			String para=inputArea.getText();
			try {
				RemoteHelper.getInstance().getUserService().changeTo(fileName);
				String result=RemoteHelper.getInstance().getUserService().exe(code, para);
				resultLabel.setText(result);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	class VersionActionListener implements ActionListener{
		int version=0;
		public VersionActionListener(int versionNow){
			version=versionNow;
		}
		public void actionPerformed(ActionEvent e){
			try {
				code=RemoteHelper.getInstance().getIOService().readFile(userId, fileName, version);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			textArea.setText(code);
		}
	}
	
	class UndoActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			rightShift();
			if(stack[index]!=null){
				code=stack[index];
				textArea.setText(code);
			}
		}
	}
	
	class RedoActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			leftShift();
			if(stack[index]!=null){
				code=stack[index];
				textArea.setText(code);
			}
		}
	}
	
	class textKeyListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent k) {
//			if(k.getKeyCode()==17){//press ctrl 
//				ctrl=true;
//			}
		}

		@Override
		public void keyReleased(KeyEvent k) {
		}

		@Override
		public void keyTyped(KeyEvent k) {
			if((code=textArea.getText())!=stack[index]){
				leftShift();
				stack[index]=code;
			}
		}	
	}
	
	class QuitActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			changeToLogin=true;
		}
	}
}
