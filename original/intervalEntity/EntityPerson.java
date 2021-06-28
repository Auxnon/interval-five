package intervalEntity;

import org.lwjgl.opengl.GL11;
import interval.Actor;
import interval.Inventory;
import interval.Item;
import interval.ModelManager;
import interval.Texture;
import interval.Timeline;
import interval.VGO;

/**
 * 
 * @author Aninon
 * @usage base class for all bipedial humans that can communicate and do basic intelligent dextrous actions
 */
public class EntityPerson extends EntityPlanned {
	protected short actionDelay;
	boolean moveTypeStick;
	boolean walkin;
	boolean aniMod=true;
	short aniDelay=0;
	String baseModel;
	Actor act;

	Inventory inventory;
	public EntityPerson(){
		inventory=new Inventory();
		baseModel="player";
		act=ModelManager.getActor("norm");
	}
	@Override
	public VGO getModel(){
		return ModelManager.getModel(baseModel+"Idle");
	}

	protected short getActionDelay(){
		return actionDelay;
	}
	protected void decActionDelay(){
		actionDelay--;
	}
	protected void setActionDelay(int u){
		actionDelay=(short) u;
		renewAction();
	}
	public void renewAction(){
		moveTypeStick=false;
		if(getAniMode()>0){
			setAniMode(0);
		}
	}

	@Override
	public void time(Timeline t){
		if(getActionDelay()>0){
			decActionDelay();
		}else{
			this.renewAction();
		}
		super.time(t);
		
		if(getAniMode()==0)
			teek2(80,105);
		else if(getMoveType()==2)
			teek(15);
		else
			teek(5);
		
	}
	private void teek(int t){
		aniDelay++;
		if(aniDelay>t){
			aniDelay=0;
			aniMod=!aniMod;
		}
	}
	private void teek2(int t,int t2){
		aniDelay++;
		if(aniDelay>t){
			aniMod=false;
			if(aniDelay>t2){
				aniDelay=0;
			}
		}else
			aniMod=true;
	}
	
	String walk1="UWalk1";
	String walk2="UWalk2";
	
	public void derpy(){
		
	}

	@Override
	public void render(){
		GL11.glPushMatrix();
		if(getAniMode()==8){
			GL11.glColor4f(1f,0.4f,0.0f,1f);
		}else{
			GL11.glColor4f(1f,1f,1f,1f);
		}
		GL11.glTranslatef((x), (y), z);
		GL11.glPushMatrix();

		//VGO vox=this.getModel();
		String upper="UIdle"+(aniMod?"":"2");
		String lower="LIdle";

		//boolean wa=false;

		if(walkin){
			if(aniMod){
				lower="LWalk1";
			}else{
				lower="LWalk2";
			}
		}
		if(getAniMode()!=0){
			switch(getAniMode()){
			case 1: 
				//wa=true;
				if(aniMod){
					upper=walk1;
				}else{
					upper=walk2;
				}break;
			case 2: 
				if(aniMod){
					upper="UPunch1";
				}else{
					upper="UPunch2";//.renderBloat(r,1f,PlayerManager.fatness);
				}break;
			case 3: upper="UUse";break;
			case 4: upper="UShoot"; break;
			case 5: 
				if(aniMod){
					upper="UTalk1";
				}else{
					upper="UTalk2";//.renderBloat(r,1f,PlayerManager.fatness);
				}break;
			}
		}

		//if(!walkin){
		//	vox=ModelManager.getModel(upper.replaceFirst("U", ""));
		//}else

		accessModel(upper,lower);
		
		derpy();
		/*	vox=ModelManager.getModel(upper+"-"+lower);
		vox.orientPlain(r);
		vox.orient(base);
		parseModel();
		vox.render();*/
		GL11.glPopMatrix();
		/*if(getAniMode()==4 && actionDelay>0){
			double rr=Math.toRadians(r);
			float ffx=(float)Math.cos(rr)/4f;
			float ffy=(float)Math.sin(rr)/4f;
			GL11.glPushMatrix();
			trans(ffx-ffy*1.75f,ffy+ffx*1.75f, 0.5f);
			pistol.orient(r);
			pistol.render();
			GL11.glPopMatrix();
		}*/
		Texture.bind(0);
		GL11.glColor4f(0,0,0,0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		float sx=getHalfSize();
		GL11.glVertex3f(-sx,-sx, top+0.22f-z);
		GL11.glVertex3f(sx, -sx, top+0.22f-z);
		GL11.glVertex3f(sx,sx, top+0.22f-z);
		GL11.glVertex3f(-sx, sx,top+0.22f-z);
		GL11.glEnd();
		GL11.glPopMatrix();


	}
	protected void accessModel(String up,String low){
		up=baseModel+up;
		low=baseModel+low;
		VGO vox=ModelManager.getModel(up+"-"+low);
		vox.orientPlain(r);
		vox.orient(base);
		parseModel();
		vox.render();
	}
	public boolean holdingItem(){
		return inventory.Selection()!=Item.Empty;
	}
}
