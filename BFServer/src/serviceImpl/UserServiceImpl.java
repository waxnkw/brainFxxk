package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;
import service.UserService;

public class UserServiceImpl implements UserService{
	ExecuteService ex=new ExecuteServiceImpl();
	IOServiceImpl io=new IOServiceImpl();
	
	@Override
	public boolean login(String username, String password) throws RemoteException {
		String userall=io.readFile("", "user",0);
		String [] userInfor=userall.split(System.lineSeparator());
		for(int i=0;i<userInfor.length;i++){
			if((username+" "+password).equals(userInfor[i])){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}
	
	//exe the code
	@Override
	public String exe(String code ,String param) throws RemoteException {
		return ex.execute(code, param);
	}
	
	//Change to BF mode or OOk mode
	@Override
	public boolean changeTo(String mode) {
		System.out.println(mode.substring(mode.length()-2,mode.length()));
		switch(mode.substring(mode.length()-2,mode.length())){
		case "bf":
			ex=new ExecuteServiceImpl();
			break;
		case "ok":
			ex=new OokServiceImpl();
			break;
		}
		return true;
	}
}
