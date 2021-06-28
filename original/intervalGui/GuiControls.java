package intervalGui;

import interval.Changer;
import interval.FileManager;
import interval.IFont;
import interval.Main;
import interval.Message;
import interval.Tex;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiControls extends Gui {
	Tex derp;
	float an=0;
	boolean wait=false;
	GI targ;
	String targ2;
	
	
	GI kuse1;
	GI kuse2;
	GI kjump1;
	GI kjump2;
	GI ksprint1;
	GI ksprint2;
	
	GI kleft1;
	GI kleft2;
	GI kright1;
	GI kright2;
	GI kup1;
	GI kup2;
	GI kdown1;
	GI kdown2;
	
	GI kitem1;
	GI kitem2;
	
	GI kmelee1;
	GI kmelee2;
	
	

	public void initer(){
		setRender(false);
		add(new GI("Key 1",14,64,IFont.BLACK).ambient());
		add(new GI("Key 2",26,64,IFont.BLACK).ambient());

		add(new GI(" Use  ",0,54,IFont.BLACK).ambient());
		add(new GI(" Jump ",0,48,IFont.BLACK).ambient());
		add(new GI("Sprint",0,42,IFont.BLACK).ambient());
		add(new GI("  <   ",0,36,IFont.BLACK).ambient());
		add(new GI("  >   ",0,30,IFont.BLACK).ambient());
		add(new GI("  ^   ",0,24,IFont.BLACK).ambient());
		add(new GI("  v   ",0,18,IFont.BLACK).ambient());
		add(new GI(" Item ",0,12,IFont.BLACK).ambient());
		add(new GI("Melee ",0,6,IFont.BLACK).ambient());
		
		add(new GI("'Shout' X",48,12,IFont.BLACK).ambient());
		//add(new GuiItem("Fatness",120,50));


		kuse1=new GI(cleaner(Main.control.keyUse1),14,54);kuse1.action="kuse1";
		kuse2=new GI(cleaner(Main.control.keyUse2),26,54);kuse2.action="kuse2";


		kjump1=new GI(cleaner(Main.control.keyJump1),14,48);kjump1.action="kjump1";
		kjump2=new GI(cleaner(Main.control.keyJump2),26,48);kjump2.action="kjump2";

		 ksprint1=new GI(cleaner(Main.control.keySprint1),14,42);ksprint1.action="ksprint1";
		 ksprint2=new GI(cleaner(Main.control.keySprint2),26,42);ksprint2.action="ksprint2";

		 kleft1=new GI(cleaner(Main.control.keyLeft1),14,36);kleft1.action="kleft1";
		 kleft2=new GI(cleaner(Main.control.keyLeft2),26,36);kleft2.action="kleft2";
		 kright1=new GI(cleaner(Main.control.keyRight1),14,30);kright1.action="kright1";
		 kright2=new GI(cleaner(Main.control.keyRight2),26,30);kright2.action="kright2";

		 kup1=new GI(cleaner(Main.control.keyUp1),14,24);kup1.action="kup1";
		 kup2=new GI(cleaner(Main.control.keyUp2),26,24);kup2.action="kup2";

		 kdown1=new GI(cleaner(Main.control.keyDown1),14,18);kdown1.action="kdown1";
		 kdown2=new GI(cleaner(Main.control.keyDown2),26,18);kdown2.action="kdown2";

		 kitem1=new GI(cleaner(Main.control.keyTool1),14,12);kitem1.action="kitem1";
		 kitem2=new GI(cleaner(Main.control.keyTool2),26,12);kitem2.action="kitem2";

		 
		 kmelee1=new GI(cleaner(Main.control.keyMelee1),14,6);kmelee1.action="kmelee1";
		 kmelee2=new GI(cleaner(Main.control.keyMelee2),26,6);kmelee2.action="kmelee2";
		 
		add(kuse1);add(kuse2);add(kjump1);add(kjump2);add(ksprint1);add(ksprint2);
		add(kleft1);add(kleft2);add(kright1);add(kright2);add(kup1);add(kup2);
		add(kdown1);add(kdown2);add(kitem1);add(kitem2);add(kmelee1);add(kmelee2);
		
		add(new GI("Defaults",48,60));
		
		add(new GI("<- Back",48,6));

		derp= new Tex(FileManager.dir()+"playo.png");
		//cb=new GuiCheckBox(PlayerManager.VoxelRefine,600,100);
		//add(cb);
	}
	public void keyed(String s){
		wait=true;
		targ2=s;
		//Main.control.hold=true;
		targ.redo("     ", IFont.PILLAR);
	}
	public void command(String s,GI g){
		//if(s!="")
			Message.addPass(s);
		if(!wait){
			/*if(s=="kuse1"){
				targ=kuse1;
				keyed(s);
			}else if(s=="kuse2"){
				targ=kuse2;
				keyed(s);
			}else if(s=="kjump1"){
				targ=kjump1;
				keyed(s);
			}else if(s=="kjump2"){
				targ=kjump2;
				keyed(s);
			}else if(s=="ksprint1"){
				targ=ksprint1;
				keyed(s);
			}else if(s=="ksprint2"){
				targ=ksprint2;
				keyed(s);
			}else if(s=="kleft1"){
				targ=kleft1;
				keyed(s);
			}else if(s=="kleft2"){
				targ=kleft2;
				keyed(s);
			}else if(s=="kright1"){
				targ=kright1;
				keyed(s);
			}else if(s=="kright2"){
				targ=kright2;
				keyed(s);
			}else if(s=="kup1"){
				targ=kup1;
				keyed(s);
			}else if(s=="kup2"){
				targ=kup2;
				keyed(s);
			}else if(s=="kdown1"){
				targ=kdown1;
				keyed(s);
			}else if(s=="kdown2"){
				targ=kdown2;
				keyed(s);
			}else if(s=="kitem1"){
				targ=kitem1;
				keyed(s);
			}else if(s=="kitem2"){
				targ=kitem2;
				keyed(s);
			}else if(s=="kmelee1"){
				targ=kmelee1;
				keyed(s);
			}else if(s=="kmelee2"){
				targ=kmelee2;
				keyed(s);
			}else */
			
			if(s=="<- Back"){
				Changer.menuUp();
			}else if(s=="Defaults"){
				Main.control.defaulter();
				kuse1.redo2(cleaner(Main.control.keyUse1), IFont.PILLAR);
				kuse2.redo2(cleaner(Main.control.keyUse2), IFont.PILLAR);
				
				kjump1.redo2(cleaner(Main.control.keyJump1), IFont.PILLAR);
				kjump2.redo2(cleaner(Main.control.keyJump2), IFont.PILLAR);
				
				ksprint1.redo2(cleaner(Main.control.keySprint1), IFont.PILLAR);
				ksprint2.redo2(cleaner(Main.control.keySprint2), IFont.PILLAR);
				
				kitem1.redo2(cleaner(Main.control.keyTool1), IFont.PILLAR);
				kitem2.redo2(cleaner(Main.control.keyTool2), IFont.PILLAR);
				
				kmelee1.redo2(cleaner(Main.control.keyMelee1), IFont.PILLAR);
				kmelee2.redo2(cleaner(Main.control.keyMelee2), IFont.PILLAR);
				
				kup1.redo2(cleaner(Main.control.keyUp1), IFont.PILLAR);
				kup2.redo2(cleaner(Main.control.keyUp2), IFont.PILLAR);
				
				kdown1.redo2(cleaner(Main.control.keyDown1), IFont.PILLAR);
				kdown2.redo2(cleaner(Main.control.keyDown2), IFont.PILLAR);
				
				kleft1.redo2(cleaner(Main.control.keyLeft1), IFont.PILLAR);
				kleft2.redo2(cleaner(Main.control.keyLeft2), IFont.PILLAR);
				
				kright1.redo2(cleaner(Main.control.keyRight1), IFont.PILLAR);
				kright2.redo2(cleaner(Main.control.keyRight2), IFont.PILLAR);
			}else{
				targ=g;
				keyed(s);
			}
		}
	}

	public String cleaner(int i){
		String str=Keyboard.getKeyName(i);
		return cleaner(str);
	}
	public String cleaner(String str){
		int yy=str.length();
		while(yy<5){
			yy++;
			str+=" ";
		}
		return str;
	}
	public void run(){
		if(!wait){
			mouser();
		}
		guisRun();
		
		an+=4f;
		if(an>360){
			an=0;
		}
		float w=4f;
		derp.bind();
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPushMatrix();
		GL11.glTranslatef(56, 32, 0);
		GL11.glRotatef(an, 0, 0, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(-w, -w);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(-w, w);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(w, w);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(w, -w);
		GL11.glEnd();
		GL11.glPopMatrix();

		
		if(wait){
			if(Main.control.getKey()!=""){
				targ.redo2(cleaner(Main.control.getKey()), IFont.PILLAR);
				wait=false;
				Main.control.set(targ2, Main.control.keyBind);
				Main.control.hold=false;
			}
		}


	}
}
