#version 300 es

in vec4 position;

uniform mat4 Proj;
uniform mat4 Transform;

void main() {
    gl_Position = Proj * Transform * position;
}
