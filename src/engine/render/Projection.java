package engine.render;

import org.joml.Matrix4f;

public class Projection
{
    public static Matrix4f getPerspectiveMatrix(float fovY, int width, int height)
    {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        matrix.perspective((float)(Math.toRadians(fovY)), (float)(width) / (float)(height), 0.1f, 1000, matrix);
        return matrix;
    }

    public static Matrix4f getOrthographicMatrix(float left, float right, float bottem, float top, float zNear, float zFar)
    {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        matrix.ortho(left, right, bottem, top, zNear, zFar);
        return matrix;
    }
}
