package interval;

public class Spin {

	private static float dx[];
	private static float dy[];
	
	final static int Factor=2;
	static {

		int N=360*Factor;
		
		dx=new float[N];
		dy=new float[N];
		for(int i=0;i<N;i++){
			float nn=(float)i/(float)Factor;
			float nx=(float) (Math.cos(Math.toRadians(nn))/2f);
			float ny=(float) Math.sin(Math.toRadians(nn))/2f;
			
			dx[i]=ny-nx;
			dy[i]=-ny-nx;
		}
		System.out.println("spin data initialized");
	}
	public Spin(){
		
	}
	
	public static float[] get(float spin){
		int I=(int)(spin*Factor);
		return new float[]{dx[I],dy[I]};
	}
}
