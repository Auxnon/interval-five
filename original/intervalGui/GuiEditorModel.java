package intervalGui;

import interval.ModelManager;
import interval.Changer;
import interval.FileManager;
import interval.IFont;
import interval.Message;
import interval.Render;
import interval.Texture;
import interval.UserData;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.lwjgl.input.Mouse;

public class GuiEditorModel extends Gui{
	float T=0.3f;
	public void initer() {
		T=0.3f;
		
		/*String[] st=new String[]{
				"duh",
				"hug",
				"yuh",
				"hmm",
				"doof",
				"lolwut",
				"no really",
				"seriously?",
				"god this is dumb",
				"i should write a whole story here",
				"once upon a time",
				"there was a big stanky cock",
				"he was so ugly, everyone died",
				"i am such a tool"
		};*/

		gl=new GIlist("List",FileManager.getTextures(),-1,2,64,IFont.BLACK);

		gl2=new GIlist("List2",FileManager.getTextures(),-1,2,32,IFont.BLACK);
		gl2.hide();
		add(gl);
		add(gl2);
		gp=new GIpic(0,34,56,32,32);

		gp2=new GIpic(0,34,24,32,32);
		gp2.hide();
		add(gp2);
		lod=new GI("Load",34,63);
		add(lod);
		rem=new GI("Remake",43,63);
		add(rem);
		com=new GI("Combo",54,63);
		com2=new GI("Mix",32,36);
		com3=new GI("Cancel",32,32);
		com2.leftAlign();
		com3.leftAlign();
		com2.hide();
		com3.hide();
		add(com);
		add(com2);
		add(com3);
		
		add(new GI("Dir",63,63,0.66f,0.66f,IFont.BLACK));

		add(gp);
		add(new GI("<- Back",0,6).centerLeft());
		vox=new GIvox(4,"playerIdle",32,16);
		vox.inv.plat=true;
		add(vox);
		
		icon1=new GIicon("down",new int[]{1},25,40);
		icon2=new GIicon("up",new int[]{0},25,48);
		
		icon3=new GIicon("mid",new int[]{4,5},25,44);
		
		icon4=new GIicon("left",new int[]{3},21,44);
		icon5=new GIicon("right",new int[]{2},29,44);
		
		icon6=new GIicon("type",new int[]{8,9},20,49);
		icon7=new GIicon("smooth",new int[]{6,7},30,49);
		
		
		icon1.hide();
		icon2.hide();
		icon3.hide();
		icon4.hide();
		icon5.hide();
		icon6.hide();
		icon7.hide();
		off();
		
		add(icon1);
		add(icon2);
		add(icon3);
		add(icon4);
		add(icon5);
		add(icon6);
		add(icon7);
		menuPriority=true;
		
		add(new GItoggle("Background","BG",new int[]{10,11,12},0,36));
	//	add(new GIscale("derk:","sca",0,32,20,20));
	}
	GI com;
	GI com2;
	GI com3;
	GI rem;
	GI lod;
	
	GIicon icon1;
	GIicon icon2;
	GIicon icon3;
	GIicon icon4;
	GIicon icon5;
	
	GIicon icon6;
	GIicon icon7;

	GIvox vox;
	GIlist gl;
	GIlist gl2;
	GIpic gp;
	GIpic gp2;
	
	String protocol="";
	boolean smooth=true;

	public void setProto(String s){
		protocol=s;
		Message.addPass(s+" models",1);
	}
	
