package interval;

import org.lwjgl.opengl.GL11;

public class Vox {

	float sx,sy,sz;
	float V[];
	float t[];
	
	Vec ver;
	
	static float N[]={-1,-1,1,
		1,-1,1,
		1,1,1,
		-1,1,1,
		
		-1,-1,-1,
		1,-1,-1,
		1,1,-1,
		-1,1,-1
	};
	
	public Vox(){
		V=new float[6]; //24
		t=new float[24];
		polish();
	}

	
	public Vox(float[] v,float[] T){
		this();
		for(int h=0;h<6;h++){
			V[h]=v[h];
		}
		
		for(int h=0;h<24;h++){
			t[h]=T[h];
		}
	}
	public void apply(){
		
			
			GL11.glNormal3f(1,0,0);

			GL11.glTexCoord2f(t[0], t[1]);
			GL11.glVertex3f(V[0],V[4],V[2]);
			
			GL11.glTexCoord2f(t[2], t[1]);
			GL11.glVertex3f(V[0],V[1],V[2]);
			
			GL11.glTexCoord2f(t[2], t[3]);
			GL11.glVertex3f(V[0],V[1],V[5]);
			
			GL11.glTexCoord2f(t[0], t[3]);
			GL11.glVertex3f(V[0],V[4],V[5]);
			
			
			GL11.glNormal3f(0,-1,0);
			
			GL11.glTexCoord2f(t[4], t[5]);
			GL11.glVertex3f(V[0],V[1],V[2]);
			
			GL11.glTexCoord2f(t[6], t[5]);
			GL11.glVertex3f(V[3],V[1],V[2]);
			
			GL11.glTexCoord2f(t[6], t[7]);
			GL11.glVertex3f(V[3],V[1],V[5]);
			
			GL11.glTexCoord2f(t[4], t[7]);	
			GL11.glVertex3f(V[0],V[1],V[5]);
			
			
			
			
			
			
			
			
			
			GL11.glNormal3f(-1,0,0);
			GL11.glTexCoord2f(t[8], t[9]);
			GL11.glVertex3f(V[3],V[4],V[2]);
			
			GL11.glTexCoord2f(t[10], t[9]);
			GL11.glVertex3f(V[3],V[1],V[2]);
			
			GL11.glTexCoord2f(t[10], t[11]);
			GL11.glVertex3f(V[3],V[1],V[5]);
			
			GL11.glTexCoord2f(t[8], t[11]);
			GL11.glVertex3f(V[3],V[4],V[5]);
			
			
			
			GL11.glNormal3f(0,1,0);//x-1
			GL11.glTexCoord2f(t[12], t[13]);
			GL11.glVertex3f(V[0],V[4],V[2]);
			
			GL11.glTexCoord2f(t[14], t[13]);
			GL11.glVertex3f(V[3],V[4],V[2]);
			
			GL11.glTexCoord2f(t[14], t[15]);
			GL11.glVertex3f(V[3],V[4],V[5]);
			
			GL11.glTexCoord2f(t[12], t[15]);
			GL11.glVertex3f(V[0],V[4],V[5]);
			
			
			GL11.glNormal3f(0,0,1);
			GL11.glTexCoord2f(t[16], t[17]);
			GL11.glVertex3f(V[0],V[4],V[2]);
			
			
			GL11.glTexCoord2f(t[18], t[17]);
			GL11.glVertex3f(V[3],V[4],V[2]);
			
			GL11.glTexCoord2f(t[18], t[19]);
			GL11.glVertex3f(V[3],V[1],V[2]);
			
			
			GL11.glTexCoord2f(t[16], t[19]);
			GL11.glVertex3f(V[0],V[1],V[2]);
			
			
			
			
			GL11.glNormal3f(0,0,-1);
			GL11.glTexCoord2f(t[20], t[21]);
			GL11.glVertex3f(V[3],V[4],V[5]);
			
			GL11.glTexCoord2f(t[22], t[21]);
			GL11.glVertex3f(V[0],V[4],V[5]);
			
			GL11.glTexCoord2f(t[22], t[23]);
			GL11.glVertex3f(V[0],V[1],V[5]);
			
			GL11.glTexCoord2f(t[20], t[23]);
			GL11.glVertex3f(V[3],V[1],V[5]);
	}
	
	public void applyD(float v,float H){
		GL11.glPushMatrix();
		float vv=-0.9f*v*v/2f;

		float xx=ver.x*v;
		float yy=ver.y*v;
		float zz=vv+(ver.z+H)*v;

		GL11.glTranslatef(xx, yy, zz);
		GL11.glBegin(GL11.GL_QUADS);
		apply();
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
	public void polish(){
		ver = new Vec((Math.random()*2)-1f,(Math.random()*2)-1f,(Math.random()*2)-0.4f);
	}

}
