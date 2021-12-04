#version 400 core

in vec4 color;
in vec3 normal;
in vec3 worldSpacePos;

out vec4 fragColor;

void main()
{
    vec3 lightColor = vec3(0.8, 0.8, 0.8);
    vec3 lightPos = vec3(10, 10, 10);

    vec3 finalColor = vec3(color.xyz);

    float ambientStrength = 0.4;
    vec3 ambient = lightColor * ambientStrength;

    vec3 lightDir = normalize(lightPos - worldSpacePos);
    float diffuseStrength = max(dot(normalize(normal), lightDir), 0);
    vec3 diffuse = diffuseStrength * lightColor;

    fragColor = vec4((ambient + diffuse) * finalColor, 1);
    //fragColor = color;
}