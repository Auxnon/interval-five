package intervalEntity;

import org.lwjgl.opengl.GL11;

import interval.Main;
import interval.Texture;
import interval.VecF;

public class gTimer extends Gadget {

	int Time=0;
	int Cap;
	float rate=0.005f;
	VecF Color2;
	public gTimer(int cap){
		Cap=cap;
		this.sideCollision=true;
		Color.setV(0.8f,0.2f,0.2f);
		Color2=new VecF(0,1,0,1);
		//ON=false;
	}
	public gTimer(){
		this(120);
	}

	public void gadgetOn(){
		Time++;
		if(Time>=Cap){
			Time=0;
			outState=!outState;
			if(!outState){
				Color.setV(0.2f,0.6f,0.2f);Color2.setV(1,0,0);
			}else{
				Color.setV(0.6f,0.2f,0.2f);Color2.setV(0,1,0);
			}
		}
	}
	
	public void gadgetOff(){
		//Time=0;
	}

	public String[] infer(String param){
		String st[]=super.infer(param);
		Cap=Integer.parseInt(st[inferIndex]);
		inferIndex++;
		
		
		if(!ON){
			Color.setV(0.2f,0.6f,0.2f);Color2.setV(1,0,0);
		}else{
			Color.setV(0.6f,0.2f,0.2f);Color2.setV(0,1,0);
		}
		return st;
	}

