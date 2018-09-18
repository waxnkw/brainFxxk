
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean creatUser(String userId) throws RemoteException;
	
	public boolean writeFile(String file, String userId, String fileName,int versions)throws RemoteException;
	
	public String readFile(String userId, String fileName,int versions)throws RemoteException;
	
	public String readFileList(String userId)throws RemoteException;
	
	public boolean updateFiles(String file, String userId, String fileName)throws RemoteException;
}
