/*uniform float time;
varying vec4 vertColor;

void main(void)
{
	
	
	vec4 v = vec4(gl_Vertex);
	float var=v.x*0.5 + time*0.1;
	if(var>3.1457){
		var=0;
	}
	v.z = v.z+sin(var);
	gl_Position = gl_ModelViewProjectionMatrix * v;
	gl_FrontColor = gl_Color;   
	vertColor = vec4(0,0,0,0);
}
*/

varying vec3 normal, lightDir, eyeVec;
uniform float time;
uniform vec3 ppos;
uniform vec3 rrot;
uniform int on;

void main()
{	
	gl_TexCoord[0] = gl_MultiTexCoord0;
	
	vec4 v = vec4(gl_Vertex);
	float var=v.x*0.5 + time*0.1;
	v.z = v.z+sin(var);
	//gl_Position = gl_ModelViewProjectionMatrix * v;
	

	
	vec3 lightPos=gl_LightSource[1].position.xyz;
	normal = (gl_NormalMatrix * gl_Normal);
	vec3 vVertex = vec3(gl_ModelViewMatrix * gl_Vertex);

	if(on==1){
		vec3 difference = lightPos - ppos;
		vec3 skewed = vec3(difference.x*rrot.x-difference.y*rrot.y,difference.x*rrot.y +difference.y*rrot.x,difference.z);
		lightDir = vec3(((-vVertex)))+normal;
		lightDir+=(skewed);
	}else{
		lightDir = vec3(((lightPos-vVertex)))+normal;
	}
	
	gl_Position = ftransform();	
	eyeVec = -vVertex;
	gl_FrontColor = gl_Color; 

}