	public void setType(boolean n){
		if(n){
			setProto("blend");
			off();
		}else{
			setProto("front bind");
			off();
			icon3.setIcon(5);icon3.mode=1;
			icon3.colorNormal.setV(1,1,1);
		}
	}
	public void off(){
		icon1.colorNormal.setV(T,T,T);
		icon2.colorNormal.setV(T,T,T);
		icon3.colorNormal.setV(T,T,T);
		icon4.colorNormal.setV(T,T,T);
		icon5.colorNormal.setV(T,T,T);
	}
	public void sideOn(){
		icon6.setIcon(9);icon6.mode=1;
	}
	public void command(String s,GI g){
		if(s.startsWith("sca")){
			UserData.TEM=Integer.parseInt(s.substring(3));
		}else if(s.contains("BG10")){
			setRender(true);
		}else if(s.contains("BG11")){
			setRender(false);
			Render.backColor(0, 0, 0);
		}else if(s.contains("BG12")){
			setRender(false);
			Render.backColor(1,1, 1);
		}else if(s.contains("type")){
			setType(s.equals("type8"));
		}else if(s.contains("mid")){
			off();sideOn();
			icon3.colorNormal.setV(1,1,1);
			if(s.equals("mid5")){
				setProto("front bind");
			}else{
				setProto("rear bind");
			}
		}else if(s.contains("left")){
			off();sideOn();
			icon4.colorNormal.setV(1,1,1);
			setProto("left bind");
		}else if(s.contains("right")){
			off();sideOn();
			icon5.colorNormal.setV(1,1,1);
			setProto("right bind");
		}else if(s.contains("up")){
			off();sideOn();
			icon2.colorNormal.setV(1,1,1);
			setProto("top bind");
		}else if(s.contains("down")){
			off();sideOn();
			icon1.colorNormal.setV(1,1,1);
			setProto("bottom bind");
		}else if(s.contains("smooth")){
			smooth=s.equals("smooth6");
		}else if(s.equals("Dir")){
			try {
				String ss=FileManager.getTextureDir();
				int ii=ss.indexOf(".");
				ss=ss.substring(0, ii)+ ss.substring(ii+2);
				File ff=new File(ss);
				Message.m(this,"file "+ff.exists() +", "+ff.isDirectory());
				Desktop.getDesktop().open(ff);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(s.equals("Combo")){
			com.ambient();
			lod.ambient();
			rem.ambient();
			gp2.unhide();
			gl2.unhide();
			com2.unhide();
			com3.unhide();
			
			icon1.unhide();
			icon2.unhide();
			icon3.unhide();
			icon4.unhide();
			icon5.unhide();
			icon6.unhide();
			icon7.unhide();
		}else if(s.equals("Load")){
			if(gl.select!=null){
				String st=FileManager.cleanestFileName(gl.select.action);
				vox.change(st);
				UserData.setTempModel(st);
			}
		}else if(s.equals("Mix")){
			if(gl.select!=null && gl2.select!=null){
				int i=0;
				int j=0;
				int k=0;
				if(protocol.contains("front")){
					j=-1;
				}else if(protocol.contains("rear")){
					j=1;
				}else if(protocol.contains("left")){
					i=-1;
				}else if(protocol.contains("right")){
					i=1;
				}else if(protocol.contains("top")){
					k=1;
				}else if(protocol.contains("bottom")){
					k=1;
				}
				Message.addPass(smooth?"smoooth":"not smooth");
				Message.addPass(i+","+j+","+k);
				ModelManager.combine(gl.select.action, gl2.select.action,i,j,k,smooth);
				
				String st=FileManager.cleanestFileName(gl.select.action);
				ModelManager.reloadModel(st);
				vox.change(st);
				UserData.setTempModel(st);
				cancel();
			}
		}else if(s.equals("Cancel")){
			cancel();
		}else if(s.equals("Remake")){
			if(gl.select!=null){
				String st=FileManager.cleanestFileName(gl.select.action);
				Texture.redo(st);
				ModelManager.remakeModel(st);
				vox.change(st);
				UserData.setTempModel(st);
			}

		}else if(s.contains(".png")){
				Message.m(this,"here it is "+s +" "+g);
				BufferedImage b=FileManager.loadImage(s);
				if(g==gl)
					gp.set(Texture.get(s),b.getWidth(),b.getHeight());
				else
					gp2.set(Texture.get(s),b.getWidth(),b.getHeight());
			
		}else if(s=="<- Back"){
			Changer.menuUp();
		}
	}
	private void cancel(){
		gp2.hide();
		gl2.hide();
		com2.hide();
		com3.hide();
		com.unambient();
		lod.unambient();
		rem.unambient();
		
		icon1.hide();
		icon2.hide();
		icon3.hide();
		icon4.hide();
		icon5.hide();
		icon6.hide();
		icon7.hide();
	}
	float mox=0;
	float moy=0;
	public void run(){
		super.run();
		
		float D=Mouse.getDWheel();
		if(D!=0){
			Message.addPass("z");
			vox.addScale(Mouse.getDWheel()/100f);
		}
		if(!within || hide){
			if(click){
				mox=mx;
				moy=my;
			}
			if(downRight){
				vox.inv.r++;
				vox.inv.r2=0;
				hide=true;
			}else if(down){
				float mmx=mox-mx;
				float mmy=moy-my;
				vox.inv.r-=mmx/5;
				vox.inv.r2-=mmy/5;
				hide=true;
			}
			if(vox.inv.r>360){
				vox.inv.r-=360;
			}else if(vox.inv.r<-360){
				vox.inv.r+=360;
			}
		}
		if(!downRight && !down)
			hide=false;
	}

}
