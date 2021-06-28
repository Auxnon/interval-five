package interval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLevel {

	public static Level load(File f){
		Level L=new Level(LevelManager.getLvlNum());
		try {
			BufferedReader buf= new BufferedReader(new FileReader(f));
			String line="";
			while((line=buf.readLine())!=null){
				int u=line.indexOf(":");
				if(u>1){
					String key=line.substring(0, u);
					String val=line.substring(u+1);
					parse(L,key,val);
				}
			}
			buf.close();
		} catch (FileNotFoundException e) {
			Message.er(e);
		} catch (IOException e) {
			Message.er(e);
		}
		return L;
	}
	
	public static void parse(Level L,String key,String value){
		if(key.contains("name")){
			L.setLevelName(value.trim());
		}else if(key.contains("interval")){
			int uu=value.indexOf(":");
			String end=value.substring(uu+1).trim();
			String start=value.substring(0,uu).trim();
			dateParse(start);
			dateParse(end);
		}else if(key.contains("goal")){
			objParse( L,value);

		}else if(key.contains("map")){
			
			
			L.setMap(value);
		//}else if(key.contains("map")){

		//}else if(key.contains("map3")){

		}else if(key.contains("size")){

		}else if(key.contains("start")){

		}
	}
	public static int[] dateParse(String st){
		int u=st.indexOf("/");
		int month=Integer.parseInt(st.substring(0, u));
		st=st.substring(u+1);
		u=st.indexOf("/");
		int days=Integer.parseInt(st.substring(0,u));
		st=st.substring(u+1);
		u=st.indexOf("/");
		int year=0;
		int hour=0;
		if(u==-1){
			year=Integer.parseInt(st.trim());
		}else{
			year=Integer.parseInt(st.substring(0,u));
			hour=Integer.parseInt(st.substring(u+1));
		}
		return new int[]{days,month,year,hour};
	}
	
	public static void objParse(Level L,String val){

		int st=val.indexOf("[");
		int en=val.indexOf("]");
		int colo=val.indexOf(":");
		if(st<0 || en<0 || colo<0){
			return;
		}
		String command=val.substring(st+1,colo);
		String details=val.substring(colo+1,en);
		String text=val.substring(0, st);
		Objective obj=null;
		if(command.contains("point")){
			obj=new ObjectivePoint(text,details);
		}else if(command.contains("damage")){

		}else if(command.contains("erase")){

		}else if(command.contains("kill")){

		}else if(command.contains("protect")){

		}else if(command.contains("pristine")){

		}else if(command.contains("unite")){

		}

		if(obj!=null){
			obj.setName(text);
			L.objectives.add(obj);
		}
	}
}
