package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import rmi.RemoteHelper;
import service.IOService;
import ui.Login;
import ui.MainFrame;
import ui.New;
import ui.Register;

public class ClientRunner {
	MainFrame mainFrame;
	Register regisFrame;
	Login    loginFrame;
	New      newFrame;
	private RemoteHelper remoteHelper;
	private String userId;
	
	public ClientRunner() {
		linkToServer();
		loginFrame=new Login();
	}
	
	//main logic 
	private void go(){
			
		//loginFrame exist condition
			if(loginFrame!=null){
				if(loginFrame.changeToMain){
					userId=loginFrame.userId;
					loginFrame.Close();
					sleep(30);
					loginFrame=null;
					mainFrame=new MainFrame(userId);
				}
				else if(loginFrame.changeToRegis){
					loginFrame.Close();
					sleep(10);
					loginFrame=null;
					if(loginFrame==null){
						System.out.println("shehui");
					}
					regisFrame=new Register();
				}
			}
		//regisFrame exist condition
			else if(regisFrame!=null){
				if(regisFrame.changeToLogin){
					regisFrame.Close();
					sleep(10);
					regisFrame=null;
					loginFrame=new Login();
				}
			}
		//mainFrame exist condition
			else if(mainFrame!=null){
				if(mainFrame.changeToLogin){
					mainFrame.Close();;
					sleep(10);
					mainFrame=null;
					userId="";
					loginFrame=new Login();
				}
				else if(mainFrame.openNew){//open newFrame
					mainFrame.openNew=false;
					showNew();
				}
			}
		//newFrame exist condition 
			if(newFrame!=null){
				if(newFrame.CloseNew){
					mainFrame.SetCode(newFrame.Open(userId));
					mainFrame.fileName=newFrame.GetFileName();
					mainFrame.userId=userId;
					mainFrame.SetVersions(); 					//set versions menu
					newFrame.Close();
					newFrame=null;
				}
			}
	}
	private void linkToServer() {
		try {
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://127.0.0.1:8887/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	//use to show NewFrame
	private void showNew(){
		try {
			System.out.println(userId);
			System.out.println(RemoteHelper.getInstance().getIOService().readFileList(userId));
			newFrame=new New(RemoteHelper.getInstance().getIOService().readFileList(userId).split(" "));
			newFrame.userId=userId;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void sleep(long x){
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		ClientRunner cr = new ClientRunner();
		while(true){
			cr.go();
			cr.sleep(1);
		}
	}
}
