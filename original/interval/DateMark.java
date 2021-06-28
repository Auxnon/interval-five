package interval;

public class DateMark {

	short day;
	short month;
	short hour;
	short minute;
	int year;
	String level;
	int iterate;
	
	public void DateMark(int min, int ho,int da,int mon,int yea){
		minute=(short) min;
		hour=(short) ho;
		day=(short) da;
		month=(short) mon;
		year=yea;
		level="";
		iterate=0;
	}
	public void DateMark(int min, int ho,int da,int mon,int yea,String lvl,int it){
		//this(min,ho,da,mon,yea);
		level=lvl;
		iterate=it;
	}
}
