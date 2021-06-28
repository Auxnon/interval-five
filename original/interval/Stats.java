package interval;

//THIS SPECIAL CLASS RESERVED FOR ALL
//UNIVERSAL AND/OR GLOBAL PARAMETERS FOR ALL GAME CLASSES AND FUNCTIONS

public class Stats {
	private boolean timeTravel; //can player manipulate the timeline
	private boolean pastPlayer; //on
	private boolean water=true; //turn on water terrain, this is currently broken :(
	private boolean lightCycle; //should light level change from dusk to dawn
	private boolean seasonCycle; //should grass change color for seasons
	private boolean noTerrainAge; //should terrain not age, useful for short maps
	private boolean nonInterval; //should time be non interval, thus being stuck on the timeline with a single terrain buffer
	
	//private double rate;
	private double rTerra;
	private long rEnt;
	private int rCalender;
	private double rHour;
	private double rDay;
	private double rYear;
	private float defaultSeason;
	private float defaultLight;

	private int capYear;
	private int capDay;
	private int capHour;
	private int capTerra;
	private int capEntSnaps;
	private long capTicks;
	private int capCalender;

	private int size;
	private int landHeight=1;
	private int waterHeight=1;
	private int defaultBlocks[];

	private int currentBlock;


	//private double FACTOR;

	private double agePerTerraFactor;

	private float clip1=50.0f;
	private float clip2=0.0f;


	public boolean canTimeTravel() {
		return timeTravel;
	}

	public Stats nonInterval(){
		//capTerra=0;
		nonInterval=true;
		timeTravel=false;
		return this;
	}
	public Stats interval(){
		nonInterval=false;
		timeTravel=true;
		return this;
	}
	public boolean isInterval(){
		return !nonInterval;
	}
	public void setTimeTravel(boolean timeTravel) {
		this.timeTravel = timeTravel;
	}

	public boolean canPastPlayer() {
		return pastPlayer;
	}

	public void setPastPlayer(boolean pastPlayer) {
		this.pastPlayer = pastPlayer;
	}

	public boolean isWater() {
		return water;
	}

	public void setWater(boolean water) {
		this.water = water;
	}

	public boolean canLightCycle() {
		return lightCycle;
	}

	public void setLightCycle(boolean lightCycle) {
		this.lightCycle = lightCycle;
	}

	public boolean canSeasonCycle() {
		return seasonCycle;
	}

	public void setSeasonCycle(boolean seasonCycle) {
		this.seasonCycle = seasonCycle;
	}

	public boolean isNoTerrainAge() {
		//capTerra=0;
		return noTerrainAge;
	}

	public void setNoTerrainAge(boolean noTerrainAge) {
		this.noTerrainAge = noTerrainAge;
	}

	
	public double getRTerra() {
		return rTerra;
	}

	public Stats setRTerra(double rTerra) {
		this.rTerra = rTerra;
		return this;
	}

	public long getREnt() {
		return rEnt;
	}

	public void setREnt(long irEnt) {
		this.rEnt = irEnt;
	}

	public int getRCalender() {
		return rCalender;
	}

	public void setRCalender(int irCalender) {
		this.rCalender = irCalender;
	}

	public double getRHour() {
		return rHour;
	}

	public void setRHour(double rHour) {
		this.rHour = rHour;
	}

	public double getRDay() {
		return rDay;
	}

	public void setRDay(double rDay) {
		this.rDay = rDay;
	}

	public double getRYear() {
		return rYear;
	}

	public void setRYear(double rYear) {
		this.rYear = rYear;
	}

	public float getDefaultSeason() {
		return defaultSeason;
	}

	public void setDefaultSeason(float defaultSeason) {
		this.defaultSeason = defaultSeason;
	}

	public float getDefaultLight() {
		return defaultLight;
	}

	public void setDefaultLight(float defaultLight) {
		this.defaultLight = defaultLight;
	}

	public int getCapYear() {
		return capYear;
	}

	public void setCapYear(int capYear) {
		this.capYear = capYear;
	}

	public int getCapDay() {
		return capDay;
	}

	public void setCapDay(int capDay) {
		this.capDay = capDay;
	}

	public int getCapHour() {
		return capHour;
	}

	public void setCapHour(int capHour) {
		this.capHour = capHour;
	}

	public int getCapTerra() {
		return capTerra;
	}

	public void setCapTerra(int capTerra) {
		this.capTerra = capTerra;
	}

	public int getCapEntSnaps() {
		return capEntSnaps;
	}

	public void setCapEntSnaps(int capEntSnaps) {
		this.capEntSnaps = capEntSnaps;
	}

	public long getCapTicks() {
		return capTicks;
	}

