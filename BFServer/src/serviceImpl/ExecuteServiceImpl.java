
package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {

	@Override
	public String execute(String code, String param) throws RemoteException {
		code=code.trim();
		String result="";
		char [] cod=code.toCharArray();
		char [] ptrs=new char[100];
		int index=50;//can be traced to left in the first step
		int i=0;
		int indexParam=0;
		
		//init the stack
		for(int j=0;j<ptrs.length;j++){
			ptrs[j]=0;
		}
		
		//interpret the code
		while(i<cod.length){
			switch(cod[i]){
			case '<':
				index--;
				i++;
				break;
			case '>':
				index++;
				i++;
				break;
			case ',':
				if(indexParam<param.length()){
					//System.out.println(indexparam);
					ptrs[index]=param.charAt(indexParam);
				}
				indexParam++;
				i++;
				break;
			case '.':
				result+=ptrs[index]+" ";
				i++;
				break;
			case '+':
				ptrs[index]++;
				i++;
				break;
			case '-':
				ptrs[index]--;
				i++;
				break;
				
			case '[':
				int respond1=0;
				while(ptrs[index]==0&&i<cod.length){
					if(cod[i]=='['){
						respond1++;
					}
					if(cod[i]==']'){
						respond1--;
						if(respond1<=0){
							break;
						}else{
							
						}
					}
					i++;
				}
				i++;
				break;
				
			case ']':
				int respond2=0;
				while(ptrs[index]!=0&&i>=0){
					if(cod[i]==']'){
						respond2++;
					}
					if(cod[i]=='['){
						respond2--;
						if(respond2<=0){
							break;
						}else{
							
						}
					}
					i--;
				}
				i++;
				break;
			default:
				i++;
				break;
			}
		}
		return result;
	}
	
}
