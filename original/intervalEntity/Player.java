package intervalEntity;

import interval.Main;
import interval.ModelManager;
import interval.Render;
import interval.Texture;
import interval.UserData;
import interval.Vec;
import intervalParticle.PTeleCore;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Player extends PlayerBase {
	public boolean inside=false;
	private long destination=1;

	public ArrayList<Damage> damage;
	int D;
	boolean newDamage=false;
	public void addDamage(Damage t){
		damage.add(t);
		newDamage=true;
	}
	public void renewDamage(){
		D=0;
		for(Damage d:damage){
			D+=d.getAmount();
		}
		newDamage=false;
	}
	public void cleanDamage(Damage t){
		damage.remove(t);
		newDamage=true;
	}
	public Player(){
		super();
		damage=new ArrayList<Damage>();
		weight=1;
		ModelManager.remakeModel2("shape");
		//TODO see below
		//derp=ModelManager.getLayout(ModelManager.getModel("shape"), 7);
	}

	public void timeTravel(){
		timeTravel(Main.player.getDestination());
	}
	public void timeTravel(long i){
		if(Main.level.getStats().canPastPlayer() && schedule.task.size()>1){
			schedule.addPureTask("01check"+dx+","+dy,0);
			endExistance=Main.world.timeline.getPureTick();
			Main.past.createVersion(startExistance,endExistance,schedule.clone());
		}
		Main.world.setTime(i);
		advancedSchedule();
		startExistance=i;
		endExistance=i;
		Main.world.addP(new PTeleCore(true,x,y,z));
		annual();
	}
	public void forceTravel(int i){
		Main.world.setTime(i);
		advancedSchedule();
		Main.world.addP(new PTeleCore(true,x,y,z));
	}

	public void directions(double fx, double fy){
		float sp=1;
		float ss=1;
		switch(getMoveType()){
		case 1: ss=UserData.getSSideSpeed();sp=UserData.getSSpeed();break;
		case 2: ss=UserData.getSneakSideSpeed();sp=UserData.getSneakSpeed();break;
		case 3:return;
		default: ss=UserData.getSideSpeed();sp=UserData.getSpeed();break;
		}

		walkin=!(fy==0 && fx==0) && Render.getGui().isGame();
		if(getAniMode()<2 ){
			if(walkin){
				setAniMode(1);
				actionDelay=2;
			}else{
				setAniMode(0);
			}
		}
		float dr=9000;
		if(fy>.5){
			if(fx<-.5){
				combiner("ueft");
				dr=45;
				dx-=ss;
				dy+=ss;
			}else if(fx>.5){
				combiner("uight");
				dr=315;
				dx+=ss;
				dy+=ss;
			}else{
				combiner("up");
				dr=0;
				dy+=sp;
			}
		}else if(fy<-.5){
			if(fx<-.5){
				combiner("doweft");
				dr=135;
				dx-=ss;
				dy-=ss;
			}else if(fx>.5){
				combiner("dowight");
				dr=225;
				dx+=ss;
				dy-=ss;
			}else{
				combiner("down");
				dr=180;
				dy-=sp;
			}
		}else{
			if(fx<-.5){
				combiner("left");
				dr=90;
				dx-=sp;
			}else if(fx>.5){
				combiner("right");
				dr=270;
				dx+=sp;
			}
		}
		if(!aiming && dr!=9000){
			r=dr;
		}
		if(!Render.getGui().isGame()){
			dx=x;
			dy=y;
		}
	}

	public void advancedSchedule(){
		schedule=new ScheduleAdvanced();
		schedule.activate();
		schedule.addPureTask("01init",0);
		schedule.annual(getVecP());
		uncontain();
		setPIN(Main.world.allocateID()); //make me a new PIN since enemies will be seeking my old self!

	}
	public void renewSchedule(){
		place(Main.level.getStartX(),Main.level.getStartY(),Main.level.getStartZ());
		advancedSchedule();
	}

	String combo="";
	private int comboed=0;
	public void startCombo(){
		setCommandCount(0);
		combo="";
	}
	public void endCombo(){
		if(getCommandCount()>0){
			schedule.addPureTask(getMoveType()+""+getCommandCount()+combo,0);
		}
	}
	public void combiner(String in){
		combo+=in;
		setCommandCount(getCommandCount() + 1);
	}

	public void logic(){
		double fy=0;
		double fx=0;
		startCombo();
		if(Main.control.jump && getAniMode()!=3){
			jump();
		}

		if( Main.control.use){
			if(!useLock &&is(3)){
				combiner("use");
				use();
				useLock=true;
			}//else if(carry!=null && carryMode==1)
			//use();
		}else{
			useLock=false;
		}

		if(Main.control.melee&&is(2)){
			combiner("punch");
			melee();
		}
		if(Main.control.getKey().equals("X")){
			dialogue("hi");
		}
		if(Main.control.tool &&is(4) && holdingItem()){
			if(armTool()){
				r=(float) (180.0*Math.atan2((double)(x-Main.mouseSelector.x), (double)(Main.mouseSelector.y-y))/Math.PI);
				combiner("aim:"+r+"<aim>");
				if(Mouse.isButtonDown(0)){
					toolDelay--;
					if(toolDelay<=0){
						toolDelay=20;
						tool();
						combiner("tool"+inventory.Selection().getName());
					}
				}
			}else if(toolDelay<=0){
				toolDelay=20;
				tool();
				combiner("tool"+inventory.Selection().getName());
			}
		}else{
			toolDelay=0;
		}

		if(!moveTypeStick){
			if(Main.control.sprint){
				setMoveType(1);
			}else{ 
				setMoveType(0);
			}
		}
		double yaw=Render.getRealYawRad();
		if(Main.control.right){
			fy-=Math.sin(yaw);
			fx+=Math.cos(yaw);
		}

		if(Main.control.left){
			fy+=Math.sin(yaw);
			fx-=Math.cos(yaw);
		}

		if(Main.control.up){
			fy+=Math.cos(yaw);
			fx+=Math.sin(yaw);
		}
		if(Main.control.down){
			fy-=Math.cos(yaw);
			fx-=Math.sin(yaw);
		}
		directions(fx,fy);


		endCombo();
		physics(1);
		//	x=50;
		//	y=50;
		//	z=50;
	}

	@Override
	public void render(){
		super.render();
		aiming=false;
	}
	@Override
	public void contain(Container c){
		vPIN=c.PIN;
		c.ents.add(this);
		inside=!(c instanceof Vehicle);
		Main.world.wipeP();
		super.contain(c);
	}
	public void uncontain(){
		if(this.getContained()!=null){
			vPIN=0;
			place(getContained().x,getContained().y);
			this.getContained().ents.remove(this);
			contained=null;
			inside=false;
			Main.world.wipeP();
		}
	}

	public long getDestination() {
		return destination;
	}
	public void setDestination(long destination) {
		this.destination = destination;
	}
	public int getCommandCount() {
		return comboed;
	}
	public void setCommandCount(int comboed) {
		this.comboed = comboed;
	}

	public void jump(){
		super.jump();
		//Message.m(this,"doh!"+vz+","+grounded);
		if(grounded){
			if(Render.getGui().isGame()){
				combiner("jump");
				vz=UserData.getJumpSpeed();
			}else{
				vz=0.1f;
			}
		}
	}

	
	//TODO floor generation testing, located within the player for a convenient render test because i'm insane.
	/*
	ArrayList<Vec> derp;
	public void derpy(){
		Texture.bind(0);
		GL11.glScalef(10, 10, 10);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glBegin(GL11.GL_QUADS);
		float GU=0.05f;
		float H=1f/(float)derp.size();
		float HU=0;
		Vec vo=derp.get(0);
		for(int u=1;u<=derp.size();u++){
			Vec vv;
			if(u>=derp.size())
				vv=derp.get(0);
			else
				vv=derp.get(u);
			HU+=H;
			GL11.glColor3f(0, 0,HU);
			GL11.glVertex3f(vo.x, vo.y, 0.4f+GU);
			GL11.glVertex3f(vv.x, vv.y, 0.4f+GU);
			GL11.glVertex3f(vv.x, vv.y, 0.2f+GU);
			GL11.glVertex3f(vo.x, vo.y, 0.2f+GU);
			vo=vv;
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_CULL_FACE);
	}*/

	/*@Override
	public void contactSide(Entity e){
		if(e instanceof EntityPlanned){

			if(sideCollision){
				side(e);
			}
			//schedule.offsetTarget(target, D, destination, comboed, damage)
			//((EntityPlanned)e).schedule
		}
	}*/

}
