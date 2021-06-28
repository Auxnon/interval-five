package interval;

public class Identity {

	int age;
	int metaAge;
	int timeAge; //adjuster for timetravel
	boolean timeTraveller;

	int birthHour;
	int birthDay;
	int birthYear;
	int birthTick;

	int originDay;
	int originYear;

	int mortality;



	//characteristics
	String name;
	int PIN;


	int custom;
	float Intelligence;
	int Generation;

	float aggression;
	float vitality;
	float size;
	float speed;
	float immunity;
	float fertility;
	float mutation;
	Vec color;
	float complacency;

	//variable with timeline;
	float hunger;
	float thirst;
	int children;
	float fear;
	
	boolean exists;
	private boolean living;

	public Identity(){
		timeAge=0;
		Timeline t=Main.world.timeline;
		timeTraveller=false;
		birthHour=t.getHour();
		birthDay=t.getDay();
		birthYear=t.getYear();
		originDay=birthDay;
		originYear=birthYear;
		mortality=40*24;
	}

	public void oldestYear(int year){

	}
	public void aging(){
		Timeline t= Main.world.timeline;
		aging(t.getHour(),t.getDay(),t.getYear());
	}
	public void aging(int hour,int day,int year){
		metaAge=timeAge+((((hour-birthHour)*24)+ (day-birthDay))*365)+year-birthYear;
		age=(int) Math.floor(metaAge/8760f);
		exists=metaAge>0;
		setLiving(exists &&(metaAge<=mortality));
	}
	public void adjustTime(){
		Timeline t= Main.world.timeline;
		adjustTime(t.getHour(),t.getDay(),t.getYear());
	}
	public void adjustTime(int hour,int day,int year){
		timeTraveller=true;
		timeAge=age;
		birthHour=hour;
		birthDay=day;
		birthYear=year;
	}

	public boolean isLiving() {
		return living;
	}

	public void setLiving(boolean living) {
		this.living = living;
	}
}
