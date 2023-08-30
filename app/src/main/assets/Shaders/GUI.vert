#version 300 es
precision mediump float;

in vec4 position;

uniform mat4 Transform;
uniform vec2 screen;

void main(){
    gl_Position = (Transform * position)/vec4(screen.x,screen.y,1f,1f);
}