	public void setCapTicks(long capTicks) {
		this.capTicks = capTicks;
	}

	public int getCapCalender() {
		return capCalender;
	}

	public void setCapCalender(int capCalender) {
		this.capCalender = capCalender;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getLandHeight() {
		return landHeight;
	}

	public void setLandHeight(int landHeight) {
		this.landHeight = landHeight;
	}

	public int getWaterHeight() {
		return waterHeight;
	}

	public void setWaterHeight(int waterHeight) {
		this.waterHeight = waterHeight;
	}

	public int[] getDefaultBlocks() {
		return defaultBlocks;
	}

	public void setDefaultBlocks(int[] defaultBlocks) {
		this.defaultBlocks = defaultBlocks;
	}

	public int getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(int currentBlock) {
		this.currentBlock = currentBlock;
	}

	

	public double getAgePerTerraFactor() {
		return agePerTerraFactor;
	}

	public void setAgePerTerraFactor(double agePerTerraFactor) {
		this.agePerTerraFactor = agePerTerraFactor;
	}

	public float getClip1() {
		return clip1;
	}

	public void setClip1(float clip1) {
		this.clip1 = clip1;
	}

	public float getClip2() {
		return clip2;
	}

	public void setClip2(float clip2) {
		this.clip2 = clip2;
	}



	public Stats(){
		this.timeTravel = true;
		//this.locked = false;
		this.size = 32;
		this.landHeight = 4;
		this.waterHeight=2;
		this.defaultBlocks = new int[]{0};
		this.lightCycle = true;
		this.seasonCycle=true;

		

		capYear=1;
		capDay=0;
		capHour=0;
		capTerra=1;
		capEntSnaps=100;

		this.clip1 = 50f;
		this.clip2 = 0f;
		this.noTerrainAge=false;
		pastPlayer=true;
		this.lockSeason(0.4f);
		computeRates();
	}

	public Stats computeRates(){
		long cd=capDay;
		long cy=capYear;
		long ch=capHour;
		setRCalender(50);
		setCapTicks( rCalender*(((24*((365*cy)+cd))+ch)) );
		//capTicks=1*365*20*60;//standard 12 minute days or about 120x realworld time
		//in other words, to make the scale be real world timed, multiply by 120
		//but realize this captick is an ENTIRE YEAR, so that would mean running through it all would take A YEAR
		//whereas running through at 120x speed takes...73 hours, which is just as absurd but i dont expect people
		//to be crazy enough to run through it like that, but then this is just ONE scenario



		setREnt( (capTicks/capEntSnaps) );
		setRTerra( (double)capTerra/(double)capTicks );
		setCapCalender( (((24*((365*capYear)+capDay))+capHour)) );
		//(double)capCalender/(double)capTicks;
		//FACTOR=rate/rCalender;
		// System.out.println("cap: "+(capTicks));
		return this;
	}
	public Stats set(boolean canTimeTravel,int gridSize,int baseHeight){
		timeTravel=canTimeTravel;

		size=gridSize;
		landHeight=baseHeight;
		return this;
	}
	public Stats staticTerrain(){
		noTerrainAge=true;
		return this;
	}
	/* public Stats menu(boolean amIAMenu){
		 locked=amIAMenu;
		 return this;
	 }*/
	/* public Stats blockCycle(int types[],int tickCycles){
		 swapBlocks=true;
		 defaultBlocks=types;
		 swapBlockTime=tickCycles;
		 return this;
	 }*/
	/*public Stats finalBlock(int type){
		 swapBlocks=false;
		 defaultBlocks=new int[]{type};
		 currentBlock=type;
		 return this;
	 }*/

	public Stats lockSeason(float s){
		seasonCycle=false;
		defaultSeason=s; 
		return this;
	}
	public Stats lockLight(float d){
		lightCycle=false;
		defaultLight=d;
		return this;
	}
	public Stats light(float gradient,float cutoff){
		clip1=gradient;
		clip2=cutoff;
		return this;
	}
	public Stats timeSpan(int years,int ticksToCycle){
		capTerra=years;
		rTerra=ticksToCycle;
		return this;
	}



	public void parse(String s){
		int i=s.indexOf(":");
		if(i!=-1){
			String unit=s.substring(0,i);
			unit=unit.trim();
			String value=s.substring(i+1);
			value=value.trim();
			//System.out.println("unit: " +unit+" value: "+value);

			if(unit.contains("size")){
				this.size=Integer.parseInt(value);
			}else
				if(unit.contains("hour")){
					//this.firstHour=Float.parseFloat(value)/24.0f;
					//this.days=false;
				}
		}
	}
}
