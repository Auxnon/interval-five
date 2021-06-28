package interval;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class SoSource {
	
	int KEY;
	private boolean reserved=false;
	private int held=0;
	int max=60;

	private String occupiedNote="";
	private int occupiedKey=0;
	float GAIN=1f;
	
	private int occupiedKeyLength=0;
	private int fade=-1;
	private int fadeRate=60;
	
	public int getKeyLength() {
		return occupiedKeyLength;
	}
	public void setKeyLength(int occupiedKeyLength) {
		this.occupiedKeyLength = occupiedKeyLength;
	}

	FloatBuffer pos;
	public SoSource(int so){
		KEY=so;
		
		pos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).flip();

	}
	public void setHeld(int h){
		reserved=true;
		held=h;
	}
	public void setString(String i){
		occupiedNote=i;
	}
	//final static float[] zero=new float[]{0,0,0};
	public void setSound(int i){
		occupiedKey=i;
		m("play source: "+occupiedKey);
		AL10.alSourceUnqueueBuffers(KEY);
		AL10.alSourceQueueBuffers(KEY, occupiedKey);
	}
	public void setSound(int i,float[] f){
		
		occupiedKey=i;
		m("play source: "+occupiedKey);
		pos.put(f).flip();
		AL10.alSource(KEY, 
				AL10.AL_POSITION, 
				pos);
		AL10.alSourceUnqueueBuffers(KEY);
		AL10.alSourceQueueBuffers(KEY, occupiedKey);
	}
	public int getHeld(){
		return held;
	}
	/*public boolean stringExists(){
		return !occupiedNote.equals("");
	}*/
	public void refresh(){
		AL10.alSourcef(KEY, AL10.AL_GAIN,GAIN);
	}
	public String getString(){
		return occupiedNote;
	}
	
	public boolean run(){
	
		if(reserved){
			if(held>0){
				held--;
				int pro=AL10.alGetSourcei(KEY, AL10.AL_BUFFERS_PROCESSED);
				//m("processed "+pro);
				while(pro-->0){
					//m("--cleared "+pro);
					AL10.alSourceUnqueueBuffers(KEY);
				}
				int que=AL10.alGetSourcei(KEY, AL10.AL_BUFFERS_QUEUED);
				int occupied=(que-pro);
				//m("occupied "+occupied);
				if((occupied-1)*occupiedKeyLength<max){
					AL10.alSourceQueueBuffers(KEY, occupiedKey);
				}
				if(AL10.alGetSourcei(KEY, AL10.AL_SOURCE_STATE)!=AL10.AL_PLAYING){
					//m("restart sound source");
					AL10.alSourcePlay(KEY);
					m("play source: "+occupiedKey);
				}
			}else if(fade>=0){
				float G=(GAIN*fade)/(float)fadeRate;
				AL10.alSourcef(KEY, AL10.AL_GAIN,G);
				//m("fade "+fade+" gain "+G);
				fade--;
				if(fade<=0){
					int que=AL10.alGetSourcei(KEY, AL10.AL_BUFFERS_QUEUED);
					while(que-->0){
						AL10.alSourceUnqueueBuffers(KEY);
					}
					fade=-2;
					reserved=false;
					//m("stopped");
					AL10.alSourceStop(KEY);
					return true;
				}
			}else{
				fade=fadeRate;
				//m("undo");
				
			}
		}
		return false;
	}
	public boolean isReserved() {
		return reserved;
	}
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	public void m(String s){
		Message.m(this, KEY+": "+s);
	}

}
