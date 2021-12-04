package engine.scene.mesh.anim;

import org.joml.Matrix4f;

public class BindPose
{
    private Matrix4f[] transforms;

    public BindPose(Matrix4f[] transforms)
    {
        this.transforms = transforms;
    }

    public Matrix4f[] getTransforms()
    {
        return transforms;
    }

    public Matrix4f getTransform(int index)
    {
        return transforms[index];
    }

    public int getTransformsAmount()
    {
        return transforms.length;
    }
}