package interval;


public class Task {

	public int year=0;
	public long tick=0;
	public int length=0;
	public String action="nothing";
	
	public int c;
	
	public Task(){	

	}
	public Task(int year,long tick,int length,String action,int C){
		this(year,tick,length,action);
		c=C;
	}
	public Task(int year,long tick,int length,String action){
		this.year=year;
		this.tick=tick;
		this.length=length;
		this.action=action;
	}
	public Task(int year,long tick,String action){
		this.year=year;
		this.tick=tick;
		this.action=action;
		this.length=0;
	}
	public Task(int length,String action){
		this.length=length;
		this.action=action;
	}
}
