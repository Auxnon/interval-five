package intervalEntity;

import interval.Item;
import interval.ModelManager;
import interval.Main;
import interval.SoundFX;
import interval.UserData;
import interval.VGO;
import intervalParticle.PMuzzleFlash;
import intervalParticle.PWord;

public class PlayerBase  extends EntityPerson {

	public VGO getModel(){
		return ModelManager.getModel(UserData.getTempModel());
	}

	@Override
	protected void keyFinder(){
		
	}
	VGO pistol;
	int toolDelay=0;
	private short blockpick=0;
	boolean aiming;

	public boolean is(int i){
		return getAniMode()<2 || getAniMode()==i;
	}
	
	//walk  
	//sprint
	//sneak

	//tool1
	//tool2
	//jump
	//wave
	//melee
	//use  

	@Override
	public void contactTop(Entity e){
		carryTop(e);
	}

	
	public PlayerBase(){
		pistol=ModelManager.getModel("pistol");
		renewDimensions();
	}
	public short getBlockPick() {
		return blockpick;
	}
	public void setBlockPick(short blockpick) {
		this.blockpick = blockpick;
	}

	@Override
	public void attack(){
		//super.attack(); :3
	}

	public void dialogue(String d){
		actionDelay=30;
		setAniMode(5);
		moveTypeStick=true;
		setMoveType(3);
		Main.world.addP(new PWord(x,y,z+height,16));
		Main.world.communicate(this, 3,"chat");
	}

	public void fallDamage(){
		setAniMode(8);
		actionDelay=30;
	}

	public void cantUse(Entity e){
		/*pull=true;
		//tempo=e;
		px=x;
		py=y;
		pr=r;*/
		pull(e);
	}

	public void jump(){
		if(grounded)
			SoundFX.jump.play(x-Main.player.x,y-Main.player.y,0);
	}

	public void use(){
		super.use();
		setAniMode(3);
		unGrab();
		actionDelay=10;
	}

	public void tool(){
		Main.world.addE(new EntityBullet(x,y,z,r));
		double rr=Math.toRadians(r);
		float ffx=(float)Math.cos(rr)/4f;
		float ffy=(float)Math.sin(rr)/4f;
		for(int u=0;u<10;u++)
			Main.world.addP(new PMuzzleFlash(x+ffx-ffy*1.75f,y+ffy+ffx*1.75f, z+0.5f,-ffy,ffx));
	}
	public boolean armTool(){
		setActionDelay(30);
		setAniMode(4);
		Item item=inventory.Selection();
		int u=item.getMove();
		if(u==1){
			moveTypeStick=true;
			setMoveType(2);
		}else if(u==2){
			moveTypeStick=true;
			setMoveType(3);
		}
		if(item.getAim()){
			aiming=true;
			return true;
		}
		return false;
	}

	public void melee(){
		actionDelay=10;
		setAniMode(2);
		attack();
	}
	/*public void walk(){

	}*/
	public void still(){

	}
}
