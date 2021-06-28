package intervalParticle;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import interval.Main;
import interval.Render;
import interval.Texture;
import interval.Vec;

public class PWord extends Particle{

	float Fx;
	float indx;
	float indy;
	float Fy;
	int T;
	
	public static final short COMMAND=0;
	public static final short COME=1;
	public static final short CAUTION=2;
	public static final short STOP=3;
	public static final short BANK=4;
	public static final short DUNNO=5;
	public static final short YES=6;
	public static final short NO=7;
	public static final short POOP=8;
	public static final short CHAT=16;
	public static final short HAPPY=32;
	public static final short ANGRY=33;
	public static final short BOTHERED=34;
	public static final short DISSAPOINTED=35;
	public static final short SAD=36;
	public static final short SHOCK=37;
	public static final short FEAR=38;
	public static final short DEVIOUS=39;
	public static final short EVIL=40;
	public static final short DUCK=44;
	public static final short DERR=45;
	public static final short DOINK=46;
	public static final short CREEPY=47;
	public static HashMap<String,Short> set;
	static{
		set=new HashMap<String,Short>();
		set.put("Command", COMMAND);
		set.put("Come",COME);
		set.put("Caution",CAUTION);
		set.put("Stop",STOP);
		set.put("Bank",BANK);
		set.put("Dunno",DUNNO);
		set.put("Yes",YES);
		set.put("No",NO);
		set.put("Poop",POOP);
		set.put("Chat",CHAT);
		set.put("Happy", HAPPY);
		set.put("Angry",ANGRY);
		set.put("Bothered",BOTHERED);
		set.put("Dissapointed",DISSAPOINTED);
		set.put("Sad",SAD);
		set.put("Shock", SHOCK);
		set.put("Fear",FEAR);
		set.put("Devious", DEVIOUS);
	}
	public PWord(float x,float y,float z,short f){
		this(x,y,z,(int)f);
	}
	public PWord(float x,float y,float z,int f){
		this.x=x;
		this.y=y;
		this.z=z;
		vz=0.04f;
		buffer=0.9f;
		az=-0.00001f;
		bounce=0f;
		size1=0;
		size2=0.5f;
		sizeRange=10;
		alpha1=20;
		alpha2=0;
		alphaRange=200;
		lifetime=200;
		color=new Vec(1,1,1);
		indx=1/16f;
		indy=1/4f;
	
		if(f==CHAT){
			f+=Main.rand.nextInt(9);
		}
		Fx=(f%16)*indx;
		Fy=(float) Math.floor(f/16)*indy;
		T=Texture.get("talk.png");
	}
	
	public void render(){
		
		GL11.glPushMatrix();
		GL11.glColor4f(color.x, color.y, color.z, alpha);
		GL11.glTranslatef(x, y, z+0.5f);
		
		GL11.glRotatef(-Render.getYaw(), 0, 0, 1);
		GL11.glRotatef(-Render.getPitch(), 1, 0, 0);
		Texture.bind(T);
		GL11.glBegin(GL11.GL_QUADS);
		
		//GL11.glNormal3f(1, 0, 0);
		float F1=Fx+0.0001f;
		float F2=Fx+indx-0.0001f;
		float F3=Fy+0.0001f;
		float F4=Fy+indy-0.0001f;
		
		GL11.glTexCoord2f(F1,F4);
		GL11.glVertex3f(-size,-size,0);
		
		GL11.glTexCoord2f(F2,F4);
		GL11.glVertex3f(size,-size,0);
		
		GL11.glTexCoord2f(F2,F3);
		GL11.glVertex3f(size,size,0);
		
		GL11.glTexCoord2f(F1,F3);
		GL11.glVertex3f(-size,size,0);
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
}
