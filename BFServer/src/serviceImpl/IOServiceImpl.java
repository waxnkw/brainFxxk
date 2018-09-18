package serviceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.io.*;

import service.IOService;

public class IOServiceImpl implements IOService{
	public static final int VERSIONS=6;
	
	//creatUser dirs
	public boolean creatUser(String userId){
		File dir=new File(userId);
		if(!dir.exists()){
			dir.mkdirs();
			return true;
		}else{
			return  false;
		}
	}
	
	// write file into user's file folder.   
	@Override
	public boolean writeFile(String file, String userId, String fileName,int version) {
		File f;
		String vers=String.valueOf(version);
		if(userId.equals("")){
			f = new File(userId + fileName);
		}else{
			f = new File(userId + File.separator + fileName+File.separator+fileName+String.valueOf(version));
		}
		//File f = new File("BFServer"+File.separator+userId+File.separator+fileName);
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//read file from user's file folder. when read user, userId=""; filename="user";111
	@Override
	public String readFile(String userId, String fileName,int version) {
		String result="";
		File f;
		String vers=String.valueOf(version);
		if(userId.equals("")){
			f = new File(userId + fileName);
		}else{
			f = new File(userId + File.separator + fileName+File.separator+fileName+vers);
		}
		//File f = new File("BFServer/"+userId + File.separator + fileName);
		try{
			InputStreamReader read=new InputStreamReader(new FileInputStream(f));
			BufferedReader bf=new BufferedReader(read);
			String line=null;
			while((line=bf.readLine())!=null){
				result+=line+System.lineSeparator();
			}
			bf.close();
			return result;
		}catch(IOException e){
			e.printStackTrace();
			return "";
		}
	}
	
	//get file names from user's file folder.
	@Override
	public String readFileList(String userId) {
		String result="";
		//String filepath="BFServer"+File.separator+userId;
		String filepath=userId;
		File file=new File(filepath);
		File[] list=file.listFiles();
		try{
			for(int i=0;i<list.length;i++){
				//if(list[i].isFile()){
					result+=list[i].getName()+" ";
				//}
			}
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return "readFileList wrong";
		}
	}

	@Override
	public boolean updateFiles(String file, String userId, String fileName) {
		String path=userId+File.separator+fileName;
		File dir=new File(path);
		if(!dir.exists()){
			dir.mkdirs();
			for(int i=0;i<VERSIONS;i++){
				writeFile(file,userId,fileName,i);
			}
		}
		else{
			for(int i=VERSIONS-1;i>=0;i--){
				String eachPath=path+File.separator+fileName;
				File now=new File(eachPath+String.valueOf(i));
				File next=new File(eachPath+String.valueOf(i+1));
				if(now.exists()&&now.isFile()){
					now.renameTo(next);
				}
			}
			File last=new File(path+File.separator+fileName+VERSIONS);
			last.delete();
			writeFile(file,userId,fileName,0);
		}
		return true;
	}

}
