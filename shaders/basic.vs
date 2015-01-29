#version 150 core

in vec3 in_pos;
in vec4 in_color;
in vec2 in_texCoord;

out vec4 pass_color;
out vec2 pass_texCoord;

void main(void) {
	gl_Position = in_pos;
	
	pass_color = in_color;
	pass_texCoord = in_texCoord;
}