#version 150 core

uniform sampler2D texture_diffuse;

in vec4 pass_color;
in vec2 pass_texCoord;

out vec4 out_color;

void main(void) {
	out_color = vec4(1,0,0,1);
	// Override out_Color with our texture pixel
	out_color = texture(texture_diffuse, pass_texCoord);
}