package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;
import service.UserService;

public class OokServiceImpl implements ExecuteService{
	ExecuteServiceImpl ex=new ExecuteServiceImpl();
	@Override
	public String execute(String code, String param) throws RemoteException {
		String codeBf="";
		for(int i=0;i<code.length()/10;i++){
			switch(code.substring(i*10,i*10+10)){
			case "Ook. Ook? ":
				codeBf+=">";
				break;
			case "Ook? Ook. ":
				codeBf+="<";
				break;
			case "Ook. Ook. ":
				codeBf+="+";
				break;
			case "Ook! Ook! ":
				codeBf+="-";
				break;
			case "Ook! Ook. ":
				codeBf+=".";
				break;
			case "Ook. Ook! ":
				codeBf+=",";
				break;
			case "Ook! Ook? ":
				codeBf+="[";
				break;
			case "Ook? Ook! ":
				codeBf+="]";
				break;
			}
		}
		//System.out.println(codeBf);
		return ex.execute(codeBf, param);
	}
	
}
