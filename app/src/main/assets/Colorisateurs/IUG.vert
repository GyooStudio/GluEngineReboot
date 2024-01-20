#version 300 es
precision mediump float;

in vec4 position;

uniform mat4 Transformee;
uniform vec2 screen;

out vec2 UV;

void main(){
    UV = vec2(position.x + 0.5, 0.5 - position.y);
    gl_Position = (Transformee * position)/vec4(screen.x,screen.y,1f,1f);
}