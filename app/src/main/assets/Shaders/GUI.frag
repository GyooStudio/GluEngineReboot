#version 300 es

precision mediump float;

out vec4 Fragment;

uniform vec4 color;

void main(){
    Fragment = vec4(color);
}