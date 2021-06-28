package intervalEntity;


import interval.FileManager;
import interval.Main;
import interval.Message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class EntityManager {

	static HashMap<String,Class<? extends Entity>> map;
	public static void init(){
		map=new HashMap<String,Class<? extends Entity>>();
		map.put("enemy", EntityEnemy.class);
		map.put("button", gButton.class);
		map.put("platform", gPlatform.class);
		map.put("timer", gTimer.class);
		map.put("counter", gCounter.class);
		map.put("npc", EntityNPC.class);
		map.put("human", Human.class);
		map.put("prop", Prop.class);
	}

	public static boolean infer(String type, String param,String extra,int line){
		Entity E;
		try {
			Class<? extends Entity> c=map.get(type.toLowerCase());
			if(c!=null){
				E = c.cast( c.newInstance());
				E.infer(param);
				if(!extra.equals("")){
					E.extra(extra);
				}
				Main.world.addE(E);

				return true;
			}else{
				Message.er("\nUnknown Entity type: "+type);
			}
		} catch(ArrayIndexOutOfBoundsException e){
			Message.er("\nEntity "+type+" on line "+line+" is formatted wrong!");
			return false;
		} catch (InstantiationException e) {
			Message.er(e);
		} catch (IllegalAccessException e) {
			Message.er(e);
		}
		return false;
	}

	public static void parse(File f) {
		try {
			BufferedReader br =new BufferedReader(new FileReader(f));
			String buffer="";
			int line=0;
			while((buffer=br.readLine())!=null){
				String st[]=buffer.split(":");
				if(st.length>1)
					infer(st[0],st[1],st.length>=3?st[2]:"",line);

				line++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			Message.er(e);
		} catch (IOException e) {
			Message.er(e);
		}
	}
	public static void createMap(String name){
		File f=FileManager.newLevelFile(name);
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				Message.er(e);
			}
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			for(int u=0;u<Main.world.sizeE();u++){
				bw.write(Main.world.getE(u).toString()+"\n");
			}
			bw.close();
		} catch (IOException e) {
			Message.er(e);
		}
	}

}
