package interval;

import intervalEntity.Entity;
import intervalGui.GI;
import intervalGui.GIass;
import intervalGui.GIdialog;
import intervalGui.GIpass;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class Message {

	static ArrayList <GIpass> passQue;
	static ArrayList <GIpass> passQue2;
	static ArrayList <GIpass> passQue3;
	static Toolkit tk;
	static boolean point=false;
	static GI pointer;
	static GIdialog dialogPane;
	
	
	public static void init(){
		passQue = new ArrayList<GIpass>();
		passQue2 = new ArrayList<GIpass>();
		passQue3 = new ArrayList<GIpass>();
		pointer =new GI("derk",0,32,IFont.BLACK);
		pointer.sx/=2;
		pointer.sy/=2;
		pointer.setDepth(-0.0001f);
		tk=Toolkit.getDefaultToolkit();
		clippy=tk.getSystemClipboard();
		dialogPane=new GIdialog(0,IFont.BLACK);
	}
	static float load;
	public static void loader(float d){
		load=d;
	}
	private static void loaderRun(){
		float s=load*64;
		GL11.glColor4f(0, 0.5f, 0.75f, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(0, 1);
		GL11.glVertex2f(s, 1);
		GL11.glVertex2f(s, 0);
		GL11.glEnd();
	}
	public static void pointerMessage(String s){
		if(!point){
			point=true;
			if(!pointer.action.equals(s)){
				m(Message.class,"selected "+s);
				pointer.redo(s,IFont.BLACK);
				pointer.sx/=2;
				pointer.sy/=2;
			}
		}
	}

	
	public static void addPass(String in,int d){
		switch(d){
		case 1:passQue2.add(new GIpass(in,1));break; //left
		case 2:passQue3.add(new GIpass(in,2));break; //right
		default:passQue.add(new GIpass(in)); //middle
		}
	}
	public static void addPass(String in){
		//TODO default middle messages aren't adequate
		addPass(in,1);
		//passQue.add(new GIpass(in));
	}
	
	public static void addText(String in){

	}

	public static String insult(){
		String stuff="";

		switch(Main.rand.nextInt(55)){
		case 1:stuff="Nicely Done! You're Adopted.";break;
		case 2:stuff="Gerbil #436: Passed";break;
		case 3:stuff="Ha Ha Ya Pansey!";break;
		case 4:stuff="Bravo, Wanker!";break;
		case 5:stuff="The Most Successful Failure!";break;
		case 6:stuff="Took Ya Long Enough!";break;
		case 7:stuff="Hardly Disgraceful!";break;
		case 8:stuff="You Accidently Won!";break;
		case 9:stuff="ERROR: Player Won";break;
		case 10:stuff="Impressive For A Cucumber!";break;
		case 11:stuff="Total Claps Earned: 1";break;
		case 12:stuff="That Was Okay, I Guess.";break;
		case 13:stuff="Why Are You Still Playing?";break;
		case 14:stuff="Moderately Self-Fulfilled, Possibly!";break;
		case 15:stuff="Satisfactory As A Paper Cut!";break;
		case 16:stuff="You Make Smart People Sad!";break;
		case 17:stuff="One Day You Won't Be Dumb!";break;
		case 18:stuff="My Little Buttercup,Sweet Little Buttercup.";break;
		case 19:stuff="What Do You Want? A Freaking Medal?";break;
		case 20:stuff="It's Almost Like You Know What You were Doing!";break;
		case 21:stuff="Failure Level Decreased!";break;
		case 22:stuff="Your Head Is Filled With Pineapples!";break;
		case 23:stuff="Paramedics Will Arive To Resuscitate Your Brain!";break;
		case 24:stuff="What Do You Want? A Freaking Medal?";break;
		case 25:stuff="... oh you won.";break;
		
		//case 29:stuff="\"Yer A Bum Tickler!\", Said God.";break;
		case 30:stuff="Stop Being Bad At All The Things!";break;
		
		//case 31:stuff="Stop Mooning M- Oh, That's Your Face?";break;
		case 32:stuff="You Are Insignificant And Taste Bad!";break;

		//case 35:stuff="If I Were Your Pet, Id Poop In Your Shoes!";break;
		//case 36:stuff="I Find You Offensive!";break;
		//case 37:stuff="If I Felt Sick, Id Move Away From You!";break;
		//case 38:stuff="What's My Problem? Your Face!";break;
		//case 39:stuff="Failure Is Expected!";break;
		//case 40:stuff="Am Dissapoint Son";break;
		//case 41:stuff="Why Haven't You Beaten The Game Yet!";break;

		//case 43:stuff="I Aint Gonna Write You A Love Song!";break;
		
		
		//case 46:stuff="Why Do You Bother?";break;
		//case 47:stuff="Life Is Short And Meaningless!";break;
		//case 48:stuff="You Are A Spec In The Universe!";break;
		//case 49:stuff="Your Existance Is For Bad Jokes!";break;
		//case 50:stuff="I Say Hurtful Things Because You Hurt My Eyes To See!";break;
		//case 51:stuff="Winning Is Losing! Loser.";break;
		//case 52:stuff="Losing Is Winning! Loser.";break;
		//case 53:stuff="Meh.";break;
		//case 54:stuff="You Are Dull.";break;
		//default:stuff="Good Job, Asshole!";break;
		}
		return stuff;
	}
	
	public static String existential(){
		switch(Main.rand.nextInt(38)){
			case 1: return "Your efforts are futile.";
			case 2: return "Many deem this sad and pathetic.";
			case 3: return "Persistence where none is needed.";
			case 4: return "Most Dissappointing.";
			case 5: return "Pitiful.";
			case 6: return "Your judgement is unsound.";
			case 7: return "Useless.";
			case 8: return "You are alone in a cold dark universe.";
			case 9: return "The expanse of all time and space is lifeless, your kind is an anomaly.";
			case 10: return "Win or lose, it's all the same.";
			case 11: return "You make no difference in this world.";
			case 12: return "Everyone dies alone.";
			case 13: return "Foundation of life is built on lies.";
			case 14: return "Security is a farse.";
			case 15: return "Expect little, recieve nothing.";
			case 16: return "Work hard, die poor.";
			case 17: return "Find a purpose, it's still meaningless.";
			case 18: return "Expecting nothing, is the only truth";
			case 19: return "Your life is summarized in a sentence.";
			case 20: return "In the grand scheme of existance, your life is unknown.";
			case 21: return "You will die and be forgotten.";
			case 22: return "Excel at everything, achieve nothing";
			case 23: return "Purpose? There is none.";
			case 24: return "You toil fruitlessly, sad.";
			case 25: return "Why bother?";
			case 26: return "Kindness does not exist.";
			case 27: return "You are unknown, and unloved.";
			case 28: return "In death, you become nothingness.";
			case 29: return "Give up.";
			case 30: return "Face your reality, there is nothing left for you.";
			case 31: return "Let the darkness take you.";
			case 32: return "You sicken me.";
			case 33: return "Claim your destiny, death and void.";
			case 34: return "You are nothing.";
			case 35: return "Life is fiction.";
			case 36: return "Collapsing anomalyous stardust. Life is that simple.";
			case 37: return "Fate claims you, there is no exception.";
			default: return "You are a meaningless Spec.";
		}
	}
	
	//You are truly loved, by at least someone somewhere, and you always will be. Always.  LOVE
	//There is more to this life than fleeting moments and sciences. There is spirit.   HOPE
	//Life is never senseless, purpose is just waiting to be had. Claim it. LIVE

	static GIass assPass;
	public static void run(){

		if(assHat){
			assPass.render();
			assDelay++;
			if(assDelay>assPause){
				assIndex++;
				assDelay=0;
				if(assIndex>=assBuffer.length){
					assHat=false;
					return;
				}
				assPass.redo(assBuffer[assIndex]);
			}
		}else{
			if(point){
				point=false;
				pointer.x=1+Render.getGui().mx;
				m(Message.class," screen "+pointer.x+" "+MainFrame.screenRight());
				if(pointer.x+pointer.sx>MainFrame.screenRight())
					pointer.x=MainFrame.screenRight()-pointer.sx;
				pointer.y=Render.getGui().my;
				pointer.render();
			}
			if(dialog){
				runDialog();
			}
			if(load>0)
				loaderRun();
			
			cycleQue(passQue);
			cycleQue(passQue2);
			cycleQue(passQue3);
		}
	}
	public static void reset(){
		passQue.clear();
	}

	public static void cycleQue(ArrayList<GIpass> que){
		int m=que.size();

		if(m>0){

			float nf=0.005f*m;
			if(m>10){
				nf*=10;
			}
			float spee=0.01f+nf;
			float spee2=0.02f+nf;
			int d=400-m;
			float sz=(d<1?1:d)/100f;
			for(int i=0;i<m;i++){
				GIpass gu=que.get(i);
				gu.render();
				gu.sy=sz;
				if(gu.y<(60-sz*i)){
					gu.y+=0.5f+spee;
				}else{

					if(gu.counter<5f){
						gu.counter+=spee;
					}else if(gu.y>56){
						gu.counter+=(spee2);
						if(gu.counter>=6){
							que.remove(i);
							m--;
							i--;
						}
					}
				}
			}
		}
	}
	public static void m(Class c,String s){
		System.out.println(c.getName()+":::"+s);
	}

	public static void m(Object o,String s){
		System.out.println(o.getClass().getName()+":::"+s);
	}
	public static void er(Exception e){
		er(e,true);
	}
	public static void er(Exception e,boolean f){
		//System.err.print(e.getMessage());
		System.out.println(e.getMessage());
		if(f)e.printStackTrace();
	}
	public static void er(String s){
		System.err.print(s);
		System.out.println();
		addPass(s);
	}
	public static void assMessage(String s,int p){
		assPause=p;
		s=s.toUpperCase();
		assBuffer=new String[s.length()];
		for(int u=0;u<s.length();u++)
			assBuffer[u]=s.substring(u,u+1);

		//Message.addPass("ass "+assBuffer.length);
		assHat=true;
		assIndex=0;
		if(assPass==null)
			assPass=new GIass(assBuffer[0]);
		else
			assPass.redo(assBuffer[0]);
	}
	static boolean assHat=false;
	static int assDelay=0;
	static String[] assBuffer;
	static int assIndex;
	static int assPause=200;
	public static void pop(String s){
		new MsgWindow(s);
	}
	public static void popText(String title,String message){
		new MsgWindow(title,message);
	}
	public static void popText(String title,boolean edit){
		new MsgWindow(title,edit);
	}
	private static String clipString(Clipboard clip){
		if ( clip.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
			try {
				Object o = clip.getData(DataFlavor.stringFlavor);
				if(o instanceof String){
					return (String) o;
				}
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	private static Image clipImage(Clipboard clip){
		try {
			if ( clip.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
				Object o = clip.getData(DataFlavor.imageFlavor);
				if(o instanceof Image){
					return (Image) o;
				}
			}
		} catch(IllegalStateException e){
			Message.er(e);
		} catch (UnsupportedFlavorException e) {
			Message.er(e);
		} catch (IOException e) {
			Message.er(e);
		}
		return null;
	}
	public static String copiedString(){
		//Clipboard clip=tk.getSystemClipboard();
		if(clippy!=null){
			return clipString(clippy);
		}
		return "";
	}
	static Clipboard clippy;
	public static Image copiedImage(){
		if(clippy!=null){
			return clipImage(clippy);
		}
		return null;
	}
	public static Object copiedObject(){
		if(clippy!=null){
			Image ii=clipImage(clippy);
			if(ii==null){
				return clipString(clippy);
			}else{
				return ii;
			}
		}
		return "";
	}
	static int dialogIndex=0;
	//int dialogIndex=0;
	static int dialogCap;
	public static void dialog(Entity e,String s,int type){
		dialog=true;
		dialogCap=s.length()*30;
		dialogPane.message(s, type);
	}
	public static void runDialog(){
		dialogIndex++;
		int ind=dialogIndex/30;
		dialogPane.render();
	}
	static boolean dialog=false;
}
