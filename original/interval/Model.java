package interval;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;

public class Model {

	Vec verts[];
	Vec norms[];
	int count;
	int order[][];
	int norder[][];
	int faces;
	boolean tri[];

	public float x=0f,y=0f,z=0f;

	float maxx=-9000f,minx=9000f;
	float maxz=-9000f,minz=9000f;
	float maxy=-9000f,miny=9000f;

	float sizex,sizez,sizey;
	int vertexBufferID; 
	int normalBufferID; 
	public Model(){
	}

	public Model(String s){

		FileInputStream fstream;
		try {
			fstream = new FileInputStream(s);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));



			String  st;
			int ord[][]=new int[9000][4];
			int nord[][]=new int[9000][4];
			boolean tri2[]=new boolean[9000];

			Vec vek[]=new Vec[9000];

			Vec nom[]=new Vec[9000];

			int cn=0;
			int k=0;

			int nn=0;
			while((st=br.readLine()) != null){
				if(st.charAt(0)!='#'){
					if(st.charAt(0)=='v'){
						if(st.charAt(1)=='n'){
							int i1=st.indexOf(" ", 3);
							String s1=st.substring(3, i1);
							float f1 = new Float(s1);

							int i2=st.indexOf(" ", i1+1);
							String s2=st.substring(i1, i2);
							float f2 = new Float(s2);

							String s3=st.substring(i2);
							float f3 = new Float(s3);

							nom[nn]= new Vec(f1, f2, f3);
							nn++;

						}else if(st.charAt(1)=='t'){

						}else{

							int i1=st.indexOf(" ", 2);
							String s1=st.substring(2, i1);
							float f1 = new Float(s1);


							int i2=st.indexOf(" ", i1+1);
							String s2=st.substring(i1, i2);
							float f2 = new Float(s2);

							String s3=st.substring(i2);
							float f3 = new Float(s3);

							if(f1>maxx){
								maxx=f1;
							}

							if(f1<minx){
								minx=f1;
							}

							if(f3>maxz){
								maxz=f3;
							}

							if(f3<minz){
								minz=f3;
							}

							if(f2>maxy){
								maxy=f2;
							}

							if(f2<miny){
								miny=f2;
							}

							vek[cn]= new Vec(f1, f2, f3);
							cn++;
						}
					}
					if(st.charAt(0)=='f'){
						//int tmp=st.indexOf("//");
						//while(tmp!=-1){
						//	st=st.substring(0, tmp)+st.substring(tmp+3);


						//	tmp=st.indexOf("//");
						//}


						int i1=st.indexOf(" ", 2);
						String s1=st.substring(2, i1);
						int ii1=s1.indexOf("/");
						int p1;
						int n1=0;
						if(ii1!=-1){
							p1=new Integer(s1.substring(0, ii1));
							n1=new Integer(s1.substring(ii1+2)); //HERE
						}else{
							p1 = new Integer(s1);
						}

						int i2=st.indexOf(" ", i1+1);
						String s2=st.substring(i1+1, i2);
						int ii2=s2.indexOf("/");
						int p2;
						int n2=0;
						if(ii2!=-1){
							p2=new Integer(s2.substring(0, ii2));
							n2=new Integer(s2.substring(ii2+2));//HERE
						}else{
							p2 = new Integer(s2);
						}
						boolean part4=false;

						int i3=st.indexOf(" ", i2+1);
						String s3;
						if(i3!=-1){
							s3=st.substring(i2+1, i3);
							part4=true;
							tri2[k]=false;
						}else{
							s3=st.substring(i2+1);
							tri2[k]=true;
						}
						int ii3=s3.indexOf("/");
						int p3;
						int n3=0;
						if(ii3!=-1){
							p3=new Integer(s3.substring(0, ii3));
							n3=new Integer(s3.substring(ii3+2));//HERE
						}else{
							p3 = new Integer(s3);
						}

						int p4=0;
						int n4=0;
						if(part4){
							//int i4=st.indexOf(" ", i3+1);
							String s4=st.substring(i3+1);

							int ii4=s4.indexOf("/");


							if(ii4!=-1){
								p4=new Integer(s4.substring(0, ii4));
								n4=new Integer(s4.substring(ii4+2));//HERE
							}else{
								p4 = new Integer(s4);
							}
						}

						ord[k][0]=p1;
						ord[k][1]=p2;
						ord[k][2]=p3;
						ord[k][3]=p4;

						nord[k][0]=n1;
						nord[k][1]=n2;
						nord[k][2]=n3;
						nord[k][3]=n4;

						k++;

					}
				}
			}
			count=cn;
			verts = new Vec[cn];
			for(int ii=0;ii<(cn);ii++){
				verts[ii]=vek[ii];
			}

