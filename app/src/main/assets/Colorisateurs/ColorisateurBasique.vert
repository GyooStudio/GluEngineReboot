#version 300 es
precision mediump float;

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 uv;
layout(location = 2) in vec3 normale;
layout(location = 3) in vec3 tangeante;
layout(location = 4) in vec3 bitangeante;

uniform mat4 projection;
uniform mat4 transformee;
uniform mat4 vue;
uniform mat4 rotation;

out vec3 f_pos;
flat out vec2 f_uv;
flat out vec3 f_norm;

void main() {
    f_pos = (transformee*vec4(position,1.0)).xyz;
    f_uv = uv;
    f_norm = (rotation*vec4(normale,1.0)).xyz;
    gl_Position = projection*vue*transformee*vec4(position,1.0);
    gl_PointSize = 5.0;
}
