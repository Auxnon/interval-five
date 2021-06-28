package interval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.ARBGeometryShader4;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

public class Shader {
	public int shader=0;
	private int vertShader=0;
	private int fragShader=0;
	//private int geomShader=0;
	private int posShader;
	private int rotShader;
	private int conversionShader;
	private int clipShader;
	private int onShader;
	private int normShader;
	private int warpShader;
	private int warpMShader;

	int indicingShader;

	private int matShader;
	private int lightShader;

	public Shader(){
		shader=ARBShaderObjects.glCreateProgramObjectARB();
		if(shader!=0){
			vertShader=createShader("./data/screen.vert",0);
			fragShader=createShader("./data/screen.frag",1);
			//geomShader=createShader("./data/G.glsl",2);
		}
		
		if(vertShader !=0){
			ARBShaderObjects.glAttachObjectARB(shader, vertShader);
		}
		
		if(fragShader !=0){
			ARBShaderObjects.glAttachObjectARB(shader, fragShader);
		}
		
		//if(geomShader !=0){
		//	ARBGeometryShader4.glProgramParameteriARB(geomShader,ARBGeometryShader4.GL_GEOMETRY_INPUT_TYPE_ARB,GL11.GL_TRIANGLES);
		//	ARBGeometryShader4.glProgramParameteriARB(geomShader,ARBGeometryShader4.GL_GEOMETRY_OUTPUT_TYPE_ARB,GL11.GL_TRIANGLE_STRIP);
		//	ARBGeometryShader4.glProgramParameteriARB(geomShader,ARBGeometryShader4.GL_GEOMETRY_VERTICES_OUT_ARB,8);
		//	ARBShaderObjects.glAttachObjectARB(shader, geomShader);
		//}
		 

		ARBShaderObjects.glLinkProgramARB(shader);
		ARBShaderObjects.glValidateProgramARB(shader);
		printLogInfo(shader,3);
		
		posShader= ARBShaderObjects.glGetUniformLocationARB(shader, "ppos");
		rotShader= ARBShaderObjects.glGetUniformLocationARB(shader, "rrot");
		//modeShader= ARBShaderObjects.glGetUniformLocationARB(shader, "mode");
		onShader= ARBShaderObjects.glGetUniformLocationARB(shader, "on");
		conversionShader= ARBShaderObjects.glGetUniformLocationARB(shader, "season");
		clipShader= ARBShaderObjects.glGetUniformLocationARB(shader, "clip");
		indicingShader= ARBShaderObjects.glGetUniformLocationARB(shader, "centres");
		warpShader= ARBShaderObjects.glGetUniformLocationARB(shader, "warpPos");
		warpMShader= ARBShaderObjects.glGetUniformLocationARB(shader, "warpMode");
		normShader=ARBShaderObjects.glGetUniformLocationARB(shader, "normOn");
		matShader=ARBShaderObjects.glGetUniformLocationARB(shader, "model_matrix");
		lightShader=ARBShaderObjects.glGetUniformLocationARB(shader, "lightOn");
	}



	private int createShader(String filename,int m){
		String n="";
		int I=0;
		switch(m){
		case 1: I=ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		n="fragment";break;
		case 2: I=ARBShaderObjects.glCreateShaderObjectARB(ARBGeometryShader4.GL_GEOMETRY_SHADER_ARB);
		n="geometry";break;
		default: I=ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
		n="vertex";break;
		}
		if(I==0){Message.m(this,"Failed Creating "+n+" Shader");
			return 0;}

		String fragCode="";
		String line;
		try{
			BufferedReader reader=new BufferedReader(new FileReader(filename));
			while((line=reader.readLine())!=null){
				
				fragCode+=line + "\n";
			}
		}catch(Exception e){
			
			Message.m(this,"Failed reading "+n+" shading code");
			UserData.shaderFail();
			return 0;
		}
		Message.m(this,"inputting "+I);
		ARBShaderObjects.glShaderSourceARB(I, fragCode);
		ARBShaderObjects.glCompileShaderARB(I);
		if(!printLogInfo(I,m)){
			//TODO originally this was a good practice for finding errors, 
			//but currently it links shaders at 0 when there are none, thus getting us an untraceable runtime error.
			//I=0;
		}
		Message.m(this,"returning "+I);
		return I;
	}

	private static boolean printLogInfo(int obj,int m){
		IntBuffer iVal = BufferUtils.createIntBuffer(1);
		ARBShaderObjects.glGetObjectParameterARB(obj,
				ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, iVal);

		
		int length = iVal.get();
		if (length > 1) {
			ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
			iVal.flip();
			ARBShaderObjects.glGetInfoLogARB(obj, iVal, infoLog);
			byte[] infoBytes = new byte[length];
			infoLog.get(infoBytes);
			String out = new String(infoBytes);
			String outout ="Info log:\n"+out;
			Message.m(Shader.class,outout);
			if(outout.contains("error")){
				if(m==0)
					UserData.shaderFail();
				else if(m==1)
					UserData.setShaderMode(2);
			}
			Message.addPass(outout);
		}else return false;
		return true;
	}

