package intervalEntity;




public class Critter extends LinearEntity {

	public Critter(int L, int age) {
		super(L, age);
		// TODO Auto-generated constructor stub
	}
/*
	float radial;
	Coord center;
	float destx;
	float desty;
	float speed=0.1f;
	int pe;
	int breeded;
	int breedMeter;
	int breedRate;
	boolean asexual=false;
	int age;

	public Critter(int L,int ag,float x,float y){
		super(L,ag);
		init(x,y);
	}
	public VoxelGroupObject getModel(){
		return Actor.aminal;
	}

	public Critter(float x,float y){
		super();
		init(x,y);
	}
	public void init(float x,float y){
		place(x,y);
		center = new Coord(x,y);
		radial=10f;
		pe=-1;
		breedRate=3;
		breedMeter=0;
		breeded=0;
	}
	public void travel(Coord c){
		destx=c.x;
		desty=c.y;
		center=c;
	}
	public void wander(Coord c){
		destx=c.x;
		desty=c.y;
	}
	public Coord destinator(int m){
		int rr=choice(m,360);
		r=rr;
		float dd=radial*(float)choice(m,100)/100f;
		float xy[]=Spin.get(rr);
		float xx=center.x+xy[0]*dd;
		float yy=center.y+xy[1]*dd;
		return new Coord(xx,yy);
	}
	public void render(){
		if(active())
		super.render();
	}
	public void time(){
		int ce=Main.world.timeline.getEnt();
		n2=(ce-schedule.startYear2);
		
		if(!active()){
			return;
		}
		
		int tick=Main.world.timeline.getTick();
		if(schedule.endTick<tick){
			schedule.endTick=tick;
		}


		if(ce!=pe){
			pe=ce;
			wander(destinator(pe));
			breed(pe,false);
		}

		float ux=x-destx;
		float uy=y-desty;
		float ur=(float) Math.sqrt(ux*ux + uy*uy);
		if(ur>0.1f){
			float uxx=ux/ur;
			float uyy=uy/ur;
			r=(float) Math.toDegrees(Math.atan2(ux, uy))+270;
			if(r>=360){
				r-=360;
			}
			dx-=speed*uxx;
			dy-=speed*uyy;
		}

		physics();
	}

	public void activity(int c,float multi){

		Coord co=destinator(c-1);
		place(co.x,co.y);
		
		breed( c,true);
		schedule.annualLocation(getVec());
	}
	public boolean active(){
		return (n2+age)<50;
	}
	public void breed(int c,boolean b){
		breedMeter= (c-schedule.startYear2)-breeded*breedRate;
		if(breedMeter>breedRate){
			boolean boo=asexual;
			if(!asexual){
				Entity ee=Main.world.nearest(2f, this);
				if(ee!=null){
					//System.out.println(ee instanceof Critter);
					if(ee instanceof Critter){
						Critter cr=(Critter)ee;
						boolean bool=cr.breedMeter>breedRate;
						//if(ee.n4<=1){
							boo=bool;
							cr.breeded++;
						//}else{
							//if(b){
								//place()
							//}
						//}
					}
					//boo=true;//ee.getVec().n1>breedRate;
				}
			}
			if(boo){
				breeded++;
				Main.world.addE(new Critter(choice(c,999),0,x,y));
			}
		}
		
		n1=breeded;
		//n2=age;
	}

	public void setTime(int i){
		super.setTime(i);
		Coord c=destinator(i-1);
		place(c.x,c.y);
		breeded=n1;
		//age=n2;
	}*/

}