			norms = new Vec[nn];
			for(int ii=0;ii<(nn);ii++){
				norms[ii]=nom[ii];
			}




			faces = k;

			order = new int[faces][4];
			norder = new int[faces][4];
			tri=new boolean[faces];

			for(int kk=0;kk<(k);kk++){
				order[kk][0]=ord[kk][0];
				order[kk][1]=ord[kk][1];
				order[kk][2]=ord[kk][2];
				order[kk][3]=ord[kk][3];


				norder[kk][0]=nord[kk][0];
				norder[kk][1]=nord[kk][1];
				norder[kk][2]=nord[kk][2];
				norder[kk][3]=nord[kk][3];
				tri[kk]=tri2[kk];
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		sizex=maxx-minx;
		sizez=maxz-minz;
		sizey=maxy-miny;

		VBO();

	}

	public void VBO(){

		float V[]=new float[faces*4*3];
		float N[]=new float[faces*4*3];

		int fff=0;

		for(int i=0;i<(faces);i++){
			for(int k=0;k<(4);k++){

				int u=order[i][k]-1;
				int nu=norder[i][k]-1;

				if(u==-1){
					u=order[i][2]-1;
					nu=norder[i][2]-1;


				}


				V[fff*3 ]=verts[u].x;
				V[fff*3 +1]=verts[u].y;
				V[fff*3 +2]=verts[u].z;



				N[fff*3 ]=norms[nu].x;
				N[fff*3 +1]=norms[nu].y;
				N[fff*3 +2]=norms[nu].z;

				fff++;
				//GL11.glNormal3f(norms[nu].x,norms[nu].y,norms[nu].z);



			}
		}


		FloatBuffer vb= (FloatBuffer) BufferUtils.createFloatBuffer(faces*4*3).put(V).flip();
		vertexBufferID=createVBOID();

		FloatBuffer nb= (FloatBuffer) BufferUtils.createFloatBuffer(faces*4*3).put(N).flip();
		normalBufferID=createVBOID();

		bufferData(vertexBufferID,vb);
		bufferData(normalBufferID,nb);
	}



