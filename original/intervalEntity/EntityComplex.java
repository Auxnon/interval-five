package intervalEntity;

import interval.Main;
import interval.Message;
import interval.Timeline;
import interval.VecP;
import intervalParticle.PBlood;

public class EntityComplex extends Entity{

	boolean useLock=false;
	float health=10;
	private short  aniMode=0; //animation helper
	private short moveType=0; //movement helper
	float destx;
	float desty;
	float meleeDamage=0.1f;
	int vPIN;

	protected Entity carry=null;
	short carryMode;
	
	public boolean active(){
		return health>0 && vPIN==0;
	}
	@Override
	public void time(Timeline t){
		//diso();
	}
	
	/*public void diso(){
		user=false;
	}*/

	public VecP getVecP(){
		return new VecP(x,y,z,vx,vy,vz,r,getAniMode(),getMoveType(),vPIN,health,dx,dy,getIndex());
	}
	public int getIndex(){
		return 0;
	}
	public void setVecP(VecP v){
		x=v.x;
		y=v.y;
		z=v.z;
		vx=v.vx;
		vy=v.vy;
		vz=v.vz;
		r=v.r;
		setAniMode(v.mode);
		vPIN=v.vPIN;
		health=v.health;
		dx=v.tx;
		dy=v.ty;
		//TODO should i change back to dest? waht is this vec param needed for
		//dx=destx;
		//dy=desty;		
	}

	@Override
	public void contain(Container c){
		vPIN=c.PIN;
		c.ents.add(this);
		super.contain(c);
	}
	
	@Override
	public void uncontain(){
		if(this.getContained()!=null){
		vPIN=0;
		place(getContained().x,getContained().y);
		this.getContained().ents.remove(this);
		contained=null;
		}
	}
	
	public void attack(){
		Main.world.attackE(this, 1, 5, 20);
	}

	public void attacked(Entity e,float num){
		if(getContained()==null ||e.getContained() ==getContained()){
				//ouch!
			while(num-->0)
				Main.world.addP(new PBlood(x,y,z));
		}
	}
	public void use(){
		if(getContained()!=null &&getContained() instanceof Vehicle){
			useLock=true;
			uncontain();
		}else{
			int R=(int) (this.r/90);
			if(R>3)
				R=0;
			Main.world.useE(this,R, 0.3f);
		}
	}
	
	public void renewP(VecP v){
		uncontain();
		setVecP(v);
		if(vPIN!=0){
			Entity ee=Main.world.findE(vPIN);
			if(ee!=null && ee instanceof Container){
				Container cc= (Container) ee;
				contain(cc);
			}
		}
		refresh();
	}
	public short getMoveType() {
		return moveType;
	}
	public void setMoveType(int i) {
		this.moveType = (short) i;
	}
	public void setMoveType(short i) {
		this.moveType = i;
	}
	
	public short getAniMode() {
		return aniMode;
	}
	public void setAniMode(int mode) {
		this.aniMode = (short) mode;
	}
	
	public void jump(){
		
	}
	float pr=0;
	public void pull(Entity e){
		if(carry==null){
			carry=e;
			carryMode=1;
			pr=r;
		}else if(carry==e){
			r=pr;
			e.dx-=px-x;
			e.dy-=py-y;
		}
	}
	public void carryTop(Entity e){
		carry=e;
		carryMode=2;
		e.dx-=px-x;
		e.dy-=py-y;
	}
	public void unGrab(){
		if(carry!=null){
			carry=null;
			carryMode=0;
		}
	}
}
