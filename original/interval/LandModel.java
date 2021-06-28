package interval;

public class LandModel {

	int x;
	int y;
	int choice=-1;
	
	boolean updatedC=false;
	boolean updatedV=false;
	
	boolean visible;
	
	boolean equilibrium;
	boolean waterStill;
	
	
	int n;
	int c;
	int v;
	
	public void renew(){
		
	}
	public void load(){
		if(visible){
			return;
		}
		int m=Land.modelRef.length;
		choice=0;//-1;
		for(int i=0;i<m;i++){
			if(!Land.modelRef[i].used){
				//Land.model[i]=Model.createMap(x, y);
				updatedC=true;
				updatedV=true;
				Land.modelRef[i].used=true;
				Land.modelRef[i].updatedC=true;
				Land.modelRef[i].updatedV=true;
				choice=i;
				break;
			}
		}
		Message.m(this,"added: "+choice);
		visible=choice!=-1;
	}
	public void unload(){
		
		visible=false;
		if(choice==-1){
			return;
		}
		Message.m(this,"removed: "+choice+"    "+UserData.getLandModels());
		Land.modelRef[choice].used=false;
		choice=-1;
	}
	
	public int getChoice(){

		return choice;
	}
	public LandModel(int xx,int yy){
		x=xx;
		y=yy;
		visible=false;
		equilibrium=false;
		waterStill=false;
	}
}
