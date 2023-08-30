#version 300 es

out vec4 Fragment;

in vec4 pos_o;

void main() {
    Fragment = vec4(pos_o);
}
