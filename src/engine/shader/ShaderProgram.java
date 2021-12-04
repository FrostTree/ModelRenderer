package engine.shader;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL40.*;

public class ShaderProgram
{
    public int programID;

    public ShaderProgram(int programID)
    {
        this.programID = programID;
    }

    public void start()
    {
        glUseProgram(programID);
    }

    public void uploadMatrix4f(String varName, Matrix4f matrix)
    {
        int location = glGetUniformLocation(programID, varName);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }

    public void uploadTexture(String varName, int slot)
    {
        int location = glGetUniformLocation(programID, varName);
        glUniform1i(location, slot);
    }

    public void stop()
    {
        glUseProgram(0);
    }
}
