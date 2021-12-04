#version 400 core

layout (location = 0) in vec3 positions;
layout (location = 1) in vec4 colors;
layout (location = 2) in vec2 texCoords;
layout (location = 3) in vec3 normals;

out vec4 color;
out vec3 normal;
out vec3 worldSpacePos;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    gl_Position = projection * view * transform * vec4(positions, 1);
    worldSpacePos = (transform * vec4(positions, 1)).xyz;
    color = colors;
    normal = normals;
}