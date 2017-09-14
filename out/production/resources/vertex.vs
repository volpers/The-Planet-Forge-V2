#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec3 inColor;

uniform mat4 projectionMatrix;
uniform mat4 worldMatrix;

out vec3 exColor;

void main()
{
    gl_Position = projectionMatrix * worldMatrix * vec4(position, 1.0);
    exColor = inColor;
}