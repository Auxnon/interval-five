package interval;

public class Note {
	/**
	 * pitch of note
	 */
	int tone;
	/**
	 * how long to sound this particular note
	 */
	int hold; 
	/**
	 * total time note occupies
	 */
	int length; //occupies
	
	public Note(int t,int h,int l){
		tone=t;hold=h;length=l;
	}
}
