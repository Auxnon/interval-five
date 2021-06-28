package interval;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class SoStream {
	int KEY;
	int buffers[];
	final int bSize=16;
	int IN;
	int OUT;

	double value;
	public SoStream(int i){
		buffers=new int[bSize];
		IntBuffer bu = BufferUtils.createIntBuffer(bSize);
		AL10.alGenBuffers(bu);
		bu.get(buffers);
		OUT=0;
		KEY=i;
	}
	int SIZE=200;
	double hertz=1100;
	int  Amp = 32700;
	int max=4;
	int occupied;
	int sampleRate=44100;
	int X=0;
	long startTime;

	public void run(){
		long estimatedTime = System.nanoTime() - startTime;
		startTime = System.nanoTime();

		int ssz=(int)Math.floor(sampleRate*estimatedTime*0.000000001);///(1+occupied/5);
		if(ssz>sampleRate)
			ssz=sampleRate;

		IntBuffer bit=BufferUtils.createIntBuffer(ssz);
		double FF =(2*Math.PI)/sampleRate;

		//Message.m(this, "e "+estimatedTime);
		int XX=X;
		double k=(value-hertz)/ssz;
		double her=hertz;
		for(int u=0;u<ssz;u++){
			hertz+=k;
			bit.put(u,(int) Math.floor(Amp*Math.sin((double)(FF*X*hertz))));
			X++;
		}

		if(X>=sampleRate){
			X-=sampleRate;
		}

		AL10.alBufferData(buffers[IN], 
				AL10.AL_FORMAT_MONO16, 
				bit, 
				sampleRate);

		int pro=AL10.alGetSourcei(KEY, AL10.AL_BUFFERS_PROCESSED);
		//m("processed "+pro);
		while(pro-->0){
			//m("--cleared "+pro);
			AL10.alSourceUnqueueBuffers(KEY);
		}

		int que=AL10.alGetSourcei(KEY, AL10.AL_BUFFERS_QUEUED);
		occupied=(que-pro);
		//m("occupied "+occupied);

		if((occupied-1)<max){
			AL10.alSourceQueueBuffers(KEY, buffers[IN]);
		}else{
			X=XX;
			hertz=her;
		}

		if(AL10.alGetSourcei(KEY, AL10.AL_SOURCE_STATE)!=AL10.AL_PLAYING){
			m("restart sound source");
			AL10.alSourcePlay(KEY);
		}

		IN++;
		if(IN>=bSize){
			IN=0;
		}
	}
	public void enter(double f){
		value=f;
	}
	public void m(String s){
		Message.m(this, buffers[IN]+": "+s);
	}
}
