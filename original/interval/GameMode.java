package interval;

public enum GameMode {

	
		Omega_Historic("Omega: Historic",new Stats().set(true, 64, 1).timeSpan(1000, 300)),
		Omega_Futurist("Omega: Futurist",new Stats()),
		Blitz("Blitzeit",new Stats().set(false,64,20).timeSpan(1, 1)),
		Standard("Standard",new Stats().set(true, 64, 20).timeSpan(500, 10).lockSeason(0.3f)),
		Moments("Moments",new Stats().set(true, 4096, 1).timeSpan(500, 60).staticTerrain()),
		Regular("Regular",new Stats().set(false, 256, 20)),
		Linear("Linear",new Stats().set(false, 64, 1)),
		Menu("Title Screen",new Stats().set(false, 32, 1).setRTerra(2).light(50f, 0.5f).lockLight(0).lockSeason(0.2f).nonInterval());
	//omega- massive real-years, large terrain years,seasons off obviously, span could go 1000's of years
	//historic- only backwards timeline aka start finalyear
	//futurist- only forwards timeline aka start year 1
	
	//blitz- fast time- no travel
	//macro
	
	//standard- 30 terrainYears per real year, 5 minute (5*60*60=18000 ticks) real years, 
	//10 seconds per terrainYear,
	//base
	
	//moments- alot of terrainyears, but no decay/age/ any terrain change, simply alot of time travel 
	//so no terrain checks to bog down, day night
	//micro
	
	//regular- no time travel, no nothin, just day night cycles 5 mins, regular joe
	
	//linear-no controlled time travel, no paradox tolerance, peform task under future self (automated) assistance,
	//go back in time and replace future self and assit past self in similar steps
		String name;
		Stats rules;
	GameMode(String name,Stats rules){
		this.name=name;
		this.rules=rules;
	}
	public Stats getStat(){
		return rules;
	}
}
