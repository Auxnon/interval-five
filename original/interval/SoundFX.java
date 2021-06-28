package interval;

import java.util.HashMap;

public class SoundFX {


	public static final SoundFX ribbit;
	public static final SoundFX boop;
	public static final SoundFX jump;
	public static final SoundFX fall;
	public static final SoundFX piff;
	public static final SoundFX chirp;
	public static void init(){
		
	}
	static{
		float[] f=new float[]{0.5f,1,0.5f};
		ribbit=new SoundFX(Main.sound.science(Main.sound.genGradient(10000,0.01,64,200,f),20000));

		float[] ff=new float[]{1f,0.5f,1f,0.5f,1f};
		boop=new SoundFX(Main.sound.science(Main.sound.genGradient(2000,0.01,120,1000,ff),1000));
		ff=new float[]{0.5f,1};
		jump=new SoundFX(Main.sound.science(Main.sound.genGradient(2000,0.01,64,200,ff),4000));

		ff=new float[]{1,.5f};
		fall=new SoundFX(Main.sound.science(Main.sound.genGradient(2000,0.01,120,200,ff),4000));

		ff=new float[]{0.6f,1,0.4f,1,0.8f,0.2f};
		chirp=new SoundFX(Main.sound.science(Main.sound.genGradient(2000,0.01,120,300,ff),4000));

		ff=new float[]{1f,0.2f,0};
		piff=new SoundFX(Main.sound.science(Main.sound.genGradient(300,0,64,100,ff),600));
		soundBank=new HashMap<String,SoundFX>();
		
		
		//ffg = 3898,0.0,64,259,[0.021739125,0.9492754,0.061594248,0.9601449,0.5],3800

		//boo hooo=6966,0.53,64,896,[0.5072464,0.49275362],6100
	}

	int sound;
	String name;
	public SoundFX(int i){
		sound=i;
		name="";
	}
	public int getInt(){
		return sound;
	}
	public SoundFX(String name,int i){
		this.name=name;
		sound=i;
	}
	public void play(){
		//Main.sound.runInstance(sound);
	}
	public void play(float[] f){
	//	Main.sound.runInstance(sound,f);
	}
	public void play(float x,float y,float z){
		//Main.sound.runInstance(sound,x,y,z);
	}
	public static void add(SoundFX s){
		soundBank.put(s.name,s);
	}

	public static SoundFX get(String n){
		SoundFX sx=soundBank.get(n);
		if(sx==null){
			sx=FileManager.loadSound(n);
			if(sx==null)
				sx=boop;
			add(sx);
		}
		return sx;
	}
	public static void play(String n){
		SoundFX sfx=get(n);
		if(sfx!=null){
			sfx.play();
		}
	}

	public static HashMap<String,SoundFX>soundBank;
}
