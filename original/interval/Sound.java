package interval;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound {
	int coin=0;
	int synth=0;
	int bwom=0;
	int bwom2=0;
	int ocar1;
	int ocar2;
	int ocar3;
	public int menu=0;
	final int MAX_SOURCE=16;
	final int SAMPLE_RATE=44100;
	
	boolean loaded=false;

	HashMap<String,Integer[]> notes;
	HashMap<String,Integer> keys;
	HashMap<String,Integer> keyLengths;
	HashMap<Integer,Integer> occupy;
	HashMap<Integer,SoSource> sources;
	HashMap<String,Integer> reserve;

	ArrayList<Integer> buffers;

	int occupy2[];
	int hold[];
	int beeps[];

	int stream;
	SoStream streamSo;

	String notes2[];
	int ccc=0;

	public Integer[] i(int u,int k){
		return new Integer[]{u,k};
	}
	public void N(String note,int freq,int length){
		notes2[ccc]=note;
		ccc++;
		notes.put(note,new Integer[]{freq,length});
	}
	public void test(){
		this.runInstance(test);
	}

	public void m(Object s){
		Message.m(this, ""+s);
	}
	int test;
	int[] cache;
	
	
	
	public void init1(){
		notes2=new String[128];
		notes=new HashMap<String,Integer[]>();
		keys=new HashMap<String,Integer>();
		keyLengths=new HashMap<String,Integer>();
		occupy=new HashMap<Integer,Integer>();
		sources=new HashMap<Integer,SoSource>();
		buffers=new ArrayList<Integer>();
		reserve=new HashMap<String,Integer>();
		cache=new int[MAX_SOURCE];
		hold=new int[MAX_SOURCE];
		occupy2=new int[MAX_SOURCE];

		/*N("C0", 1635, 2100);
		N("C#0", 1732, 1990);
		N("D0", 1835, 1870);
		N("D#0", 1945, 1770);
		N("E0", 2060, 1670);
		N("F0", 2183, 1580);
		N("F#0", 2312, 1490);
		N("G0", 2450, 1400);
		N("G#0", 2596, 1320);
		N("A0", 2750, 1250);
		N("A#0", 2914, 1180);
		N("B0", 3087, 1110);
		N("C1", 3270, 1050);
		N("C#1", 3465, 996);
		N("D1", 3671, 940);
		N("D#1", 3889, 887);
		N("E1", 4120, 837);
		N("F1", 4365, 790);
		N("F#1", 4625, 746);
		N("G1", 4900, 704);
		N("G#1", 5191, 665);
		N("A1", 5500, 627);
		N("A#1", 5827, 592);
		N("B1", 6174, 559);
		N("C2", 6541, 527);
		N("C#2", 6930, 498);
		N("D2", 7342, 470);
		N("D#2", 7778, 444);
		N("E2", 8241, 419);
		N("F2", 8731, 395);
		N("F#2", 9250, 373);
		N("G2", 9800, 352);
		N("G#2", 10383, 332);
		N("A2", 11000, 314);
		N("A#2", 11654, 296);
		N("B2", 12347, 279);
		N("C3", 13081, 264);
		N("C#3", 13859, 249);
		N("D3", 14683, 235);
		N("D#3", 15556, 222);
		N("E3", 16481, 209);
		N("F3", 17461, 198);
		N("F#3", 18500, 186);
		N("G3", 19600, 176);
		N("G#3", 20765, 166);
		N("A3", 22000, 157);
		N("A#3", 23308, 148);
		N("B3", 24694, 140);
		N("C4", 26163, 132);
		N("C#4", 27718, 124);
		N("D4", 29366, 117);
		N("D#4", 31113, 111);
		N("E4", 32963, 105);
		N("F4", 34923, 988);
		N("F#4", 36999, 932);
		N("G4", 39200, 880);
		N("G#4", 41530, 831);
		N("A4", 44000, 784);
		N("A#4", 46616, 740);
		N("B4", 49388, 699);
		N("C5", 52325, 659);
		N("C#5", 55437, 622);
		N("D5", 58733, 587);
		N("D#5", 62225, 554);
		N("E5", 65926, 523);
		N("F5", 69846, 494);
		N("F#5", 73999, 466);
		N("G5", 78399, 440);
		N("G#5", 83061, 415);
		N("A5", 88000, 392);
		N("A#5", 93233, 370);
		N("B5", 98777, 349);
		N("C6", 104650, 330);
		N("C#6", 110873, 311);
		N("D6", 117466, 294);
		N("D#6", 124451, 277);
		N("E6", 131851, 262);
		N("F6", 139691, 247);
		N("F#6", 147998, 233);
		N("G6", 156798, 220);
		N("G#6", 166122, 208);
		N("A6", 176000, 196);
		N("A#6", 186466, 185);
		N("B6", 197553, 175);
		N("C7", 209300, 165);
		N("C#7", 221746, 156);
		N("D7", 234932, 147);
		N("D#7", 248902, 139);
		N("E7", 263702, 131);
		N("F7", 279383, 123);
		N("F#7", 295996, 117);
		N("G7", 313596, 110);
		N("G#7", 332244, 104);
		N("A7", 352000, 98);
		N("A#7", 372931, 93);
		N("B7", 395107, 87);
		N("C8", 418601, 82);
		N("C#8", 443492, 78);
		N("D8", 469864, 7);*/
	}
	public boolean init2(){
		try {
			Message.m(this, "create AL...");
			AL.create();
			Message.m(this, "finished AL.");
		} catch (LWJGLException e1) {
			Message.m(this, "AL failed!!");
			AL.destroy();
			Message.er(e1,false);
			
			return false;
		}
		

		IntBuffer source = BufferUtils.createIntBuffer(MAX_SOURCE+1);
		AL10.alGenSources(source);
		for(int i=0;i<MAX_SOURCE;i++)
			cache[i]=source.get(i);
		

		stream=source.get(MAX_SOURCE);


		IntBuffer buffer = buffer(5);
		AL10.alGenBuffers(buffer);
		coin=buffer.get(0);
		synth=buffer.get(1);
		bwom=buffer.get(2);
		bwom2=buffer.get(3);
		menu=buffer.get(4);

		AL10.alBufferData(synth, 
				AL10.AL_FORMAT_MONO16, 
				gen(10000,0.2,64,200,0.5f),  //gen(10000,0.2,64,200,0.5f), 
				20000);
		

		int L=10000;
		double Hz = 180; //440
		int  A = 32700; //maximum amplitude value for 16bit
		int SR = 22000;
		double FF =(2*Math.PI*Hz)/SR;

		IntBuffer O=BufferUtils.createIntBuffer(L);

		for (int T = 0; T < L; T ++)
			O.put(T,(int) Math.floor(A*Math.sin((double)(FF*T))));

		AL10.alBufferData(coin, 
				AL10.AL_FORMAT_MONO16, 
				O, 
				SR);

		AL10.alBufferData(menu, 
				AL10.AL_FORMAT_MONO16, 
				gen(300,0.15,64,92,60), 
				600);
		Beepize(10,11050,0,64,100,10,2,44100);


		IntBuffer bu = buffer(3);


		ocar1=bu.get(0);
		ocar2=bu.get(1);
		ocar3=bu.get(2);

		AL10.alBufferData(ocar1, 
				AL10.AL_FORMAT_MONO16, 
				gen(30000,0.1,64,600,0.5f), 
				20000);

		AL10.alBufferData(ocar2, 
				AL10.AL_FORMAT_MONO16, 
				gen(10000,64,200), 
				20000);

		AL10.alBufferData(ocar3, 
				AL10.AL_FORMAT_MONO16, 
				genS(10000,0,0.8,64,200,0.5f,0.5f), 
				20000);

		test=this.buffer();
		AL10.alBufferData(test, 
				AL10.AL_FORMAT_MONO16, 
				gen(10000,0.01,64,50), 
				20000);
		//TODO here
		//SoundFX.init();
		Noterize(0,200,0,64,50,5f);


		if (AL10.alGetError() != AL10.AL_NO_ERROR){
			//fucked();
			Message.er("SOUND IS FUCKED!!");
			return false;
		}
		/** Position of the source sound. */
		FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).flip();

		/** Velocity of the source sound. */
		FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).flip();

		
		for(int i=0;i<=MAX_SOURCE;i++){
			int ii=source.get(i);
			AL10.alSourcef(ii, AL10.AL_PITCH,    1.0f          );
			AL10.alSourcef(ii, AL10.AL_GAIN,     0.4f          );
			AL10.alSource(ii, 
					AL10.AL_POSITION, 
					sourcePos);
			AL10.alSource (ii, AL10.AL_VELOCITY, sourceVel     );

			sources.put(ii, new SoSource(ii));
		}
		streamSo=new SoStream(stream);
		loaded=true;
		return true;
	}
	
	double F=400;
	public void boop(){
		F+=16;
	}
	public void run(){
		/*streamSo.run();
		if(F>400){
			F-=8;
		}
		streamSo.enter(F);*/
		if(!loaded)
			return;
		for(SoSource so:sources.values()){
			if(so.run()){
				reserve.remove(so.getString());
			}
		}
	}
	public int science(ByteBuffer b,int rate){
		int buff=buffer();
		AL10.alBufferData(buff, 
				AL10.AL_FORMAT_MONO16, 
				b, 
				rate);
		return buff;
	}


	public void Beepize(int C,int SIZE,double cut,double amplitude,double maxOmega,double lowOmega,double wiggle,int Freq){
		IntBuffer buffer = buffer(C);
		beeps=new int[C];
		double R=1/(double)C;
		for(int u=0;u<C;u++){
			beeps[u]=buffer.get(u);
			double O=(R*(double)u)*(maxOmega-lowOmega) +lowOmega;
			O=Math.round(O/2)*2;
			Message.m(this,"O "+O);

			AL10.alBufferData(beeps[u], 
					AL10.AL_FORMAT_MONO16, 
					gen(SIZE,cut,amplitude,
							O
							),  //,wiggle
							Freq);
		}
	}
	//TODO noterize
	public void Noterize(int mode,double quality,double cut,double amplitude,double omega,double wiggle){
		String[] st=new String[notes.keySet().size()];
		notes.keySet().toArray(st);
		//keys=new int[st.length];
		IntBuffer buffer = buffer(st.length);
		AL10.alGenBuffers(buffer);
		int i=0;
		for(String s:st){
			Message.m(this,s);
			keys.put(s, buffer.get(i));
			Integer[] ii=notes.get(s);
			//Message.m(this,ii[0]+"/"+ii[1]+" "+(ii[0]/ii[1]));
			if(mode==0){
				omega=(double)ii[0]/(double)ii[1];
				omega=Math.round(omega);
				int sz=(ii[0]*ii[1])/300;
				if(sz%2==1){
					sz++;
				}
				Message.m(this, "sz "+sz+" r "+((sz*60)/SAMPLE_RATE));
				keyLengths.put(s, (sz*60)/SAMPLE_RATE);
				AL10.alBufferData(buffer.get(i), 
						AL10.AL_FORMAT_MONO16, 
						gen(sz ,cut,amplitude,omega,wiggle), //omega // (ii[1]*quality)
						SAMPLE_RATE); //(int) (ii[0]*quality) 
			}else{
				//double whoa=(double)ii[0]*(double)ii[1]/1000;
				omega=(double)ii[0]/(double)ii[1];
				omega=Math.round(omega);
				int sz=22050;

				keyLengths.put(s, (sz*60)/SAMPLE_RATE);
				AL10.alBufferData(buffer.get(i), 
						AL10.AL_FORMAT_MONO16, 
						genB(sz,amplitude,
								omega),//omega

								(SAMPLE_RATE));
			}
			i++;

			if (AL10.alGetError() != AL10.AL_NO_ERROR){
				Message.er("SOUND IS FUCKED!!");
				return;
			}
		}
	}
	
	//TODO noterize, this is literally just copy-pasta but with gen changed to genGradient, and 1 parameter change
		public void Noterize(int mode,double quality,double cut,double amplitude,double omega,float[] tones){
			String[] st=new String[notes.keySet().size()];
			notes.keySet().toArray(st);
			//keys=new int[st.length];
			IntBuffer buffer = buffer(st.length);
			AL10.alGenBuffers(buffer);
			int i=0;
			for(String s:st){
				Message.m(this,s);
				keys.put(s, buffer.get(i));
				Integer[] ii=notes.get(s);
				//Message.m(this,ii[0]+"/"+ii[1]+" "+(ii[0]/ii[1]));
				if(mode==0){
					omega=(double)ii[0]/(double)ii[1];
					omega=Math.round(omega);
					int sz=(ii[0]*ii[1])/300;
					if(sz%2==1){
						sz++;
					}
					Message.m(this, "sz "+sz+" r "+((sz*60)/SAMPLE_RATE));
					keyLengths.put(s, (sz*60)/SAMPLE_RATE);
					AL10.alBufferData(buffer.get(i), 
							AL10.AL_FORMAT_MONO16, 
							genGradient(sz ,cut,amplitude,omega,tones), //omega // (ii[1]*quality)
							SAMPLE_RATE); //(int) (ii[0]*quality) 
				}else{
					//double whoa=(double)ii[0]*(double)ii[1]/1000;
					omega=(double)ii[0]/(double)ii[1];
					omega=Math.round(omega);
					int sz=22050;

					keyLengths.put(s, (sz*60)/SAMPLE_RATE);
					AL10.alBufferData(buffer.get(i), 
							AL10.AL_FORMAT_MONO16, 
							genB(sz,amplitude,
									omega),//omega

									(SAMPLE_RATE));
				}
				i++;

				if (AL10.alGetError() != AL10.AL_NO_ERROR){
					Message.er("SOUND IS FUCKED!!");
					return;
				}
			}
		}


	public void beep(int i){
		//Message.m(this, "c"+i);
		runInstance(beeps[i]);
	}
	public void beep(int i,int length){
		runInstanceL(beeps[i],length);
	}
	public void playNote(String s){
		playNote(s,10);
	}
	/*public void playNote(String s,int length){
		Message.addPass(s);
		runInstanceL(keys.get(s),length);
	}*/
	public void playNote(int u){
		playNote(notes2[u]);
	}
	public void playNote(int u,int length){
		playNote(notes2[u],length);
	}
	public void playNote(String s,int length){
		Integer K=keys.get(s);
		if(K==null){
			Message.m(this,s+" note doesnt exist??");
			return;
			//K=-1;
		}

		Integer ii=reserve.get(s);
		Message.m(this," "+ii);
		//if(ii==null){
			ii=free3();
			reserve.put(s, ii);
		//}

		SoSource so=sources.get(ii);
		

		so.setSound(K);
		so.setString(s);
		so.setKeyLength(keyLengths.get(s));
		so.setHeld(length);
		so.refresh();

	}
	public void runInstance(int buffer){
		int f=free3();
		runInstance(buffer,f);
	}
	static FloatBuffer pos=(FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).flip();

	public void runInstance(int buffer,float[] f){
		
		int i=free();
		AL10.alSource(i, 
				AL10.AL_POSITION, 
				(FloatBuffer) pos.put(f).flip());
		
		AL10.alSourcef(i, AL10.AL_GAIN,1);
		
		
		runInstance(buffer,i);
	}

	public void runInstance(int buffer,float x,float y,float z){
		runInstance(buffer,new float[]{x,y,z});
	}
	
	public void runInstanceL(int buffer,int length){
		Integer in=occupy.get(buffer);
		if(in!=null){
			hold[in]=length;
		}else{
			int[] f=free2();
			runInstance(buffer,f[0]);
			hold[f[1]]=length;
			occupy2[f[1]]=buffer;
			occupy.put(buffer, f[1]);
			AL10.alSourcei(f[0],AL10.AL_LOOPING,1);
		}
	}
	public void runInstance(int buffer,int source){
		Message.m(this, "play direct: "+buffer);
		AL10.alSourcef(source, AL10.AL_PITCH,1.0f);
		AL10.alSourcei(source, AL10.AL_BUFFER,   buffer );
		AL10.alSourcef(source, AL10.AL_GAIN,1);
		AL10.alSourcePlay(source);
	}

	public void runInstance(int buffer,float pitch){
		int f=free();
		AL10.alSourcef(f, AL10.AL_PITCH,pitch);
		AL10.alSourcei(f, AL10.AL_BUFFER,   buffer );
		AL10.alSourcef(f, AL10.AL_GAIN,1);
		AL10.alSourcePlay(f);
	}

	int pick=0;
	public int free(){
		for(int j:cache){
			if(AL10.alGetSourcei(j, AL10.AL_SOURCE_STATE)==AL10.AL_STOPPED){
				return j;
			}
		}
		//Message.addPass("desperate");
		pick++;
		if(pick>=MAX_SOURCE){
			pick=0;
		}
		return cache[pick];
	}
	public int[] free2(){
		int ii=0;
		for(int j:cache){
			if(AL10.alGetSourcei(j, AL10.AL_SOURCE_STATE)==AL10.AL_STOPPED){
				return new int[]{j,ii};
			}
			ii++;
		}
		pick++;
		if(pick>=MAX_SOURCE){
			pick=0;
		}
		return new int[]{cache[pick],pick};
	}
	
	public int free3(){
		
		for(int j:cache){
			if(!reserve.values().contains(j)  && AL10.alGetSourcei(j, AL10.AL_SOURCE_STATE)==AL10.AL_STOPPED){//){
				return j;
			}
		}
		//Message.addPass("desperate");
		pick++;
		if(pick>=MAX_SOURCE){
			pick=0;
		}
		return cache[pick];
	}

	public int buffer(){
		return buffer(1).get(0);
	}
	public IntBuffer buffer(int u){
		IntBuffer bu = BufferUtils.createIntBuffer(u);
		AL10.alGenBuffers(bu);
		for(int y=0;y<u;y++){
			buffers.add(bu.get(y));
		}
		return bu;
	}
	public ByteBuffer gen(int SIZE,double amplitude,double omega){
		ByteBuffer bit=BufferUtils.createByteBuffer(SIZE);
		double a=0;
		for(int u=0;u<SIZE;u++){
			double d=((double)u/(double)SIZE);
			a=omega*Math.PI*d;
			bit.put(u,(byte) (amplitude*Math.sin(a)));
		}
		return bit;
	}

	public ByteBuffer gen(int SIZE,double cut,double amplitude,double omega,double wiggle){
		ByteBuffer bit=BufferUtils.createByteBuffer(SIZE);
		double a=0;
		double phaseIn=0;
		if(cut==0)
			phaseIn=1;
		double phaseOut=1;
		double buffer1=cut;
		double buffer2=1-cut;
		int buffer11=(int) (buffer1*SIZE);
		int buffer22=(int) (buffer2*SIZE);
		double bRate1=(1/(double)buffer11);
		double bRate2=(1/(double)((1-buffer2)*SIZE));
		for(int u=0;u<SIZE;u++){
			double d=((double)u/(double)SIZE);
			a=2*omega*Math.PI*d;
			double dd=wiggle*Math.PI*d;
			if(u<buffer11){
				phaseIn+=bRate1;
			}
			if(u>buffer22){
				phaseOut-=bRate2;
			}
			double w;
			
			if(wiggle==0)
				w=1;
			else
				w=Math.sin(dd);
			bit.put(u,(byte) (phaseOut*phaseIn*amplitude*Math.sin(a)*w));
		}
		return bit;
	}

	public ByteBuffer genGradient(int SIZE,double cut,double amplitude,double omega,float[] tones){
		ByteBuffer bit=BufferUtils.createByteBuffer(SIZE);
		boolean g=cut==0;
		double a=0;
		double phaseIn=0;
		double phaseOut=1;
		double buffer1=cut;
		double buffer2=1-cut;
		int buffer11=(int) (buffer1*SIZE);
		int buffer22=(int) (buffer2*SIZE);
		double bRate1=(1/(double)buffer11);
		double bRate2=(1/(double)((1-buffer2)*SIZE));
		if(g){
			phaseIn=1;
		}
		int tock=SIZE/(tones.length-1);
		int tick=0;
		int index=0;
		for(int u=0;u<SIZE;u++){
			double d=((double)u/(double)SIZE);
			if(!g){
				if(u<buffer11){
					phaseIn+=bRate1;
				}
				if(u>buffer22){
					phaseOut-=bRate2;
				}
			}
			tick++;
			if(tick>tock){
				index++;
				tick=0;
			}
			double h=(double) tick/(double)tock;
			double derk=tones[index]+(tones[index+1]-tones[index])*h;
			//Message.m(this,derk+","+u);
			a=omega*Math.PI*d*derk;
			bit.put(u,(byte) (phaseOut*phaseIn*amplitude*Math.sin(a)));
		}
		return bit;
	}
	public ByteBuffer genS(int SIZE,double cut1,double cut2,double amplitude,double omega,double wiggle,double wOff){
		ByteBuffer bit=BufferUtils.createByteBuffer(SIZE);
		double a=0;
		double phaseIn=0;
		double phaseOut=1;
		double buffer1=cut1;
		double buffer2=cut2;
		int buffer11=(int) (buffer1*SIZE);
		int buffer22=(int) (buffer2*SIZE);
		double bRate1=(1/(double)buffer11);
		double bRate2=(1/(double)((1-buffer2)*SIZE));
		for(int u=0;u<SIZE;u++){
			double d=((double)u/(double)SIZE);
			a=(omega*d)*Math.PI;
			double dd=(wOff+wiggle*d)*Math.PI;
			if(u<buffer11){
				phaseIn+=bRate1;
			}
			if(u>buffer22){
				phaseOut-=bRate2;
			}
			bit.put(u,(byte) (phaseOut*phaseIn*amplitude*Math.sin(a)*Math.sin(dd)));
		}
		return bit;
	}

	public ByteBuffer gen(int SIZE,double cut,double amplitude,double omega,double wiggle,double varied){
		ByteBuffer bit=BufferUtils.createByteBuffer(SIZE);
		boolean g=cut==0;
		double a=0;
		double phaseIn=0;
		double phaseOut=1;
		double buffer1=cut;
		double buffer2=1-cut;
		int buffer11=(int) (buffer1*SIZE);
		int buffer22=(int) (buffer2*SIZE);
		double bRate1=(1/(double)buffer11);
		double bRate2=(1/(double)((1-buffer2)*SIZE));
		if(g){
			phaseIn=1;
		}
		for(int u=0;u<SIZE;u++){
			double d=((double)u/(double)SIZE);
			a=omega*Math.PI*d;
			double dd=wiggle*Math.PI*d;
			if(!g){
				if(u<buffer11){
					phaseIn+=bRate1;
				}
				if(u>buffer22){
					phaseOut-=bRate2;
				}
			}
			bit.put(u,(byte) (phaseOut*phaseIn*amplitude*Math.sin(a)*Math.sin(dd)*Math.sin(varied*Math.PI*d)));
		}
		return bit;
	}

	public ByteBuffer gen(int SIZE,double cut,double amplitude,double omega){
		ByteBuffer bit=BufferUtils.createByteBuffer(SIZE);
		boolean g=cut==0;
		double a=0;
		double phaseIn=0;
		double phaseOut=1;
		double buffer1=cut;
		double buffer2=1-cut;
		int buffer11=(int) (buffer1*SIZE);
		int buffer22=(int) (buffer2*SIZE);
		double bRate1=(1/(double)buffer11);
		double bRate2=(1/(double)((1-buffer2)*SIZE));
		if(g){
			phaseIn=1;
		}
		for(int u=0;u<SIZE;u++){
			double d=((double)u/(double)SIZE);
			a=omega*Math.PI*d;
			//double dd=wiggle*Math.PI*d;
			if(!g){
				if(u<buffer11){
					phaseIn+=bRate1;
				}
				if(u>buffer22){
					phaseOut-=bRate2;
				}
			}
			double n=Math.sin(a);
			if(n>0.5)n=0.5;
			else if(n<-0.5)n=-0.5;

			bit.put(u,(byte) (phaseOut*phaseIn*amplitude*n));
		}
		return bit;
	}

	public ByteBuffer genB(int SIZE,double amplitude,double omega){
		ByteBuffer bit=BufferUtils.createByteBuffer(SIZE);
		double a=0;
		for(int u=0;u<SIZE;u++){
			double d=((double)u/(double)SIZE);
			a=omega*Math.PI*d;
			double n=Math.sin(a);
			if(n>0)n=1;
			else n=-1;
			//if(n>0.5)n=0.5;
			//else if(n<-0.5)n=-0.5;
			//else n=0;
			bit.put(u,(byte) (amplitude*n));
		}
		return bit;
	}

	public int apply(ByteBuffer b,int sr){
		int key=buffer();
		AL10.alBufferData(key, 
				AL10.AL_FORMAT_MONO16, 
				b, 
				sr);
		return key;
	}

	public int load(String s){
		String ex="wav";
		if(s.contains(".")){
			ex=s.substring(s.indexOf(".")+1);
		}
		int key=0;
		try {
			WaveData w=WaveData.create(new FileInputStream(FileManager.loadSound(s, ex)));
			key=buffer();
			AL10.alBufferData(key, 
					w.format, 
					w.data, 
					w.samplerate);
			byte[] bb=new byte[w.data.capacity()];
			w.data.get(bb);
			String ss="";
			int u=0;
			for(byte b:bb){
				ss+=b+",";
				if(++u>100){u=0;
				Message.m(this,ss);
				ss="";
				}
			}
			w.dispose();
		} catch (FileNotFoundException e) {
			Message.er(e);
		}
		return key;
	}

	public int findNote(String s){
		int u=0;
		for(String t:notes2){
			if(t!=null &&t.equals(s))
				return u;
			u++;
		}
		return -1;
	}

	public void end(){
		for(int c:cache){
			AL10.alDeleteSources(c);
		}
		AL10.alDeleteSources(stream);
		for(int bu:buffers)
			AL10.alDeleteBuffers(bu);
		AL.destroy();
	}
}
