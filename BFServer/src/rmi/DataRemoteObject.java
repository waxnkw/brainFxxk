package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.IOService;
import service.UserService;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
	}
	
	@Override
	public boolean creatUser(String userId) throws RemoteException{
		return iOService.creatUser(userId);
	}
	
	@Override
	public boolean writeFile(String file, String userId, String fileName,int version) throws RemoteException{
		return iOService.writeFile(file, userId, fileName,version);
	}
	
	@Override
	public String readFile(String userId, String fileName,int version) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFile(userId, fileName,version);
	}
	
	@Override
	public String readFileList(String userId) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFileList(userId);
	}
	
	@Override
	public boolean updateFiles(String file, String userId, String fileName) throws RemoteException {
		
		return iOService.updateFiles(file, userId, fileName);
	}


	@Override
	public boolean login(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.logout(username);
	}
	
	public boolean changeTo(String mode) throws RemoteException{
		return userService.changeTo(mode);
	}
	
	public String exe(String code ,String param) throws RemoteException {
		return userService.exe(code, param);
	}
	
}
