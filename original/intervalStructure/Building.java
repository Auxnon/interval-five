package intervalStructure;

import interval.ModelManager;
import interval.Main;
import interval.VGO;
import intervalEntity.Indoor;
import intervalEntity.TestIndoor;


public class Building extends Structure{
	
	Indoor room;
	
	
	float rgb[];
	public Building(float X,float Y,float R){
		super();
		rot(R);//((int)(Math.random()*4))*90);
		System.out.println(getR());
		place((int)X,(int)Y);
		rgb=new float[3];
		rgb[0]=(float) Math.random();
		rgb[1]=(float) Math.random();
		rgb[2]=(float) Math.random();
		setDependant(false);
		room=new TestIndoor();
	}
	
	public void time(){
		super.time();
		if(isPresent()){
			if(!Main.world.contains(room)){
				room.place(x, y);
				Main.world.addE(room);
			}
		}else{
			if(Main.world.contains(room)){
				Main.world.removeE(room);
			}
		}
	}
	
	public VGO getModel(){
		return ModelManager.getModel("tower");
	}
	public void Render(){
		/*
		GL11.glColor3f(rgb[0], rgb[1], rgb[2]);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(-sx, -sy, 1);
		GL11.glVertex3f(sx, -sy, 1);
		GL11.glVertex3f(sx, sy, 1);
		GL11.glVertex3f(-sx, sy, 1);
		GL11.glEnd();*/
		VGO v=getModel();
		v.orient(getR());
		v.render();
		//Actor.bank.render(getR());
	}

}