	public void start(){
		if(UserData.canShader())
			ARBShaderObjects.glUseProgramObjectARB(shader);	 //shader
		//ARBShaderObjects.glUniform1iARB(modeShader, 0);
	}
	
	/*public void testPrim(){
		//float sz=64;
		
		GL11.glColor4f(1,0,0,1);
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

		//Render.shader.lightOff();
		
		
		GL11.glVertex3f(0f,0,5);
		GL11.glVertex3f(-900,0,5);
		
		GL11.glVertex3f(0f,-900,5);
		GL11.glVertex3f(-900f,-900,5);
		GL11.glEnd();
	}*/

	public void startLight(){
		ARBShaderObjects.glUniform2fARB(clipShader, Main.level.getStats().getClip1(),Main.level.getStats().getClip2());
		ARBShaderObjects.glUniform1fARB(conversionShader, Main.world.timeline.getSeason());
	}
	public void endLight(){

	}
	public void startGrass(){
		//ARBShaderObjects.glUniform1iARB(modeShader, 2);
	}
	public void endGrass(){
		//ARBShaderObjects.glUniform1iARB(modeShader, 0);
	}

	public void mat(FloatBuffer m){
		ARBShaderObjects.glUniform1iARB(onShader, 1);
		ARBShaderObjects.glUniformMatrix4ARB(matShader, false, m);
	}

	public void warp(float x,float y,float z,float i,int m){
		ARBShaderObjects.glUniform4fARB(warpShader, x,y,z,i);
		ARBShaderObjects.glUniform1iARB(warpMShader,m);
	}

	public void autoNormOn(){
		ARBShaderObjects.glUniform1iARB(normShader, 1);
	}
	public void autoNormOff(){
		ARBShaderObjects.glUniform1iARB(normShader, 0);
	}
	public void normal(){
		ARBShaderObjects.glUniform1iARB(onShader, 0);
	}
	public void lightOn(){
		ARBShaderObjects.glUniform1iARB(lightShader, UserData.getShaderMode());
	}
	public void lightOff(){
		ARBShaderObjects.glUniform1iARB(lightShader, 2);
	}
	public void coordinate(float i,float j,float k){
		ARBShaderObjects.glUniform1iARB(onShader, 2);
		ARBShaderObjects.glUniform3fARB(posShader, i, j, k);
	}
	public void coordinateEntity(Matrix4f mat,float r){
		ARBShaderObjects.glUniform1iARB(onShader, 1);
		ARBShaderObjects.glUniformMatrix4ARB(matShader, false, mat2buff(mat));
		double A=Math.toRadians(r);
		float fx=(float)Math.cos(A);
		if(Math.abs(fx)<0.0001){
			fx=0;
		}
		float fy=(float)Math.sin(A);
		if(Math.abs(fy)<0.0001){
			fy=0;
		}
		ARBShaderObjects.glUniform3fARB(rotShader, fx, -fy,0); //fx,-fy
	}
	/*	public void coordinateEntity(boolean b,float i,float j,float k,double A){
		if(b){
			ARBShaderObjects.glUniform1iARB(modeShader, 1);
			//ARBShaderObjects.glUniform1iARB(onShader, 1);
			ARBShaderObjects.glUniform3fARB(posShader, i, j, k);

			float fx=(float)Math.cos(A);
			if(Math.abs(fx)<0.0001){
				fx=0;
			}
			float fy=(float)Math.sin(A);
			if(Math.abs(fy)<0.0001){
				fy=0;
			}
			ARBShaderObjects.glUniform3fARB(rotShader, fx, -fy,0); //fx,-fy
		}else{
			ARBShaderObjects.glUniform1iARB(modeShader, 0);
			ARBShaderObjects.glUniform1iARB(onShader, 0);
		}
	}*/
	public void end(){
		if(UserData.canShader())
			ARBShaderObjects.glUseProgramObjectARB(0);
	}

	/*public boolean useShader(){
		return useShader;
	}*/
	public int getShader(){
		return shader;
	}

	public static FloatBuffer mat2buff(Matrix4f m){
		FloatBuffer v =  BufferUtils.createFloatBuffer(16);
		v.put(0, m.m00);
		v.put(4, m.m10);
		v.put(8, m.m20);
		v.put(12, m.m30);

		v.put(1, m.m01);
		v.put(5, m.m11);
		v.put(9, m.m21);
		v.put(13, m.m31);

		v.put(2, m.m02);
		v.put(6, m.m12);
		v.put(10, m.m22);
		v.put(14, m.m32);

		v.put(3, m.m03);
		v.put(7, m.m13);
		v.put(11, m.m23);
		v.put(15, m.m33);
		return v;
	}

}