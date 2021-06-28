package intervalEntity;

public class Damage {

	int targetPIN;
	int originPIN;
	int amount;
	long occurence;
	public Damage(int target,int origin,int amount,long occ){
		targetPIN=target;
		originPIN=origin;
		this.amount=amount;
		occurence=occ;
	}
	public int getAmount(){
		return amount;
	}
}
