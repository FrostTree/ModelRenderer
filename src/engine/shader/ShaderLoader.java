package engine.shader;

import engine.system.Terminate;
import static org.lwjgl.opengl.GL40.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ShaderLoader
{
    public static final int VERTEX = GL_VERTEX_SHADER;
    public static final int FRAGMENT = GL_FRAGMENT_SHADER;

    private static ArrayList<Integer> shaders = new ArrayList<Integer>();

    public static Shader loadShader(String shaderPath, int type)
    {
        return new Shader(compile(getSource(shaderPath), type));
    }

    private static int compile(StringBuilder src, int type)
    {
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, src);
        glCompileShader(shaderID);
        shaders.add(shaderID);

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) Terminate.stopWithMessage("Failed to compile shader:" + "\n" + glGetShaderInfoLog(shaderID));

        return shaderID;
    }

    private static StringBuilder getSource(String shaderPath)
    {
        StringBuilder src = new StringBuilder();

        try
        {
            FileReader fileReader = new FileReader(new File(shaderPath));
            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;

            while((line = reader.readLine()) != null)
            {
                src.append(line + "\n");
            }
        }
        catch(Exception e)
        {
            Terminate.stopWithMessage("Failed to read shader, Path: " + shaderPath);
        }
        return src;
    }

    public static void deleteShaders()
    {
        for(int i = 0; i < shaders.size(); i++)
        {
            glDeleteShader(shaders.get(i));
        }
    }
}
