//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	public boolean login(String username, String password) throws RemoteException;

	public boolean logout(String username) throws RemoteException;
	
	public String exe(String code ,String param) throws RemoteException;
	
	public boolean changeTo(String mode) throws RemoteException;
}
