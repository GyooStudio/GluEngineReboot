#version 300 es

precision mediump float;

out vec4 Fragment;
in vec2 UV;

uniform vec4 color;
layout(location = 0) uniform sampler2D texture;

uniform bool isTextured;

void main(){
    vec4 texture = texture(texture, UV);
    Fragment = mix( color, texture * color, float(isTextured) );
}