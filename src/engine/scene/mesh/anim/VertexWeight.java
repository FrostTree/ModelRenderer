package engine.scene.mesh.anim;

public class VertexWeight
{
    private int vertexID;
    private int[] joints;
    private float[] weights;

    private int affectingJoints;

    public VertexWeight(int vertexID)
    {
        this.vertexID = vertexID;
        joints = new int[4];
        weights = new float[4];

        for(int i = 0; i < joints.length; i++)
        {
            joints[i] = -1;
        }

        for(int i = 0; i < weights.length; i++)
        {
            weights[i] = 0;
        }

        affectingJoints = 0;
    }

    public void append(int jointID, float weight)
    {
        if(affectingJoints >= 4) return;

        joints[affectingJoints] = jointID;
        weights[affectingJoints] = weight;
        affectingJoints++;
    }

    public int getJointOne()
    {
        return joints[0];
    }

    public int getJointTwo()
    {
        return joints[1];
    }

    public int getJointThree()
    {
        return joints[2];
    }

    public int getJointFour()
    {
        return joints[3];
    }

    public float getWeightOne()
    {
        return weights[0];
    }

    public float getWeightTwo()
    {
        return weights[1];
    }

    public float getWeightThree()
    {
        return weights[2];
    }

    public float getWeightFour()
    {
        return weights[3];
    }

    public int[] getJoints()
    {
        return joints;
    }

    public float[] getWeights()
    {
        return weights;
    }

    public int getVertexID()
    {
        return vertexID;
    }

    public int getAffectingJoints()
    {
        return affectingJoints;
    }
}