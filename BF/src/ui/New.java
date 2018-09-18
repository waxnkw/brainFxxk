package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rmi.RemoteHelper;
import ui.Login.EnsureActionListener;

public class New extends JFrame {
	public String[] fileName;
	public String code;
	public String userId;
	private JFrame frame;
	private JList fileList;
	private JTextArea fileArea;
	private JButton ensure;
	private JPanel p1;
	public boolean CloseNew=false;
	public New(String[] names){
		
		//-----------------------------main frame--------------------------------//
		frame = new JFrame("File");
		frame.setLayout(new GridLayout(2,1));
		//--------------------------------
		fileList=new JList(names);
		//fileList.setListData(fileName);
		fileList.addListSelectionListener(new FileListSelectionListener());
		fileList.setVisible(true);
		frame.add(fileList);
		//-----------------------------
		JPanel p1=new JPanel();
		p1.setLayout(new FlowLayout(2));
		
		fileArea=new JTextArea();
		fileArea.setColumns(15);
		fileArea.setName("file");
		fileArea.setVisible(true);
		p1.add(fileArea,FlowLayout.LEFT);
		
		ensure=new JButton("È·¶¨");
		ensure.addActionListener(new EnsureActionListener());
		ensure.setVisible(true);
		p1.add(ensure,FlowLayout.CENTER);
		
		frame.add(p1);
		
		//-------------------
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLocation(400, 200);
		frame.setVisible(true);
	}
	
	public void Close(){
		frame.dispose();
	}
	//clientserver use to open file
	public String Open (String userId){
		String name=fileArea.getText();
		try {
			code=RemoteHelper.getInstance().getIOService().readFile(userId,name,0);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return code;
	}
	public String GetFileName(){
		return fileArea.getText();
	}
	class EnsureActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			CloseNew=true;
		}
	}
	
	class FileListSelectionListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e){
			fileArea.setText((String)fileList.getSelectedValue());
		}
	}
}
