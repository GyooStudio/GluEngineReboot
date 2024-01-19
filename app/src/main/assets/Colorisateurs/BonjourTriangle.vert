#version 300 es

in vec4 position;

out vec4 pos_o;

uniform mat4 Transform;

void main() {
    pos_o = position;
    gl_Position = Transform * position;
}
