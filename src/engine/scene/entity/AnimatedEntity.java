package engine.scene.entity;

import engine.render.Renderer;
import engine.scene.mesh.anim.AnimatedModel;
import org.joml.Matrix4f;

public class AnimatedEntity
{
    private AnimatedModel animatedModel;
    private Transform transform;

    public AnimatedEntity(AnimatedModel animatedModel, Transform transform)
    {
        this.animatedModel = animatedModel;
        this.transform = transform;
    }

    public void render(Matrix4f camera, Matrix4f projection)
    {
        Renderer.render(this, camera, projection);
    }

    public AnimatedModel getAnimatedModel()
    {
        return animatedModel;
    }

    public Transform getTransform()
    {
        return transform;
    }
}