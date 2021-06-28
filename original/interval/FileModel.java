package interval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileModel {

	public static VGO load(File f){
		FileReader r;
		try {
			r = new FileReader(f);
			BufferedReader buffer = new  BufferedReader(r);
			String bu=buffer.readLine();
			if(bu!=null &&bu.startsWith("intervalModel")){
				return parseVox(f.getName(),buffer);
			}
			buffer.close();
			r.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		Message.m(FileModel.class,"could not find model file "+f.getName());
		return null;
	}

	public static void saveOldModel(File f,VGOraw v){
		FileWriter r;
		try {
			r = new FileWriter(f);
			BufferedWriter buffer = new  BufferedWriter(r);
			//String nam=v.Name.substring(v.Name.lastIndexOf("/")+1,v.Name.lastIndexOf("."));
			//buffer.write("<#"+FileManager.cleanestTextureName(v.Name)+">\n");
			buffer.write("intervalModel\n");
			buffer.write("<param>"+FileManager.cleanestFileName(v.texture)+";"+v.mx+","+v.my+","+v.mz+","+v.top+","+v.sizex+","+v.sizey+"\n");
			if(v.param!=""){
				String[] ss=v.param.split(";");
				for(int u=0;u<ss.length;u++){
					buffer.write("<part"+u+">"+ss[u]+"\n");
				}
			}
			for(int u=0;u<v.pp.length;u++){
				buffer.write("<vox>");
				VecF vf=v.pp[u].f6.pp[0];
				buffer.write(vf.x+","+vf.y+","+vf.z+",");
				/*for(int j=0;j<4;j++){
					VecF vf=v.pp[u].f4.pp[j];
					buffer.write(vf.x+","+vf.y+","+vf.z);
					buffer.write(",");
				}
				for(int j=0;j<3;j++){
					VecF vf=v.pp[u].f2.pp[j];
					buffer.write(vf.x+","+vf.y+","+vf.z);
					buffer.write(",");
				}*/
				vf=v.pp[u].f2.pp[3];
				buffer.write(vf.x+","+vf.y+","+vf.z);
				buffer.write(";");

				buffer.write(v.pp[u].f4.sx+","+v.pp[u].f4.sy+","+v.pp[u].f4.ex+","+v.pp[u].f4.ey+",");
				buffer.write(v.pp[u].f6.sx+","+v.pp[u].f6.sy+","+v.pp[u].f6.ex+","+v.pp[u].f6.ey+",");

				buffer.write(v.pp[u].f2.sx+","+v.pp[u].f2.sy+","+v.pp[u].f2.ex+","+v.pp[u].f2.ey+",");

				buffer.write(v.pp[u].f5.sx+","+v.pp[u].f5.sy+","+v.pp[u].f5.ex+","+v.pp[u].f5.ey+",");

				buffer.write(v.pp[u].f3.sx+","+v.pp[u].f3.sy+","+v.pp[u].f3.ex+","+v.pp[u].f3.ey+",");

				buffer.write(v.pp[u].f1.sx+","+v.pp[u].f1.sy+","+v.pp[u].f1.ex+","+v.pp[u].f1.ey);


				buffer.write("\n");
			}
			//buffer.write("<#/"+nam+">");
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static VGO parseVox(String oName,BufferedReader buffer) throws IOException{
		String str;
		int u;
		float hx=0,hy=0,hz=0;
		float top=0,wideX=0,wideY=0;
		String th="";
		ArrayList<Vox> v=new ArrayList<Vox>();
		boolean atLeastOnce=false;
		boolean combo=false;

		ArrayList<Integer> I=new ArrayList<Integer>();
		ArrayList<String> tex=new ArrayList<String>();

		while((str=buffer.readLine())!=null){
			if((u=str.indexOf("<vox>"))!=-1){
				atLeastOnce=true;
				str=str.substring(u+5);
				int ii=str.indexOf(";");
				v.add(new Vox(parse(str.substring(0,ii).split(",")),parse(str.substring(ii+1).split(","))));
			}else if((u=str.indexOf("<param>"))!=-1){
				str=str.substring(u+7);
				u=str.indexOf(";");
				th=str.substring(0,u);
				float f[]=parse(str.substring(u+1).split(","));
				hx=f[0];hy=f[1];hz=f[2];
				top=f[3];
				wideX=f[4];wideY=f[5];
			}else if(str.contains("<part")){
				combo=true;
				int ii=str.indexOf(">");
				str.substring(5,ii);
				int i2=str.indexOf(":");
				int H=Integer.parseInt(str.substring(ii+1, i2));
				I.add(H);
				tex.add(str.substring(i2+1));
			}
		}
		if(atLeastOnce){
			Message.m(FileModel.class,"successfully made "+th+" ,"+v.size());
			VGO vg=new VGO(th,hx,hy,hz,top,wideX,wideY,th,v.toArray(new Vox[v.size()])); //Texture.get(th)
			if(combo){
				int[] ins=new int[I.size()];
				String[] sins=new String[I.size()];
				for(int i=0;i<tex.size();i++){
					int ii=i*2;
					ins[ii]=I.get(i);
					sins[ii]=tex.get(i);
				}
				vg.set(sins,ins);
			}
			return vg;
		}
		return null;
	}
	public static float[] parse(String s[]){
		float[] f=new float[s.length];
		for(int u=0;u<s.length;u++){
			f[u]=Float.parseFloat(s[u].trim());
		}
		return f;
	}
}
