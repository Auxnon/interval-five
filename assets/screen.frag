/*
void main(void)
{
  gl_FragColor = gl_Color;
}*/


varying vec3 normal, lightDir, eyeVec;
uniform sampler2D colorMap;
uniform float season;
uniform vec2 clip;

void main (void)
{
	
	vec4 gg =texture2D( colorMap, gl_TexCoord[0].st);
	bool bul=gg==vec4(1,1,1,1);
	if(bul){
		gg=gl_Color;
		
		if(gg.z==0.042 && season>0){
			if(season<=0.4){
				gg-=vec4(0,season,0,0);
			}else{
				gg-=vec4(0,0.4,0,0);
				float n=(season-0.4)*5;
				gg.x+=n;
				gg.y+=n;
				gg.z+=n;
				gg.w+=n;
				if(gg.x>1.5){
					gg.x=1.5;}
					
				if(gg.y>1.5){
					gg.y=1.5;}
					
				if(gg.z>1.5){
					gg.z=1.5;}
					
				if(gg.w>1){
					gg.w=1;}
					
				//gg=vec4(1.2,1.2,1.2,1);
			}
			
		}
		
		//gg=vec4(1.2,1.2,1.2,1);
	}
	
	vec4 final_color = 
	
	(( (gl_FrontLightModelProduct.sceneColor * gl_FrontMaterial.ambient ) + //*gl_FrontLightModelProduct.sceneColor
	(gl_LightSource[1].ambient * gg) )) ;
							
	vec3 N = normalize(normal);
	vec3 L = normalize(lightDir);
	
	float lambertTerm = dot(N,L);
	float D=dot(normal,lightDir)/clip.x;
	
	if(lambertTerm >clip.y)
	{

	float vc=(lambertTerm-D);
	if(vc<0){
	vc=0;
	}
		final_color += vc*gg;
	}
	
	if(!bul){
	final_color*=gl_Color;
	}

	gl_FragColor = final_color;		
	//gl_FragColor.w=gg.w;
}