	public static int createVBOID() {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			ARBVertexBufferObject.glGenBuffersARB(buffer);
			return buffer.get(0);
		}
		return 0;
	}

	public static int createBufferID() {
		//if (GLContext.getCapabilities().) {
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		GL15.glGenBuffers(buffer);
		return buffer.get(0);
		//}
		//return 0;
	}

	/*public static ModelTerraBuffer createModel3(){
		int faces=4;
		float H=0.1f;
		float V[] = {
				//H,0,0f,
				//0,0,0f,
				//0,H,0f,
				//H,H,0f,
				

				H,0,H,
				0,0,H,
				0,H,H,
				H,H,H,



				H,0,0,
				H,0,H,
				0,0,H,
				0,0,0,

				//H,H,0,
				//H,H,H,
				//0,H,H,
				//0,H,0,


				0,H,0,
				0,H,H,
				0,0,H,
				0,0,0,

				H,H,0,
				H,H,H,
				H,0,H,
				H,0,0


		};


		float N[] = {
				0f,1f,
				1f,1f,
				1f,0f,
				0f,0f
		};
		FloatBuffer vb= (FloatBuffer) BufferUtils.createFloatBuffer(faces*4*3).put(V).flip();
		int vid=createVBOID();

		FloatBuffer tb= (FloatBuffer) BufferUtils.createFloatBuffer(faces*4*2).put(N).flip();
		int tid=createVBOID();

		bufferData(vid,vb);
		bufferData(tid,tb);

		return new ModelTerraBuffer(vid,tid,faces*4);
	}*/




	/**
	 * create a model for a single terrain sector (32 x 32 squares, + binding squares)
	 * @return
	 */
	public static ModelTerraBuffer createMap(){//int iii,int jjj){
		int M=32;
		int i=0;
		int z=1;
		int side=3;
		int faces=M*M*side;

		int M2=M*M;

		float V[] = new float[M2*24]; //12  24

		short I[]=new short[M2*12]; //18
		
		float N[]= new float[M2*24];//36

		float C[]= new float[M2*24];
		float F=0.5f;
		float Dx=0.5f;
		float Dy=0.5f;
		
		int ii=0;
		int j=0;
		int jj=0;
		for(float x=0;x<M;x++){
			for(float y=0;y<M;y++){		
				z=0;
				
				float YY=y;
				/*if(x%2==1){
					YY=M-y;
				}*/

				V[i]=(x+F)+Dx;i++;	N[ii]=0f;ii++;
				V[i]=(YY-F)+Dy;i++;	N[ii]=0f;ii++;
				V[i]=z+1;i++;		N[ii]=1f;ii++;

				V[i]=(x-F)+Dx;i++;	N[ii]=0f;ii++;
				V[i]=(YY-F)+Dy;i++;	N[ii]=0f;ii++;
				V[i]=z+1;i++;		N[ii]=1f;ii++;

				V[i]=(x-F)+Dx;i++;	N[ii]=0f;ii++;
				V[i]=(YY+F)+Dy;i++;	N[ii]=0f;ii++;
				V[i]=z+1;i++;		N[ii]=1f;ii++;

				V[i]=(x+F)+Dx;i++;	N[ii]=0f;ii++;
				V[i]=(YY+F)+Dy;i++;	N[ii]=0f;ii++;
				V[i]=z+1;i++;		N[ii]=1f;ii++;
				
				

				//V[i]=(x+F)+Dx;i++;	N[ii]=1f;ii++;
				//V[i]=(y-F)+Dy;i++;	N[ii]=0f;ii++;
				//V[i]=z+1;i++;		N[ii]=0f;ii++;

				//V[i]=(x+F)+Dx;i++;	N[ii]=1f;ii++;
				//V[i]=y+F+Dy;i++;	N[ii]=0f;ii++;
				//V[i]=z+1;i++;		N[ii]=0;ii++;

				/*N[ii]=1f;ii++;
				N[ii]=0f;ii++;
				N[ii]=0f;ii++;

				N[ii]=1f;ii++;
				N[ii]=0f;ii++;
				N[ii]=0f;ii++;*/

				V[i]=(x+F)+Dx;i++;	N[ii]=1f;ii++;
				V[i]=(YY+F)+Dy;i++;	N[ii]=0f;ii++;
				V[i]=z;i++;			N[ii]=0;ii++;

				V[i]=(x+F)+Dx;i++;	N[ii]=1f;ii++;
				V[i]=(YY-F)+Dy;i++;	N[ii]=0f;ii++;
				V[i]=z;i++;			N[ii]=0;ii++;

				/*
				V[i]=(x);N[i]=-1f;C[i]=r1;i++;
				V[i]=y;N[i]=0f;C[i]=g1;i++;
				V[i]=z+1;N[i]=0;C[i]=b1;i++;

				V[i]=(x);N[i]=-1f;C[i]=r1;i++;
				V[i]=y+1;N[i]=0f;C[i]=g1;i++;
				V[i]=z+1;N[i]=0;C[i]=b1;i++;

				V[i]=(x);N[i]=-1f;C[i]=r2;i++;
				V[i]=(y+1);N[i]=0f;C[i]=g2;i++;
				V[i]=z;N[i]=0;C[i]=b2;i++;

				V[i]=(x);N[i]=-1f;C[i]=r2;i++;
				V[i]=(y);N[i]=0f;C[i]=g2;i++;
				V[i]=z;N[i]=0;C[i]=b2;i++;

				 */

				//V[i]=(x+F)+Dx;i++;	N[ii]=0f;ii++;
				//V[i]=y+F+Dy;i++;	N[ii]=1f;ii++;
				//V[i]=z+1;i++;		N[ii]=0;ii++;

				//V[i]=(x-F)+Dx;i++;	N[ii]=0f;ii++;
				//V[i]=y+F+Dy;i++;	N[ii]=1f;ii++;
				//V[i]=z+1;i++;		N[ii]=0;ii++;


				/*N[ii]=0f;ii++;
				N[ii]=1f;ii++;
				N[ii]=0f;ii++;

				N[ii]=0f;ii++;
				N[ii]=1f;ii++;
				N[ii]=0f;ii++;*/

				V[i]=(x-F)+Dx;i++;	N[ii]=0f;ii++;
				V[i]=(YY+F)+Dy;i++;	N[ii]=1f;ii++;
				V[i]=z;i++;			N[ii]=0;ii++;

				V[i]=(x+F)+Dx;i++;	N[ii]=0f;ii++;
				V[i]=(YY+F)+Dy;i++;	N[ii]=1f;ii++;
				V[i]=z;i++;			N[ii]=0;ii++;
				
			

				jj=(int) (x*M +y)*8; //QUADS
				I[j]=(short) (0+jj);j++;
				I[j]=(short) (1+jj);j++;
				I[j]=(short) (2+jj);j++;
				I[j]=(short) (3+jj);j++;
				
				I[j]=(short) (0+jj);j++;
				I[j]=(short) (3+jj);j++;
				I[j]=(short) (4+jj);j++;
				I[j]=(short) (5+jj);j++;
				
				I[j]=(short) (3+jj);j++;
				I[j]=(short) (2+jj);j++;
				I[j]=(short) (6+jj);j++;
				I[j]=(short) (7+jj);j++;
				
				
				/*jj=(int) (x*M +y)*18; //TRIS
				I[j]=(short) (2+jj);j++;
				I[j]=(short) (1+jj);j++;
				I[j]=(short) (3+jj);j++;
				
				I[j]=(short) (1+jj);j++;
				I[j]=(short) (3+jj);j++;
				I[j]=(short) (0+jj);j++;
				
				I[j]=(short) (0+jj);j++;
				I[j]=(short) (4+jj);j++;
				I[j]=(short) (5+jj);j++;
				
				I[j]=(short) (4+jj);j++;
				I[j]=(short) (3+jj);j++;
				I[j]=(short) (0+jj);j++;
				
				I[j]=(short) (3+jj);j++;
				I[j]=(short) (2+jj);j++;
				I[j]=(short) (7+jj);j++;
				
				I[j]=(short) (7+jj);j++;
				I[j]=(short) (2+jj);j++;
				I[j]=(short) (6+jj);j++;*/
			}
		}

		FloatBuffer vb= (FloatBuffer) BufferUtils.createFloatBuffer(V.length).put(V).rewind();
		int vid=createVBOID();

		FloatBuffer nb= (FloatBuffer) BufferUtils.createFloatBuffer(N.length).put(N).flip();
		int nid=createVBOID();

		FloatBuffer cb= (FloatBuffer) BufferUtils.createFloatBuffer(C.length).put(C).flip();
		int cid=createVBOID();
		
		ShortBuffer ib= (ShortBuffer) BufferUtils.createShortBuffer(I.length).put(I).flip();

		//ShortBuffer iib=  BufferUtils.createShortBuffer(12);
		//iib.put(new short[]{0, 1, 2, 3, 0, 3, 4, 5,3,2,6,7});
				
		int buffer=createBufferID();
		bufferElementData(buffer, ib);
		
		bufferData(vid,vb);//(FloatBuffer) vertices.flip());
		bufferData(nid,nb);
		bufferData(cid,cb);

		ModelTerraBuffer m3=new ModelTerraBuffer(vid,nid,cid,faces*4);
		m3.V=V;
		m3.C=C;
		m3.N=N;
		m3.ibb=ib.capacity();
		m3.v2=buffer;
		return m3;  //tid
	}

	public static void bufferData(int id, FloatBuffer buffer) {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, id);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, buffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}
	}
	
	public static void bufferElementData(int id, ShortBuffer buffer) {
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, id);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, buffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}
	}



	public void render(){
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBufferID);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);

		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, normalBufferID);
		GL11.glNormalPointer( GL11.GL_FLOAT, 0, 0);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, faces*4);
	}

	public void renderO(){
		GL11.glPushMatrix();

		GL11.glTranslatef(x, y, z);

		//GL11.glRotatef(90, 1, 0,0);
		GL11.glBegin(GL11.GL_QUADS);
		for(int i=0;i<(faces);i++){
			for(int k=0;k<(4);k++){

				int u=order[i][k]-1;
				int nu=norder[i][k]-1;
				if(u==-1){
					u=order[i][2]-1;
					nu=norder[i][2]-1;

					//GL11.glColor4f(1f, 0.1f, 0.1f,1f);
				}else{
					//GL11.glColor4f(0.6f, 0.6f, 0.6f,0.1f);
				}

				GL11.glNormal3f(norms[nu].x,norms[nu].y,norms[nu].z);
				GL11.glVertex3f(verts[u].x, verts[u].y, verts[u].z);

			}
		}

		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public int vertNum(){
		return verts.length;
	}
	public int normNum(){
		return verts.length;
	}

	public void stats(){
		Message.m(this,"verts: "+vertNum()+"norms: "+normNum()+"facets: "+faces);
	}


	public void render(float Fx,float Fy,float Fz){
		float vax=Fx*sizex +minx;
		float vaz=Fy*sizez +minz;
		float vay=Fz*sizey +miny;
		GL11.glPushMatrix();

		GL11.glTranslatef(x, y, z);

		//GL11.glRotatef(90, 1, 0,0);
		GL11.glBegin(GL11.GL_QUADS);
		Vec v[]=new Vec[4];
		Vec n[]=new Vec[4];

		for(int t=0;t<4;t++){
			v[t]=new Vec();
			n[t]=new Vec();
		}
		boolean dump=false;

		for(int i=0;i<(faces);i++){
			for(int k=0;k<(4);k++){
				int u=order[i][k]-1;
				int nu=norder[i][k]-1;
				if(u==-1){
					u=order[i][2]-1;
					nu=norder[i][2]-1;

					//GL11.glColor4f(1f, 0.1f, 0.1f,1f);
				}else{
					//GL11.glColor4f(0.6f, 0.6f, 0.6f,0.1f);
				}
				float xu=verts[u].x;
				float yu=verts[u].y;
				float zu=verts[u].z;
				n[k].x=norms[nu].x;
				n[k].y=norms[nu].y;
				n[k].z=norms[nu].z;
				//GL11.glNormal3f(,norms[nu].y,norms[nu].z);

				dump=false;
				if(xu>vax){
					dump=true;
					//xu=vax;
				}

				if(yu>vay){
					dump=true;
					//yu=vay;
				}

				if(zu>vaz){
					dump=true;
					//zu=vaz;
				}
				v[k].x=xu;
				v[k].y=yu;
				v[k].z=zu;



			}

			if(!dump){
				GL11.glNormal3f(n[0].x,n[0].y,n[0].z);
				GL11.glVertex3f(v[0].x,v[0].y,v[0].z);

				GL11.glNormal3f(n[1].x,n[1].y,n[1].z);
				GL11.glVertex3f(v[1].x,v[1].y,v[1].z);

				GL11.glNormal3f(n[2].x,n[2].y,n[2].z);
				GL11.glVertex3f(v[2].x,v[2].y,v[2].z);

				GL11.glNormal3f(n[3].x,n[3].y,n[3].z);
				GL11.glVertex3f(v[3].x,v[3].y,v[3].z);
			}
		}

		GL11.glEnd();
		GL11.glPopMatrix();
	}



	public void ms(String s){
		System.out.println(s);
	}

	public void arrayPrint(float F[]){
		int n=F.length;
		for(int i=0;i<n;i++){
			System.out.print(F[i]+", ");
		}

	}
	public void arrayPrint(float F[][]){
		int n=F.length;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(F[i][j]+", ");
			}
		}
	}
	public void arrayPrint(int F[][]){
		int n=F.length;
		int n2 = F[0].length;
		for(int i=0;i<n;i++){
			for(int j=0;j<n2;j++){
				System.out.print(F[i][j]+", ");
			}
			System.out.println("");
		}
	}

	public void printVert(){
		int n=verts.length;
		for(int i=0;i<n;i++){
			System.out.println(verts[i].x+", "+verts[i].y+", "+verts[i].z);
		}
	}

	public static ModelTerraBuffer createGrassModel() {

/*
		float V[] = new float[192];

		int ii=0;
		for(float xx=0;xx<1;xx+=0.25f){
			for(float yy=0;yy<1;yy+=0.25f){
				float zz=(float) Math.random()*3;
				V[ii]=xx;V[ii+1]=yy; V[ii+2]=zz;ii+=3;
				V[ii]=xx;V[ii+1]=yy; V[ii+2]=zz;ii+=3;
				V[ii]=xx;V[ii+1]=yy; V[ii+2]=zz;ii+=3;
				V[ii]=xx;V[ii+1]=yy; V[ii+2]=zz;ii+=3;
			}
		}
		//float C[]

		FloatBuffer vb= (FloatBuffer) BufferUtils.createFloatBuffer(V.length).put(V).rewind();
		int vid=createVBOID();


		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vid);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vb, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);


		int IN[] = new int[192];
		for(int iii=0;iii<IN.length;iii++){
			IN[iii]=iii;
		}
		IntBuffer ib= (IntBuffer) BufferUtils.createIntBuffer(IN.length).put(IN).flip();

		Model3 mod = new Model3();
		mod.v=vid;
		mod.ib=ib;
*/
		
		return null;//mod;
	}
}
