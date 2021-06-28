package interval;

public class ModelTerraReference {

	public int v;
	public int n;
	public int c;
	public int faces;
	
	public int v2;
	//public ShortBuffer vib;
	public int ibb;
	
	boolean updatedC=false;
	boolean updatedV=false;
	
	boolean used=false;
	
	public ModelTerraReference(){
		
	}
	
	public ModelTerraReference(int v,int n,int faces){
		this.v=v;
		this.n=n;
		this.faces=faces;
	}
	
	public ModelTerraReference(int v,int n,int c,int faces){
		this.v=v;
		this.n=n;
		this.c=c;
		this.faces=faces;
	}
	
	public static ModelTerraReference dupe(ModelTerraBuffer m3){
		ModelTerraReference me=new ModelTerraReference();
		me.v=m3.v;
		me.n=m3.n;
		me.c=m3.c;
		me.faces=m3.faces;
		me.v2=m3.v2;
		me.ibb=m3.ibb;
		
		return me;
	}
}
