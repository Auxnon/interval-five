package interval;

public class ModelTerraBuffer {

	public int v;
	public int n;
	public int c;
	public int faces;
	public int v2;
	public int ibb;
	public float V[];
	public float C[];
	public float N[];
	boolean used=false;
	public ModelTerraBuffer(){
		
	}
	public ModelTerraBuffer(int v,int n,int faces){
		this.v=v;
		this.n=n;
		this.faces=faces;
	}
	public ModelTerraBuffer(int v,int n,int c,int faces){
		this.v=v;
		this.n=n;
		this.c=c;
		this.faces=faces;
	}
}
