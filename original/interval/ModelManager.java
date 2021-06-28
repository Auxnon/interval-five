package interval;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelManager {

	static HashMap<String,Actor> actors=new HashMap<String,Actor>();
	static HashMap<String,VGO> models=new HashMap<String,VGO>();



	static HashMap<String,VGOraw> combos=new HashMap<String,VGOraw>();

	static Actor norm=new Actor();
	private static final VGOraw none2= new VGOraw( 1,FileManager.getTextureDir()+"empty.png");
	private static VGO none;
	private static VGO loading;

	static boolean updated;
	public static void init() {
		VGOraw loading2= new VGOraw( 1,FileManager.getTextureDir()+"load.png");
		addActor("norm",norm);
		none2.save("");
		loading2.save("");
		loading=reloadModel("load");
		none= reloadModel("empty");
	}
	public ModelManager(){

	}

	/*public static VGOprime remakeOld(String name){
		//name=FileManager.cleanestTextureName(name);
		VGOprime v=new VGOprime( 1,FileManager.getTextureDir()+name+".png");
		if(v.failed){
			return none;
		}else{
			v.save("");
			addOld(name,v);
			return v;
		}
	}*/
	public static void remakeModel2(String name){
		VGOraw v1=new VGOraw( 1,FileManager.getTextureDir()+name+".png");
		if(v1.failed){
			models.put(name, none);
		}else{
			v1.save("");
			VGO v=FileManager.loadModel(name);
			models.put(name, v);
			updated=true;
		}
	}
	public static VGO remakeModel(String name){
		combos.remove(name);
		Main.loader.loadModel(name);
		models.put(name, loading);
		return loading;
	}


	public static VGOraw getCombo(String name){
		name=FileManager.cleanestFileName(name);
		VGOraw ii=combos.get(name);
		if(ii==null){
			VGOraw v=new VGOraw( 1,FileManager.getTextureDir()+name+".png");
			if(v.failed){
				combos.put(name, none2);
				return none2;
			}else{
				v.save("");
				combos.put(name,v);
				return v;
			}
		}
		return ii;
	}
	public static Actor getActor(String name){
		Actor aa=actors.get(name);
		if(aa==null){
			return norm;
		}
		return aa;
	}
	public VGO getSub(String what) {
		return null;
	}
	public static void combine2(String one, String two,boolean smooth){
		combine2(one,two,smooth,"");
	}
	public static void combine(String one){

	}
	//public static void combine(String one, String two,boolean smooth,String der){

	//}
	public static void combine2(String one, String two,boolean smooth,String der){
		boolean userCalled=der.length()==0;
		VGOraw uno=
				!userCalled?
						new VGOraw( 1,FileManager.getTextureDir()+FileManager.cleanestFileName(one)+".png")
		:getCombo(one);

						VGOraw dos=new VGOraw( 1,FileManager.getTextureDir()+FileManager.cleanestFileName(two)+".png");
						if(smooth){dos.ground();}
						if(uno!=none && dos!=none && !uno.failed && !dos.failed){
							uno.combine(dos);
							//	if(!userCalled)
							//combos.put(der, uno);
							if(userCalled)
								combos.put(one, uno);

							uno.save(der);
							VGO v=FileManager.loadModel(der);
							models.put(der, v);
							updated=true;
							Message.m(ModelManager.class,"saved "+one+"/"+two+" combination"+(der!=""?" as "+der:""));
						}else{
							Message.m(ModelManager.class,"combination of "+one+" and "+two+" failed :( "+uno+" "+dos);
						}
	}
	public static void combine(String one, String two,int i,int j,int k,boolean smooth){
		if(i==0 && j==0 && k==0){
			combine2(one,two,smooth);
			return;
		}
		VGOraw uno=getCombo(one);
		VGOraw dos=new VGOraw( 1,FileManager.getTextureDir()+FileManager.cleanestFileName(two)+".png");//getOld(two);
		if(uno!=none && dos!=none){
			Message.addPass("got this far");
			uno.combine(dos,(short)i,(short)j,(short)k,smooth);
			combos.put(one, uno);
			uno.save("");
			Message.m(ModelManager.class,"saved "+one+"/"+two+" combination");
		}else{
			Message.m(ModelManager.class,"combination of "+one+" and "+two+" failed :( "+uno+" "+dos);
		}
	}

	public static VGO reloadModel(String name){
		VGO v=FileManager.loadModel(name);
		addModel(name, v);
		return v;
	}

	public static VGO getModel(String name) {
		VGO v=models.get(name);
		if(v==null){
			v=reloadModel(name);
			if(v!=null)
				return v;
		}else
			return v;
		if(v==null && name!=null){
			//if(name.contains("-")){
			//String[] st=name.split("-");
			//combine(name);
			//v=reloadModel(name);
			//}else{
			v=remakeModel(name);
			//}

			if(v!=null)
				return v;

		}

		//VGO vg=reloadModel("empty");
		//addModel(name,vg);
		//Message.m(ModelManager.class,name+" contains missing textures, set to empty model");
		return none;
	}
	public static boolean hasModel(String name) {
		return models.get(name)==null;
	}
	public static void addActor(String s,Actor a){
		actors.put(s, a);
	}
	public static void addModel(String s,VGO m){
		models.put(s, m);
	}



	/**
	 * used mainly for buildings, in order to determine the shape of the interior
	 * @param zLevel at what vertical height should the dissection of the model be analyzed
	 */
	public static ArrayList<Vec> getLayout(VGO vgo,int zLevel){
		float Z=zLevel/16f;
		ArrayList<ArrayList<Vec>> lv = new ArrayList<ArrayList<Vec>>();
		ArrayList<Float> val=new ArrayList<Float>();
		for(Vox vox:vgo.v){
			if(Z<=vox.V[2] && Z>=vox.V[5]){
				ArrayList<Vec> p=new ArrayList<Vec>();
				p.add(new Vec(vox.V[0],vox.V[1],0));
				p.add(new Vec(vox.V[3],vox.V[1],0));
				p.add(new Vec(vox.V[3],vox.V[4],0));
				p.add(new Vec(vox.V[0],vox.V[4],0));
				val.add(Math.abs((vox.V[0]-vox.V[3])*(vox.V[1]-vox.V[4])));
				lv.add(p);
			}
		}
		int j=0;
		int i=0;
		while(lv.size()>1 && i<lv.size() && j<lv.size()){
			loop:
				for (i = 0; i < lv.size(); i++){
					ArrayList<Vec> one=lv.get(i);
					for (j = i + 1; j < lv.size(); j++) {
						ArrayList<Vec> two=lv.get(j);

						for (int a = 0; a < one.size(); a++){
							for (int b =0; b < two.size(); b++) {
								int A=a+1;
								if(A>=one.size())
									A=0;

								int B=b+1;
								if(B>=two.size())
									B=0;
								Vec a1=one.get(a);
								Vec a2=one.get(A);
								Vec b1=two.get(b);
								Vec b2=two.get(B);

								Message.m(ModelManager.class,"compare shape "+
										i +"-"+a+","+A 
										+" with "+
										j+"-"+b+","+B);

								boolean Intersect=false;
								if(a1.x==a2.x && b1.x==b2.x && a1.x==b1.x){
									Message.m(ModelManager.class,"along y axis");
									float asy=Math.min(a1.y, a2.y);
									float aey=Math.max(a1.y, a2.y);
									float bsy=Math.min(b1.y, b2.y);
									float bey=Math.max(b1.y, b2.y);
									if(aey>bsy && asy<bey){ //intersecting
										Intersect=true;
									}
								}else if(a1.y==a2.y && b1.y==b2.y && a1.y==b1.y){
									Message.m(ModelManager.class,"along x axis");
									float asx=Math.min(a1.x, a2.x);
									float aex=Math.max(a1.x, a2.x);
									float bsx=Math.min(b1.x, b2.x);
									float bex=Math.max(b1.x, b2.x);
									if(aex>bsx && asx<bex){ //intersecting
										Intersect=true;
									}	
								}

								if(Intersect){
									Message.m(ModelManager.class, "intersection with "+ i+" and "+j
											+" at "+ i +"-"+a+","+A 
											+" and "+
											j+"-"+b+","+B+"\n"+
											a1.x+","+a1.y+"|"+a2.x+","+a2.y+" --> "+
											b1.x+","+b1.y+"|"+b2.x+","+b2.y
											);

									for(int u=0;u<two.size();u++){
										int I=u+B;
										if(I>=two.size())
											I-=two.size();
										one.add(a+u+1,two.get(I));
									}
									val.set(i, val.get(i)+val.get(j));
									val.remove(j);
									lv.remove(j);

									i=0;
									j=1;
									break loop;
								}



							}
						}
					}
				}
		Message.m(ModelManager.class, "reloop layout");
		}
		//we only want one polygon!
		ArrayList<Vec> poly;
		if(lv.size()>1){
			float highest=0;
			int out=0;
			int uu=0;
			for(Float F:val){
				if(F>highest){
					highest=F;out=uu;
				}
				uu++;
			}
			poly= lv.get(out);
		}else{
			poly= lv.get(0);
		}

		//and get rid of duplicate vectors
		/*Vec previous=new Vec(-9000,-9000,-9000);
		for(int h=0;h<poly.size();h++){
			Vec vvv=poly.get(h);
			if(vvv.x==previous.x && vvv.y==previous.y){
				poly.remove(vvv);
				h--;
			}

			previous=vvv;

		}*/

		int mark=0;
		int placement=0;
		short orientation=0;
		Vec previous=poly.get(0);
		for(int h=1;h<poly.size();h++){
			Vec v=poly.get(h);
			boolean End=false;
			if(v.x==previous.x){
				if(orientation==1){
					mark++;
				}else{
					End=true;
					orientation=1;
				}
			}else if(v.y==previous.y){
				if(orientation==2){
					mark++;
				}else{
					End=true;
					orientation=2;
				}
			}else{
				End=true;
				orientation=0;
			}

			if(End){
				if(mark>0){

					for(int k=0;k<mark;k++){
						Message.m(ModelManager.class, "remove "+(k+placement));
						poly.remove(placement);
						h--;
					}
					
				}
				mark=0;
				placement=h;
			}
			previous=v;
		}
		Message.m(ModelManager.class, "count "+poly.size());
		return poly;
	}


}
