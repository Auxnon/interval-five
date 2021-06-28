package intervalGui;

import org.lwjgl.input.Keyboard;
import interval.Main;
import interval.Changer;
import interval.IFont;
import interval.Message;
import intervalEntity.Human;

public class GuiEditorHuman extends Gui{
	float T=0.3f;
	
	Human person;
	GIscale age;
	public void initer() {
		T=0.3f;

		person=new Human();
		person.defaultHuman();
		person.gender(true);
		person.skins();

		gender=new GItoggle("Gender","sex",new int[]{1,0},1f,1f);
		//gender=new GIlist("Gender",new String[]{"Male","Female"},0,2,64,IFont.BLACK);
		age = new GIscale("Age:","Age",2,60,IFont.BLACK,0,10,100);
		skin=new GIswatch("Skin","paletteSkin",34,56,16,16);
		hair=new GIswatch("Hair","paletteHair",34,24,16,16);

		add(gender);
		add(skin);
		add(hair);
		add(age);
		add(new GI("Jump",2,32));
		
		lod=new GI("Load",34,63);
		add(lod);
		rem=new GI("Remake",43,63);
		add(rem);
		
		/*com=new GI("Combo",54,63);
		com2=new GI("Mix",32,36);
		com3=new GI("Cancel",32,32);
		com2.leftAlign();
		com3.leftAlign();
		com2.hide();
		com3.hide();
		add(com);
		add(com2);
		add(com3);*/
		
		add(new GI("Dir",63,63,0.66f,0.66f,IFont.BLACK));

		
		add(new GI("<- Back",0,6).centerLeft());
		vox=new GIvox(4,"man",16f,16f);
		vox.inv.plat=true;
		vox.inv.mode=2;
		add(vox);
		
		icon1=new GIicon("down",new int[]{1},25,40);
		icon2=new GIicon("up",new int[]{0},25,48);
		
		icon3=new GIicon("mid",new int[]{4,5},25,44);
		
		icon4=new GIicon("left",new int[]{3},21,44);
		icon5=new GIicon("right",new int[]{2},29,44);
		
		icon6=new GIicon("type",new int[]{8,9},20,49);
		icon7=new GIicon("smooth",new int[]{6,7},30,49);
		
	
		add(icon1);
		add(icon2);
		add(icon3);
		add(icon4);
		add(icon5);
		add(icon6);
		add(icon7);
		menuPriority=true;
	}
	GI com;
	GI com2;
	GI com3;
	GI rem;
	GI lod;
	GItoggle gender;
	
	GIicon icon1;
	GIicon icon2;
	GIicon icon3;
	GIicon icon4;
	GIicon icon5;
	
	GIicon icon6;
	GIicon icon7;

	GIvox vox;
	GIswatch skin;
	GIswatch hair;
	
	String protocol="";
	boolean smooth=true;

	public void setProto(String s){
		protocol=s;
		Message.addPass(s+" models",1);
	}
	
	public void command(String s,GI g){
		if(s.startsWith("Age")){
			person.setAge(age.getIndex());
		}else if(s.contains("Jump")){
			person.command("jump");
		}else if(s.contains("Hair")){
			String st[]=s.substring(4).split(";");
			person.getGene().setHair(Integer.parseInt(st[0]), Integer.parseInt(st[1]));
			reset();
		}else if(s.contains("Skin")){
			//String st[]=
			person.getGene().setSkin(Integer.parseInt(s.substring(4).split(";")[0]));
			reset();
		}else if(s.equals("Gender")){
				person.gender(gender.dual);
				reset();
		}else if(s=="<- Back"){
			vox.clean();
			Changer.menuUp();
		}
		
	}
	float mox=0;
	float moy=0;
	public void run(){
		super.run();
		
		person.logic();
		person.time(Main.world.timeline);
		person.timeFactored(Main.world.timeline,1);
		person.render();
		vox.inv.model=person.getM();
		vox.inv.special=person.getS();
		vox.inv.raiseZ=person.groundHeight();
		Message.m(this,"z "+person.z);
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
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_0)){
			ag++;
			if(ag>100){
				ag=0;
			}
			person.setAge(ag);
			}
	}
	float ag;
	private void reset(){
		person.skins();
		person.command("chat");
	}

}
