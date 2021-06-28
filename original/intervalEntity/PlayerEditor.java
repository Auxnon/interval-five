package intervalEntity;

import interval.Main;
import interval.Render;
import interval.Timeline;
import interval.UserData;

import org.lwjgl.opengl.GL11;

/** @Description Dummy Player Class to use for the world editor
 * 
 */
public class PlayerEditor extends Player {

	int ticker=0;
	float sx,ex;
	public PlayerEditor(){
		super();
		ticker=60;
		setCommandCount(5);
		setAniMode(1);
		sx=-0.5f;
		ex=0.5f;
	}
	
	public void annual(){

	}
	public void logic(){

		sx=-0.5f;
		ticker--;
		ex=0.5f;
		if(Main.control.sprint){
			dx=1;
		}else{
			dx=0.1f;
		}
		if(Main.control.left){
			x-=dx*Math.cos(Render.getYawRad());
			y+=dx*Math.sin(Render.getYawRad());
		}
		if(Main.control.right){
			x+=dx*Math.cos(Render.getYawRad());
			y-=dx*Math.sin(Render.getYawRad());
		}
		if(Main.control.up){
			x+=dx*Math.sin(Render.getYawRad());
			y+=dx*Math.cos(Render.getYawRad());
		}
		if(Main.control.down){
			x-=dx*Math.sin(Render.getYawRad());
			y-=dx*Math.cos(Render.getYawRad());
		}
		float sz=Main.level.getStats().getSize();

		if(ticker<=0 &&(Render.getGui().down) && !Render.getGui().within){
			int xx=(int)Main.mouseSelector.dx;
			int yy=(int)Main.mouseSelector.dy;
			if(xx>0 && xx<sz && yy>0 && yy<sz){
				ticker=getCommandCount();

				if(getMoveType()==0){
					if(!Main.control.sprint){
						Main.world.land.Hill(xx, yy,Main.player.getBlockPick(),getAniMode(),UserData.getSelectionSize());
					}else{
						Main.world.land.Hole(xx, yy, Main.player.getBlockPick(),getAniMode(),UserData.getSelectionSize());
					}
				}else if(getMoveType()==1){
					Main.world.land.Smooth(xx, yy,UserData.getSelectionSize());
				}else if(getMoveType()==2){
					for(int iii=0;iii<getAniMode();iii++){
					Main.world.land.Decay(xx, yy,UserData.getSelectionSize());
					}
				}else if(getMoveType()==3){
					Main.world.land.Pit(xx, yy,UserData.getSelectionSize());
				}else if(getMoveType()==4){
					if(!Main.control.sprint){
					Main.world.land.Flood(xx,yy,getAniMode(),UserData.getSelectionSize());
					}else{
						Main.world.land.Flood(xx,yy,-getAniMode(),UserData.getSelectionSize());
					}
				}
			}
		}
		float dz=0;
		if(x>0 && x<sz &&y>0 && y<sz ){
			dz=Main.world.land.getHigh((int)x,(int) y);
			if(dz<0){
				dz=0;
			}
		}
		z+=(dz-z)/5f;
		//z=Main.zoom;
	}
	@Override
	public void time(Timeline t){

	}
	public void setTime(int i){

	}



	public void render(){


		GL11.glPushMatrix();
		GL11.glColor4f(1f,1f,1f,1f);
		GL11.glTranslatef((x), (y), z+0.2f);
		//Actor.sloth.render(r);

		parseModel();
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(sx,sx, 1);
		GL11.glVertex3f(sx, ex,1);
		GL11.glVertex3f(sx,ex, 0);
		GL11.glVertex3f(sx, sx,0);

		GL11.glVertex3f(ex,sx, 1);
		GL11.glVertex3f(ex, ex,1);
		GL11.glVertex3f(ex,ex, 0);
		GL11.glVertex3f(ex, sx,0);


		GL11.glVertex3f(sx,sx, 1);
		GL11.glVertex3f(ex, sx,1);
		GL11.glVertex3f(ex,sx, 0);
		GL11.glVertex3f(sx, sx,0);

		GL11.glVertex3f(sx,ex, 1);
		GL11.glVertex3f(ex, ex,1);
		GL11.glVertex3f(ex,ex, 0);
		GL11.glVertex3f(sx, ex,0);

		GL11.glVertex3f(sx,sx, 1);
		GL11.glVertex3f(ex, sx,1);
		GL11.glVertex3f(ex,ex, 1);
		GL11.glVertex3f(sx, ex,1);
		GL11.glEnd();
		/*

		Texture.bind(0);
		GL11.glColor4f(0,0,0,0.5f);
		GL11.glTranslatef(0.5f,0.5f, 0);
		GL11.glBegin(GL11.GL_QUADS);


		GL11.glVertex3f(sx,sx, top+0.22f-z);
		GL11.glVertex3f(ex, sx, top+0.22f-z);
		GL11.glVertex3f(ex,ex, top+0.22f-z);
		GL11.glVertex3f(sx, ex,top+0.22f-z);
		GL11.glEnd();
		 */
		GL11.glPopMatrix();

	}
}
