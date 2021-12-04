package engine.scene.mesh.anim;

import org.joml.Matrix4f;

public class Joint
{
    private int index;
    private String name;
    private Matrix4f offsetMatrix;
    private int[] vertexID;
    private float[] weights;

    private int affectedVertices;

    protected Joint(int index, String name, Matrix4f offsetMatrix, int[] vertexID, float[] weights)
    {
        this.index = index;
        this.name = name;
        this.offsetMatrix = offsetMatrix;
        this.vertexID = vertexID;
        this.weights = weights;

        affectedVertices = vertexID.length;
    }

    public String getName()
    {
        return name;
    }

    public int[] getVertexID()
    {
        return vertexID;
    }

    public float[] getWeights()
    {
        return weights;
    }

    public int getIndex()
    {
        return index;
    }

    public Matrix4f getOffsetTransformation()
    {
        return offsetMatrix;
    }

    public void setTransformation(Matrix4f matrix)
    {
        this.offsetMatrix = matrix;
    }

    public int getAffectedVertices()
    {
        return affectedVertices;
    }
}