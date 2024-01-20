#version 300 es
precision mediump float;

in vec3 f_pos;
flat in vec2 f_uv;
flat in vec3 f_norm;

uniform sampler2D texture;

out vec4 Fragment;
void main() {
    float wire = 1.0;//float(max(abs(f_uv.x-0.5),abs(f_uv.y-0.5)) > 0.45);
    vec3 color = vec3(f_uv,0.0) * (dot(f_norm,vec3(0,1.0,0)) + 0.5);
    mat3 aces =  mat3(
    0.9525523959,0,            0.0000936786,
    0.3439664498,0.7281660966, -0.0721325464,
    0,           0,            1.008251844);
    color = aces*color;
    Fragment = vec4(color, wire);
}
