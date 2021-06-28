package interval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;

public class FileActor {
	private FileActor(){

	}
/*
	public static void load(File f){
		FileReader r;
		try {
			r = new FileReader(f);
			BufferedReader buffer = new  BufferedReader(r);
			String str=buffer.readLine();

			//ArrayList<String> s = new ArrayList<String>();
			Actor act = new Actor();
			if(str.startsWith("intervalActor")){
				//String tName;
				String actorName=str.substring(13).trim();
				//int u;
				while((str=buffer.readLine())!=null){
					if(str.startsWith("<anim>")){
						int u2=str.indexOf("/");
						String key=str.substring(6,u2);
						String val=str.substring(u2+1);
						act.add(key, val);
					}
				}
				ModelManager.addActor(actorName, act);
			}
			buffer.close();
			r.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}*/
/*
	public static void saveVGO(File f,VGOprime v){
		FileWriter r;
		try {
			r = new FileWriter(f);
			BufferedWriter buffer = new  BufferedWriter(r);
			String nam=v.Name.substring(v.Name.lastIndexOf("/")+1,v.Name.lastIndexOf("."));
			buffer.write("<#"+nam+">\n");
			buffer.write("<param>"+FileManager.cleanestFileName(v.texture)+";"+v.mx+","+v.my+","+v.mz+"\n");
			for(int u=0;u<v.pp.length;u++){
				buffer.write("<vox>");
				for(int j=0;j<4;j++){
					VecF vf=v.pp[u].f4.pp[j];
					buffer.write(vf.x+","+vf.y+","+vf.z);
					buffer.write(",");
				}
				for(int j=0;j<3;j++){
					VecF vf=v.pp[u].f2.pp[j];
					buffer.write(vf.x+","+vf.y+","+vf.z);
					buffer.write(",");
				}
				VecF vf=v.pp[u].f2.pp[3];
				buffer.write(vf.x+","+vf.y+","+vf.z);
				buffer.write(";");
				
				buffer.write(v.pp[u].f1.sx+","+v.pp[u].f1.ey+","+v.pp[u].f1.ex+","+v.pp[u].f1.ey+","+v.pp[u].f1.ex+","+v.pp[u].f1.sy+","+v.pp[u].f1.sx+","+v.pp[u].f1.sy+",");
				buffer.write(v.pp[u].f2.sx+","+v.pp[u].f2.ey+","+v.pp[u].f2.ex+","+v.pp[u].f2.ey+","+v.pp[u].f2.ex+","+v.pp[u].f2.sy+","+v.pp[u].f2.sx+","+v.pp[u].f2.sy+",");
				buffer.write(v.pp[u].f3.sx+","+v.pp[u].f3.ey+","+v.pp[u].f3.ex+","+v.pp[u].f3.ey+","+v.pp[u].f3.ex+","+v.pp[u].f3.sy+","+v.pp[u].f3.sx+","+v.pp[u].f3.sy+",");
				buffer.write(v.pp[u].f4.sx+","+v.pp[u].f4.ey+","+v.pp[u].f4.ex+","+v.pp[u].f4.ey+","+v.pp[u].f4.ex+","+v.pp[u].f4.sy+","+v.pp[u].f4.sx+","+v.pp[u].f4.sy+",");
				buffer.write(v.pp[u].f5.sx+","+v.pp[u].f5.ey+","+v.pp[u].f5.ex+","+v.pp[u].f5.ey+","+v.pp[u].f5.ex+","+v.pp[u].f5.sy+","+v.pp[u].f5.sx+","+v.pp[u].f5.sy+",");
				buffer.write(v.pp[u].f6.sx+","+v.pp[u].f6.ey+","+v.pp[u].f6.ex+","+v.pp[u].f6.ey+","+v.pp[u].f6.ex+","+v.pp[u].f6.sy+","+v.pp[u].f6.sx+","+v.pp[u].f6.sy);
				
				buffer.write("\n");
			}
			buffer.write("<#/"+nam+">");
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static FileActor load(String s){
		return new FileActor();
	}
*/
}
