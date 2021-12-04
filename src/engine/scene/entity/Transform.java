package engine.scene.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform
{
    public Matrix4f transformMatrix;

    public Transform(Matrix4f transformMatrix)
    {
        this.transformMatrix = transformMatrix;
    }

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale)
    {
        transformMatrix = getTransformMatrix(position, rotation, scale);
    }

    public Transform(float xPos, float yPos, float zPos, float xRot, float yRot, float zRot, float xScl, float yScl, float zScl)
    {
        transformMatrix = getTransformMatrix(new Vector3f(zPos, yPos, zPos), new Vector3f(xRot, yRot, zRot), new Vector3f(xScl, yScl, zScl));
    }

    private Matrix4f getTransformMatrix(Vector3f position, Vector3f rotation, Vector3f scale)
    {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        matrix.translate(position, matrix);
        matrix.rotateX((float)(Math.toRadians(rotation.x())), matrix);
        matrix.rotateY((float)(Math.toRadians(rotation.y())), matrix);
        matrix.rotateZ((float)(Math.toRadians(rotation.z())), matrix);
        matrix.scale(scale, matrix);

        return matrix;
    }

    public Matrix4f getMatrix()
    {
        return transformMatrix;
    }
}
