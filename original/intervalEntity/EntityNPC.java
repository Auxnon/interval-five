package intervalEntity;

import interval.Main;
import interval.Timeline;
import interval.UserData;
import intervalParticle.PWord;

/**
 * 
 * @author Aninon
 * @usage EntityPerson child, specifically designated as an NPC with AI systems
 */
public class EntityNPC extends EntityPerson{

	
	public EntityNPC(){
		act="";
		baseModel="kid";
		//walk1="UIdle";
		//walk2="UIdle";
		Useable();
		this.sideCollision=true;
		this.renewDimensions();
	}


	@Override
	public void time(Timeline t){
		super.time(t);
		tix++;
		if(tix>cap){
			tix=0;
			cap=60+Main.rand.nextInt(200);
			switch(Main.rand.nextInt(8)){
			case 1:act="left";break;
			case 2:act="right";break;
			case 3:act="up";break;
			case 4:act="down";break;
			//case 5:act=""; command("chat");break;
			default: act="";command("jump");
			}
		}
		command(act);
	}
	Entity target;
	String act;
	int tix;
	int cap;
	//boolean once;
	private void walky(){
		walkin=true;setAniMode(1);actionDelay=5;
	}
	public void hear(Entity e,String m){
		command("chat");cap=180;tix=0;act="watch";target=e;
	}
	public void used(Entity e){
		command("sayHappy"); cap=180;tix=0;act="watch";target=e;
	}
	public void command(String s){
		float speed=0.02f;
		if(s!=""){
			if(s.contains("right")){
				dx+=speed;walky();r=270;
				return;
			}else if(s.contains("left")){
				dx-=speed;walky();r=90;
				return;
			}else if(s.contains("up")){
				dy+=speed;walky();r=0;
				return;
			}else if(s.contains("down")){
				dy-=speed;walky();r=180;
				return;
			}else if(s.contains("jump")){
				vz+=UserData.getJumpSpeed();
				return;
			}else if(s.contains("chat")){
				speak(PWord.CHAT);
				act="";
				cap=180;tix=0;
				return;
			}else if(s.contains("watch")){
				if(target==null)
					return;
				r=(float) (-180f*Math.atan2(target.x-x, target.y-y)/Math.PI);
				return;
			}else if(s.contains("say")){
				String st=s.substring(3);
				int y=PWord.DUNNO;
				if(st.equals("Alert")){
					y=PWord.CAUTION;
				}else if(st.equals("Happy")){
					y=PWord.HAPPY;
				}
				speak(y);
			}
		}
	}
	public void speak(int u){
		Main.world.addP(new PWord(x,y,z+height,u));
		setAniMode(5);actionDelay=60;
	}
	@Override
	public void render(){
		super.render();
			walkin=false;
	}
}