	public void render(){



		float sx=x-getHalfSize();
		float ex=x+getHalfSize();
		float sy=y-getHalfSize();
		float ey=y+getHalfSize();

		float bit=+0.01f;

		float sx2=sx-bit;
		float ex2=ex+bit;
		float sy2=sy-bit;
		float ey2=ey+bit;

		float amount=(float)Time/(float)Cap;
		//double ro=2*amount*Math.PI;
		float xo;//=(float) Math.cos(ro)*getHalfSize();
		float yo;//=(float) Math.sin(ro)*getHalfSize();
		//xo+=x;
		//yo+=y;

		if(amount<0.125 || amount>0.875){
			xo=ex2;
			if(amount>0.875)
				yo=((amount-1)/0.125f);
			else
				yo=amount/0.125f;

			yo=y+yo*(getHalfSize()+bit);
		}else if(amount<0.375){
			yo=ey2;
			xo=x-(getHalfSize()+bit)*(amount-0.25f)/0.125f;
		}else if(amount<0.625){
			xo=sx2;
			yo=y-(getHalfSize()+bit)*(amount-0.5f)/0.125f;
		}else{ // if(amount<0.875)
			yo=sy2;
			xo=x+(getHalfSize()+bit)*(amount-0.75f)/0.125f;
		}
		//Message.m(this, "xy "+(xo-x)+","+(yo-y));

		float t= z+0.2f;
		float t2=t+height;
		float t3=t2+bit;

		Texture.bind(0);

		GL11.glColor4f(Color.x,Color.y,Color.z,Color.vx);
		GL11.glBegin(GL11.GL_QUADS);

		boolean quad1=true;
		boolean quad2=true;
		boolean quad3=true;
		if(amount<0.375){
			GL11.glVertex3f(x,y,t3);
			GL11.glVertex3f(ex2, y,t3);
			GL11.glVertex3f(ex2,yo, t3);
			GL11.glVertex3f(xo, yo,t3);

			//side
			GL11.glVertex3f(ex2,y,t);
			GL11.glVertex3f(ex2, y,t3);
			GL11.glVertex3f(ex2,yo, t3);
			GL11.glVertex3f(ex2, yo,t);

			GL11.glVertex3f(xo,ey2,t3);
			GL11.glVertex3f(ex2, ey2,t3);
			GL11.glVertex3f(ex2,ey2, t);
			GL11.glVertex3f(xo, ey2,t);

		}else if(amount<0.625){
			GL11.glVertex3f(xo,yo,t3);
			GL11.glVertex3f(x, y,t3);
			GL11.glVertex3f(x,ey2, t3);
			GL11.glVertex3f(xo, ey2,t3);

			GL11.glVertex3f(x,y,t3);
			GL11.glVertex3f(ex2, y,t3);
			GL11.glVertex3f(ex2,ey2, t3);
			GL11.glVertex3f(x, ey2,t3);

			//side
			GL11.glVertex3f(sx2,yo,t);
			GL11.glVertex3f(sx2, yo,t3);
			GL11.glVertex3f(sx2,ey2, t3);
			GL11.glVertex3f(sx2, ey2,t);
			
			quad1=false;

		}else if(amount<0.875){
			GL11.glVertex3f(sx2,yo,t3);
			GL11.glVertex3f(xo, yo,t3);
			GL11.glVertex3f(x,y, t3);
			GL11.glVertex3f(sx2, y,t3);

			GL11.glVertex3f(sx2,y,t3);
			GL11.glVertex3f(ex2, y,t3);
			GL11.glVertex3f(ex2,ey2, t3);
			GL11.glVertex3f(sx2, ey2,t3);
			
			//side
			GL11.glVertex3f(sx2,sy2,t3);
			GL11.glVertex3f(xo, sy2,t3);
			GL11.glVertex3f(xo,sy2, t);
			GL11.glVertex3f(sx2, sy2,t);
			
			quad1=false;
			quad2=false;
		}else{
			GL11.glVertex3f(x,sy2,t3);
			GL11.glVertex3f(xo, sy2,t3);
			GL11.glVertex3f(xo,yo, t3);
			GL11.glVertex3f(x, y,t3);

			GL11.glVertex3f(sx2,y,t3);
			GL11.glVertex3f(ex2, y,t3);
			GL11.glVertex3f(ex2,ey2, t3);
			GL11.glVertex3f(sx2, ey2,t3);

			GL11.glVertex3f(sx2,y,t3);
			GL11.glVertex3f(x, y,t3);
			GL11.glVertex3f(x,sy2, t3);
			GL11.glVertex3f(sx2, sy2,t3);
			
			
			//side
			
			GL11.glVertex3f(ex2,yo,t);
			GL11.glVertex3f(ex2, yo,t3);
			GL11.glVertex3f(ex2,sy2, t3);
			GL11.glVertex3f(ex2, sy2,t);
			
			quad1=false;
			quad2=false;
			quad3=false;
	
		}

		if(!quad1){
			GL11.glVertex3f(ex2,y,t);
			GL11.glVertex3f(ex2, y,t3);
			GL11.glVertex3f(ex2,ey2, t3);
			GL11.glVertex3f(ex2, ey2,t);

			GL11.glVertex3f(sx2,ey2,t3);
			GL11.glVertex3f(ex2, ey2,t3);
			GL11.glVertex3f(ex2,ey2, t);
			GL11.glVertex3f(sx2, ey2,t);
			
			if(!quad2){
				GL11.glVertex3f(sx2,sy2,t);
				GL11.glVertex3f(sx2, sy2,t3);
				GL11.glVertex3f(sx2,ey2, t3);
				GL11.glVertex3f(sx2, ey2,t);
				
				if(!quad3){
					GL11.glVertex3f(sx2,sy2,t3);
					GL11.glVertex3f(ex2, sy2,t3);
					GL11.glVertex3f(ex2,sy2, t);
					GL11.glVertex3f(sx2, sy2,t);
				}
			}
		}

		GL11.glColor4f(Color2.x,Color2.y,Color2.z,Color2.vx);

		
		
		GL11.glVertex3f(sx,sy,t);
		GL11.glVertex3f(ex, sy,t);
		GL11.glVertex3f(ex,ey, t);
		GL11.glVertex3f(sx, ey,t);


		GL11.glVertex3f(sx,sy,t2);
		GL11.glVertex3f(ex, sy,t2);
		GL11.glVertex3f(ex,ey, t2);
		GL11.glVertex3f(sx, ey,t2);

		

		GL11.glVertex3f(sx,sy,t2);
		GL11.glVertex3f(ex, sy,t2);
		GL11.glVertex3f(ex,sy, t);
		GL11.glVertex3f(sx, sy,t);
		
		//if(quad2){

			GL11.glVertex3f(sx,sy,t);
			GL11.glVertex3f(sx, sy,t2);
			GL11.glVertex3f(sx,ey, t2);
			GL11.glVertex3f(sx, ey,t);
			
		//if(quad1){
			GL11.glVertex3f(sx,ey,t2);
			GL11.glVertex3f(ex, ey,t2);
			GL11.glVertex3f(ex,ey, t);
			GL11.glVertex3f(sx, ey,t);
		//}
		//}

		GL11.glVertex3f(ex,sy,t);
		GL11.glVertex3f(ex, sy,t2);
		GL11.glVertex3f(ex,ey, t2);
		GL11.glVertex3f(ex, ey,t);

		GL11.glEnd();
	}


	public String toString(){
		return super.toString()+","+Cap;
	}
	public void extra(String s){
		String st[]=s.split(",");
		
		gadgetDefine(st[0]);
		
		
		if(st.length>1){
			Entity e=Main.world.findE(st[1]);
			if(e!=null)
				this.setInput(e);
		}
	}
}
