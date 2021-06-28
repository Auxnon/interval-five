package interval;


import java.util.ArrayList;

public class Music {

	int instr;
	ArrayList<Note> notes;
	public static Music test;
	int pace=3;
	int tick=0;
	int tickPace=0;
	int index=0;
	boolean noteReady=true;
	boolean loop=false;
	boolean done=true;
	public boolean beep=true;

	public void renew(){
		done=false;
		index=0;
		tick=0;
		tickPace=0;
	}
	public static void init(){
		test=new Music();
		/*test.add(6, 4);
		test.add(8, 4);
		test.add(8, 4);

		test.add(9, 6);
		test.add(9, 1);

		test.add(7, 4);
		test.add(8, 4);

		test.add(9, 7);
		test.add(9, 1);

		test.add(7, 4);
		test.add(8, 4);
		test.add(6, 4);
		test.add(5, 4);
		test.add(6, 4);
		test.add(4, 4);*/

		/*test.add("D3", 1);
		test.add("F3", 1);
		test.add("D4", 1);

		test.add("D3", 3);
		test.add("F3", 1);
		test.add("D4", 1);
		 */
		int d=2;

		test.add("A3", 2*d);
		//	123  6666 568 7777   

		test.add("F3", 2*d);
		test.add("A3", 2*d);
		test.add("B3", 2*d);

		/*test.add("F3", 3*d);
		test.add("A3", 2*d);
		test.add("B3", 2*d);

		test.add("F3", 3*d);
		test.add("A3", 2*d);
		test.add("B3", 2*d);
		test.add("E4", 2*d);
		test.add("D3", 2*d);

		test.add("B3", 2*d);
		test.add("C3", 2*d);
		test.add("B3", 2*d);
		test.add("G3", 2*d);
		test.add("E3", 2*d,6);

		test.add("B3", 6*d);
		test.add("C3", 2*d);
		test.add("D3", 2*d);
		test.add("E3", 2*d);*/

		/*test.add("F3", 2);
		test.add("A3", 2);
		test.add("B3", 2);

		test.add("F3", 2);
		test.add("A3", 2);
		test.add("B3", 2);

		test.add("C3", 2);*/
		/*
		F, A, B, F, A, B, F, A, B, E (high), 
		D, B, C, B, G, E (low, one octave below the high one), 
		B, C, D, E, F, A, B, F, A, B, F, A, B, E (high like the first), 
		D, B, C, B, G, E (low like the second), B, C, D, E, D, 
		E, F, G, A, B, C, B, E, F, G, A, B, C, D, E, F, G, D, 
		E, F, G, A, B, C, B, E, (fast) F, E, A, G, B, A, C, B, 
		D, C, E, D, B, C, A, B
		 */
		test.renew();
	}
	public Music(){
		notes =new ArrayList<Note>();
	}
	public void add(String n,int s){
		add(n,s,1);
	}
	public void add(int n,int s){
		add(n,s,0);
	}
	public void add(int n,int s,int h){
		notes.add(new Note(n,h*pace,s));
	}

	public void add(String ss,int s,int h){
		notes.add(new Note(Main.sound.findNote(ss),h*pace,s));
		beep=false;
	}

	public void run(){
		if(done)
			return;


		Note no=notes.get(index);
		tickPace++;
		if(tickPace>pace){
			tickPace=0;
			tick++;


			if(noteReady){
				noteReady=false;
				playNote();
				//for simultaneous notes
				while(index<notes.size()&&notes.get(index).length==0){
					Message.addPass("dual note");
					playNote();
					index++;
				}
			}


			if(tick>=no.length){
				tick=0;
				index++;
				noteReady=true;
				if(index>=notes.size()){
					if(loop)
						index=0;
					else
						done=true;
				}
			}
		}
	}
	public void playNote(){

		Note n=notes.get(index);
		if(n.tone==-1){
			return;
		}
		if(beep){
			if(n.hold>0)
				Main.sound.beep(n.tone,n.hold);
			else
				Main.sound.beep(n.tone);
		}else{
			//if(n.hold>0)
				//Main.sound.playNote(n.tone,n.hold);
			//else
				//Main.sound.playNote(n.tone);
		}

	}
	public void setPace(int p){
		pace=p;
	}
	public int getPace(){
		return pace;
	}
}
