package intervalGui;

import interval.Changer;
import interval.Message;
import interval.Title;

public class GuiTitle extends Gui {

	Title t;
	public void initer() {
		t= new Title();
		GI G1=new GI("drag1",30,60);
		GI G2=new GI("drag2",30,60);
		GI G3=new GI("drag3",30,60);
		GI G4=new GI("drag4",30,60);

		G1.sx=4;
		G1.sy=4;

		G2.sx=4;
		G2.sy=4;

		G3.sx=4;
		G3.sy=4;

		G4.sx=4;
		G4.sy=4;

		GI start=new GI("Start",32,20).centerX();
		GI editor=new GI("Editor",32,6).centerRight();
		GI options=new GI("Options",32,14).centerX();
		GI exit=new GI("Exit",32,8).centerX();
		
		

		G1.visible=false;
		G2.visible=false;
		G3.visible=false;
		G4.visible=false;

		G1.canKey=false;
		G2.canKey=false;
		G3.canKey=false;
		G4.canKey=false;

		sel=3;
		add(G1);
		add(G2);
		add(G3);
		add(G4);
		add(start);
		add(editor);
		add(options);
		add(exit);
		add(new GI("Press N to toggle land slopes",32,4).leftAlign().half());
	}
	public boolean dragger=false;
	int S=1;
	public void command(String s,GI g){
		
		if(s=="Start"){
			Changer.menuDown(new GuiLevelSelect());
			//Changer.aninon();
		}else if(s=="drag1"){
			dragger=true;S=1;
		}else if(s=="drag2"){
			dragger=true;S=2;
		}else if(s=="drag3"){
			dragger=true;S=3;
		}else if(s=="drag4"){
			dragger=true;S=4;
		}else if(s=="Exit"){
			Changer.destroy();
		}else if(s=="Options"){
			Changer.menuDown(new GuiOptions());
		}else if(s=="Editor"){
			Changer.menuDown(new GuiEditor());
		}
	}
	@Override
	public void run(){
		mouser();
		guisRun();


		if(!down && dragger){
			dragger=false;
			
		}

		if(dragger){
			switch(S){
			case 1:t.p2.setPos(mx-2,my+2);break;
			case 2:t.p3.setPos(mx-2,my+2);break;
			case 3:t.p4.setPos(mx-2,my+2);break;
			case 4:t.p5.setPos(mx-2,my+2);break;
			}

		}else{
			GI G=get(0);
			G.x=(int) (t.p2.x+(G.sx/2));
			G.y=(int) (t.p2.y+(G.sy/2));

			G=get(1);
			G.x=(int) (t.p3.x+(G.sx/2));
			G.y=(int) (t.p3.y+(G.sy/2));

			G=get(2);
			G.x=(int) (t.p4.x+(G.sx/2));
			G.y=(int) (t.p4.y+(G.sy/2));

			G=get(3);
			G.x=(int) (t.p5.x+(G.sx/2));
			G.y=(int) (t.p5.y+(G.sy/2));
		}
		t.render();
		
		/*GL11.glColor4f(1,0,0,1);
		GL11.glBegin(GL11.GL_QUADS);
		float derp=0f;
		GL11.glVertex3f(0, 0, derp);
		GL11.glVertex3f(60, 0, derp);
		GL11.glVertex3f(64, 64, derp);
		GL11.glVertex3f(0, 64, derp);
		GL11.glEnd();*/
	}
}
