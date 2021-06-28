package interval;

import intervalEntity.EntityManager;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class FileManager {

	public static String path="./data/";

	public static String dir(){
		return path;
	}
	public static void init(){
		scanModels();
		scanActors();
		scanLevels();
		EntityManager.init();
	}

	public static String[] getTextures(){
		File f =new File(getTextureDir());
		String ss[]=f.list();
		ArrayList<String> st=new ArrayList<String>();
		for(int u=0;u<ss.length;u++){
			if(ss[u].toLowerCase().endsWith(".png")){
				st.add(ss[u]);
			}
		}
		return st.toArray(new String[st.size()]);
	}
	public static void scanModels(){
		File models = new File(getModelsDir());
		if(!models.exists()){
			make(models);
		}else{
			File[] f=models.listFiles();
			for(int u=0;u<f.length;u++){
				if(f[u].getName().endsWith(".model")){
					VGO vgo=FileModel.load(f[u]);
					if(vgo!=null)
						ModelManager.addModel(vgo.toString(), vgo);
				}
			}
		}
	}
	public static void scanActors(){
		/*File actors = new File(getActorsDir());
		if(!actors.exists()){
			 make(actors);
		}else{
			File[] f=actors.listFiles();
			for(int u=0;u<f.length;u++){
				if(f[u].getName().endsWith(".actor")){
					FileActor.load(f[u]);
				}
			}
		}*/
	}
	public static void scanLevels(){
		File levels = new File(getLevelsDir());
		if(!levels.exists()){
			make(levels);
		}else{
			File[] f=levels.listFiles();
			for(File F:f){
				if(F.getName().endsWith(".lvl")){
					m("found lvl: "+F.getName()+" loading...");
					Level L=FileLevel.load(F);
					if(L!=null){
						m("lvl: "+F.getName()+" loaded!");
						LevelManager.add(L);
					}else{
						m("lvl: "+F.getName()+" failed :(");
					}
				}
			}
		}
	}
	public static void make(File f){
		f.mkdir();
		m(f.getPath()+" not found, creating directory");
	}
	public static void m(String s){
		Message.m(FileManager.class,s);
	}

	public static VGO loadModel(String s){
		File mod=new File(getModelsDir()+s+".model");
		if(mod.exists()){
			return FileModel.load(mod);
		}
		return null;
	}
	public static SoundFX loadSound(String s){
		File mod=new File(getSoundDir()+s+".sfx");
		BufferedReader re=getReader(mod);
		String st="";
		SoundFX sfx=null;
		try {
			while(( st=re.readLine())!=null){
				sfx=processSound(st);
				SoundFX.add(sfx);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sfx;
	}
	
	public static SoundFX processSound(String s){
		if( s.contains(",")&&s.contains("{")){
			String n="";
			if( s.contains(":")){
				String ss[]=s.split(":");
				s=ss[1];
				n=ss[0].trim();
			}
			s=s.substring(3);
			String s1[]=s.split("{");
			String s2[]=s1[1].split("}");
			String o[]=(s1[0]+s2[1]).split(",");
			String a[]=s2[0].split(",");
			int T=Integer.parseInt(o[0]);
			float cutt=Float.parseFloat(o[1]);
			double amp=Double.parseDouble(o[2]);
			int freqq=Integer.parseInt(o[3]);
			int qual=Integer.parseInt(o[4]);
			return new SoundFX(n,Main.sound.science(Main.sound.genGradient(T,cutt,amp,freqq,toFloat(a)),qual));
		}
		return null;
	}
	private static float[] toFloat(String[] s){
		float[] f=new float[s.length];
		for(int u=0;u<s.length;u++){
			f[u]=Float.parseFloat(s[u]);
		}
		return f;
	}

	public static BufferedImage loadImage(String ref)
	{ 
		ref=FileManager.perfectTexture(ref);
		BufferedImage bufferedImage = null;
		try {
			File ff=new File(ref);
			bufferedImage = ImageIO.read(ff);
		} catch (IOException e) {
			Message.m(FileManager.class,"Texture unavailable @: "+ref+"\n");
			if(UserData.errorMessage()){
				e.printStackTrace();
			}
			return null;
		}
		return bufferedImage;
	}


	//check
	public static void saveToVGO(VGOraw v,String der) {
		File f=new File(getModelsDir());
		if(!f.exists()){
			f.mkdir();
		}
		String nam=der;
		if(nam.equals(""))
			nam=v.Name.substring(v.Name.lastIndexOf("/")+1,v.Name.lastIndexOf("."));
		File vo = new File(getModelsDir()+nam+".model");
		if(vo.exists()){
			//vo.delete();
			//vo.
		}
		FileModel.saveOldModel(vo, v);
	}
	public static String getTextureDir(){
		return dir()+"Texture/";
	}
	public static String getModelsDir(){
		return dir()+"Models/";
	}
	public static String getActorsDir(){
		return dir()+"Actors/";
	}
	public static String getSoundDir(){
		return dir()+"Sound/";
	}
	public static String getLevelsDir(){
		return dir()+"Levels/";
	}
	public static String getSavesDir(){
		return dir()+"Saves/";
	}
	public static String cleanFileName(String s){
		return s.substring(s.lastIndexOf("/")+1);
	}
	public static String cleanestFileName(String s){
		if(s.contains(".")){
			return s.substring(s.lastIndexOf("/")+1,s.lastIndexOf("."));
		}else{
			return cleanFileName(s);
		}
	}
	public static BufferedImage[] loadMap(String name){
		String ss=cleanestFileName(name);
		File f = new File(getTextureDir()+ss+".png");
		File f2 = new File(getTextureDir()+ss+"_height.png");
		if(f.exists() && f2.exists()){
			try {
				BufferedImage[] bim={ImageIO.read(f),ImageIO.read(f2)};
				Message.m(FileManager.class,"successfully loaded map "+name);
				return bim;
			} catch (IOException e) {
				Message.er(e);
			}
		}
		return null;
	}
	public static File newLevelFile(String name){
		String ss=cleanestFileName(name);
		File f = new File(getLevelsDir()+ss+".ent");
		return f;
	}
	public static void loadEntityMap(String name){
		String ss=cleanestFileName(name);
		File f = new File(getLevelsDir()+ss+".ent");
		if(f.exists()){
			EntityManager.parse(f);
		}
	}
	public static String saveImage(String n,BufferedImage im){
		try {
			File f=new File(getTextureDir());
			if(!f.exists())
				f.mkdir();

			n=cleanestFileName(n);
			Message.m(FileManager.class,getTextureDir()+n);
			ImageIO.write(im,"PNG",new File(getTextureDir()+n+".png"));
			return n;
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return "";
	}
	public static File loadSound(String s,String extension){
		return new File(getSoundDir()+cleanestFileName(s)+"."+extension);
	}
	public static String perfectTexture(String str) {
		return getTextureDir()+cleanestFileName(str)+".png";
	}

	public static void intervalLines(){
		File temp = new File("./src/interval");
		int lines=0;
		int line2=0;
		Message.m(FileManager.class,temp.getAbsolutePath());
		if(temp.exists()){
			File files[]=temp.listFiles();
			for(int i=0;i<files.length;i++){
				try {
					BufferedReader br = new BufferedReader(new FileReader(files[i]));
					String str;
					while((str=br.readLine())!=null){
						lines+=(str.length()-str.replaceAll(";", "").length());
						if(!str.isEmpty()){
							line2++;}
					}
				} catch (FileNotFoundException e) {

				} catch (IOException e) {

				}
			}
			Message.m(FileManager.class,"Class count: "+files.length+" Line count: "+lines +" newline count: "+line2);
		}
	}

	
	public static BufferedReader getReader(File f){
		try {
			if(f.exists()){
				FileReader fr= new FileReader(f);
				BufferedReader br=new BufferedReader(fr);
				return br;
			}
		} catch (IOException e) {
			//Message.er(e);
		}
		return null;
	}
	public static BufferedReader getProgressReader(){
		File f=new File(dir()+"progress");
		try {
			if(!f.exists()){
				f.createNewFile();
			}else{
				FileReader fr= new FileReader(f);
				BufferedReader br=new BufferedReader(fr);
				return br;
			}
		} catch (IOException e) {
			Message.er(e);
		}
		return null;
	}
	public static BufferedWriter getProgressWriter(){
		File f=new File(dir()+"progress");
		try {
			if(!f.exists())
				f.createNewFile();
			FileWriter fr= new FileWriter(f);
			BufferedWriter br=new BufferedWriter(fr);
			return br;
		} catch (IOException e) {
			Message.er(e);
		}
		return null;
	}
}
