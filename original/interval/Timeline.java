package interval;


public class Timeline {


	private int terra;
	private int ent;
	private double tickCal;

	long tick;
	double hour;
	double day;
	int year;

	boolean End=false;
	double timeFactor=1;
	//boolean WORLD_TIME;


	public Timeline(){
		setActive(true);
		terra=0;
		ent=0;
		tick=0;
		tickCal=0;
		hour=0;
		day=0;
		year=0;
		End=false;
		//WORLD_TIME=false;
	}

	public int getTerra(){
		return terra;
	}
	public int getHour(){
		return (int) hour;
	}
	public int getDay(){
		return (int) day;
	}
	public int getYear(){
		return year;
	}

	public int getEnt(){
		return ent;
	}

	public long getTick(){
		return  tick;
	}

	public float getSun(){
		return (float) ((hour/12f)-0.5);
	}
	public  float getDaylight(){
		if(Main.level.getStats().canLightCycle()){
			float ss=0;
			if(hour>4 && hour<8){
				ss=(float) (0.8f*(hour-4)/4f);
			}else if(hour>=8 && hour<12){
				ss=(float) (0.8f+0.2f*(hour-8)/4f);
			}else if(hour>=12 && hour<16){
				ss=(float) (1f-(0.2f*(hour-12)/4f));
			}else if(hour>=16 && hour<20){
				ss=(float) (0.8f*(20-hour)/4f);
			}else{
				ss=0;
			}
			return ss;
		}
		return Main.level.getStats().getDefaultLight();
	}
	public  float getSeason(){
		if(Main.level.getStats().canSeasonCycle()){
			return (float) (Math.cos(   (6.29f*(float)hour/12f)   ));
		}
		return Main.level.getStats().getDefaultSeason();
	}
	public boolean getRain(){
		return (day%7)==0;
	}
	public void ageLand(){
		Main.world.land.future(getTerra());
	}

	public void ageLand2(){
		Main.world.land.DecayN();
	}

	public static int computeTerraRaw(Stats s,long in){
		return (int)Math.floor(s.getRTerra()*in);
	}

	public static int computeTerra(Stats s,long in){
		int terrat= computeTerraRaw(s,in);
		if(terrat>s.getCapTerra()){
			terrat=s.getCapTerra();
		}
		return terrat;
	}

	int pe=0;

	public static int computeEnt(Stats s,long in){
		double der=in/s.getREnt();

		int entt=(int) Math.floor(der);
		if(entt>s.getCapEntSnaps()){
			entt=s.getCapEntSnaps();
		}
		return entt;
	}
	public static long invertEnt(Stats s,long in){
		return (long) (in*s.getREnt());
	}
	int pe2=0;
	public void pureFigure(Stats s){
		int en=computeEnt(s,pureTick);
		if(en>pe2){
			Main.player.annual();
		}
		pe2=en;
	}
	public void figure(Stats s,boolean pure){
		ent=computeEnt(s,tick);
		int en=ent;//computeEnt(s,tick);
		if(en>pe){
			Message.addPass("annual "+en,1);
			Main.world.annualE();
			if(!pure)
				Main.player.annual();
		}
		pe=en;
		if(!s.isNoTerrainAge()){
			terra=computeTerra(s,tick);
			if(pt<terra){
				ageLand();
			}
			pt=terra;
		}
		tickCal= (double)tick/(double)s.getRCalender();
		double partialYear=tickCal%Calender.calo;
		double dayd=partialYear/(24.0);
		double hourd=24*(dayd-( Math.floor(dayd)));
		hour=hourd;
		day=(double) Math.floor(dayd);
		year=(int) (tickCal/(Calender.calo));
	}
	private boolean active=true;
	int pt;

	int fakeTick=0;
	long pureTick=0;

	double subTick;

	boolean skipTick=false;


	public void run(){
		if(isActive()){
			double rate=timeFactor;
			Stats s =Main.level.getStats();

			if(s.isInterval()){
				if(tick<s.getCapTicks()-1){

					pureTick++;
					boolean pure=rate<1;
					if(pure){
						subTick+=rate;
						skipTick=subTick<1;
						pureFigure(s);
						if(!skipTick){
							subTick--;
							tick++;
						}else{
							return;
						}
						
					}else{
						skipTick=false;
						tick++;
					}
					figure(s,pure);
				}else{
					Changer.endTime();
				}
			}else{
				fakeTick+=rate;
				terra=computeTerraRaw(s,fakeTick);
				if(terra!=0){
					fakeTick=0;
					if(terra>1){
						for(int u=0;u<terra;u++){
							ageLand2();
						}
					}else{
						ageLand2();
					}
				}
				terra=0;
			}
		}
	}
	public long getPureTick(){
		return pureTick;
	}
	public void setTime(long i){
		pureTick=(long) (i/timeFactor);
		tick=i;
		if(i>5){
			i-=5;
		}
		pt=999999;
		figure(Main.level.getStats(),false);
		End=false;
		setActive(true);
	}

	public String wallClock(){
		return Calender.clocker(hour);
	}
	public String getMonth(){
		return Calender.calenDate(getDay());
	}

	public String getMilenia(){
		return Calender.manlyYear(getYear());
	}

	public String stringCalender(){
		return Calender.stringCalender(hour,getDay(),getYear());
	}

	public boolean shouldSkip(){
		return skipTick;
	}
	public double getTimeFactor(){
		return timeFactor;
	}

	public void setTimeFactor(double d) {
		timeFactor=d;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
