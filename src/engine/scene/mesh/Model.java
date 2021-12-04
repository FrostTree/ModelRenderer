package engine.scene.mesh;

import engine.shader.ShaderProgram;

public class Model
{
    private int vaoID;
    private int vertexCount;
    private ShaderProgram program;

    public Model(int vaoID, int vertexCount, ShaderProgram program)
    {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.program = program;
    }

    public int getVaoID()
    {
        return vaoID;
    }

    public int getVertexCount()
    {
        return vertexCount;
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
