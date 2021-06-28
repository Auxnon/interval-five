package interval;

public class Calender {

	
	//public static int[] calen(int days){


	//return new int[]{0,0};
	//}
	public static int[] calen(int d){
		d++;
		int mo=0;
		if(d<=31){
			mo=1;
		}else if(d<=60){
			d-=31;
			mo=2;
		}else if(d<=91){
			d-=60;
			mo=3;
		}else if(d<=121){
			d-=91;
			mo=4;
		}else if(d<=152){
			d-=121;
			mo=5;
		}else if(d<=182){
			d-=152;
			mo=6;
		}else if(d<=213){
			d-=182;
			mo=7;
		}else if(d<=244){
			d-=213;
			mo=8;
		}else if(d<=274){
			d-=244;
			mo=9;
		}else if(d<=305){
			d-=274;
			mo=10;
		}else if(d<=335){
			d-=305;
			mo=11;
		}else{
			d-=335;
			mo=12;
		}
		return new int[]{mo,d};
	}
	
	public static String date(int mo,int da){
		String mon="";
		switch(mo){
		case 1:mon="January";break;
		case 2:mon="February";break;
		case 3:mon="March";break;
		case 4:mon="April";break;
		case 5:mon="May";break;
		case 6:mon="June";break;
		case 7:mon="July";break;
		case 8:mon="August";break;
		case 9:mon="September";break;
		case 10:mon="October";break;
		case 11:mon="November";break;
		case 12:mon="December";break;
		}
		String day=""+da;
		if(day.endsWith("1")){
			day+="st";
		}else if(day.endsWith("2")){
			day+="nd";
		}else if(day.endsWith("3")){
			day+="rd";
		}else{
			day+="th";
		}
		return mon+" "+day;
		
	}
	public static String calenDate(int i){
		int[] ii=calen(i);
		return date(ii[0],ii[1]);
	}
	
	public static long calo=24*365;
	
	public static String predictCalender(Stats s,long en){
		long tickCal2= (long) (en/s.getRCalender());//invertEnt(s,en);
		if(Long.bitCount(tickCal2)<32){
			double tickCal3=  ((double)en/(double)s.getRCalender());//invertEnt(s,en);
			double partialYear=tickCal3%calo;
			double dayd=partialYear/(24.0);
			double hourd=24*(dayd-( Math.floor(dayd)));
			return stringCalender(hourd,(int) Math.floor(dayd),(int)( ( en/  ( (long)calo*50 )  )   ));
		}else{
			long partialYear=tickCal2%calo;
			double dayd=partialYear/(24.0);
			double hourd=24*(dayd-( Math.floor(dayd)));
			return stringCalender(hourd,(int) Math.floor(dayd),(int)( ( en/  ( (long)calo*50 )  )   ));
		}

	}
	
	public static String manlyYear(int i){
		String n=""+i;
		int N=4-n.length();
		String out="";
		if(N>0){
			for(int j=0;j<N;j++)out+="0";
		}

		return out+n;
	}
	
	public static String stringCalender(double h,int d,int y){
		String s=calenDate(d)+","+manlyYear(y);//Main.world.stats.capCalender+"_"+tickCal+"_";
		return clocker(h)+" "+s;
	}
	public static String clocker(double n){
		int pure=(int) Math.floor(n);
		double digits=n-pure;
		String dd=""+((int) Math.floor(digits*60));
		if(dd.length()<2){
			dd="0"+dd;
		}
		return pure+":"+dd;
	}
	
	
	public String realTime(long tik){
		long secs=tik/60;
		long mins=secs/60;
		long secz=secs%60;
		long hourz=mins/60;
		long minz=mins%60;
		return hourz+":"+minz+":"+secz;
	}

}
