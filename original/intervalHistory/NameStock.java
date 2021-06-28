package intervalHistory;

import interval.Main;

import java.util.Random;

public class NameStock {

	static String last[];
	static String firstM[];
	static String firstF[];
	static{
		last=new String[]{
				"Smith",
				"Johnson",
				"McAvoy",
				"Erickson",
				"Harris",
				"Anderson",
				"Miller",
				"Rudolph",
				"Mann",
				"Burnes",
				"Young",
				"Long",
				"Cumberdale",
				"Jameson",
				"Frederick",
				"Emmit",
				"Williams",
				"Ferris",
				"Fox",
				"Cogan",
				"Robinson",
				"Goodman",
				"Baker",
				"Parker",
				"Thatcher",
				"Stevens",
				"Houser",
				"Grey",
				"Wailes",
				"Moore",
				"Sanders",
				"Zinn",
				"Felix",
				"Scott",
				"Parsons",
				"Hoover"
		};
		firstM=new String[]{
				"John",
				"James",
				"Mark",
				"Eric",
				"Richard",
				"Arbor",
				"Philip",
				"Nick",
				"Max",
				"Nate",
				"George",
				"Paul",
				"Luke",
				"Hank",
				"Evan",
				"Carl",
				"Henry",
				"Larry",
				"Jacob",
				"Zachary",
				"Michael",
				"Chris",
				"Martin",
				"Dan",
				"Tyler",
				"Patrick",
				"Herald"
		};
		firstF=new String[]{
				"Susan",
				"Jill",
				"Maxine",
				"Mary",
				"Missy",
				"Patricia",
				"Amber",
				"Margaret",
				"Tanya",
				"Lila",
				"Eve",
				"Angelica",
				"Laura",
				"Heather",
				"Olga",
				"Helga",
				"Zelda",
				"April",
				"Sarah",
				"Jannet",
				"Danielle",
				"Grace",
				"Faith",
				"Jennifer",
				"Abigale",
				"Allyson",
				"Maria",
				"Jullian"
		};
	
	}
	
	public static String getLastName(int seed){
		return last[Main.world.Rand(seed, last.length)];
	}
	public static String getFirstName(boolean gender,int seed){
		if(gender)
			return firstM[Main.world.Rand(seed, firstM.length)];
		else
			return firstF[Main.world.Rand(seed, firstF.length)];
	}
	public static Dynasty getDynasty(int seed){
		return new Dynasty(getLastName( seed));
	}
}
