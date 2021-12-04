package engine.scene.mesh.anim;

import engine.shader.ShaderProgram;

public class AnimatedModel
{
    private int vaoID;
    private int vertexCount;
    private Animation[] animations;
    private ShaderProgram program;
    private BindPose bindPose;

    protected AnimatedModel(int vaoID, int vertexCount, BindPose bindPose, Animation[] animations, ShaderProgram program)
    {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.animations = animations;
        this.program = program;
        this.bindPose = bindPose;
    }

    public BindPose getBindPose()
    {
        return bindPose;
    }

    public int getVaoID()
    {
        return vaoID;
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

    public Animation[] getAnimations()
    {
        return animations;
    }

    public ShaderProgram getShaderProgram()
    {
        return program;
    }

    public void setShaderProgram(ShaderProgram program)
    {
        this.program = program;
    }
}