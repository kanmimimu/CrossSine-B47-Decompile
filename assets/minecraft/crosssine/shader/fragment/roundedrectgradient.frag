#version 120

uniform vec2 u_size;
uniform float u_radius;
uniform vec4 u_colorTop;
uniform vec4 u_colorBottom;
uniform float u_shadow;

void main(void)
{
    float adjustedRadius = u_radius * 2.0;

    // Invert the gradient factor so top becomes bottom and vice versa
    float gradientFactor = 1.0 - gl_TexCoord[0].t;

    // Interpolate color between bottom and top (swapped)
    vec4 gradientColor = mix(u_colorTop, u_colorBottom, gradientFactor);

    // Compute alpha with smoothstep shadow
    float alpha = gradientColor.a * smoothstep(u_shadow, -u_shadow,
        length(max((abs(gl_TexCoord[0].st - 0.5) + 0.5) * u_size - u_size + adjustedRadius, 0.0)) - adjustedRadius + (u_shadow - 0.5));

    gl_FragColor = vec4(gradientColor.rgb, alpha);
}
