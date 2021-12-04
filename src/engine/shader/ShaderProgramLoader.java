package engine.shader;

import static org.lwjgl.opengl.GL40.*;

import java.util.ArrayList;

public class ShaderProgramLoader
{
    private static ArrayList<Integer> programs = new ArrayList<Integer>();
    private static ArrayList<Integer> vertexShaders = new ArrayList<Integer>();
    private static ArrayList<Integer> fragmentShaders = new ArrayList<Integer>();

    public static ShaderProgram loadShaderProgram(Shader vs, Shader fs)
    {
        vertexShaders.add(vs.getShaderID());
        fragmentShaders.add(fs.getShaderID());

        int programID = glCreateProgram();
        glAttachShader(programID, vs.getShaderID());
        glAttachShader(programID, fs.getShaderID());
        glLinkProgram(programID);
        glValidateProgram(programID);
        programs.add(programID);

        return new ShaderProgram(programID);
    }

    public static void deletePrograms()
    {
        for(int index = 0; index < programs.size(); index++)
        {
            glDetachShader(programs.get(index), vertexShaders.get(index));
            glDetachShader(programs.get(index), fragmentShaders.get(index));
            glDeleteProgram(programs.get(index));
        }
    }
}
