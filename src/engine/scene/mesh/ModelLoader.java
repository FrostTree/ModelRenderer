package engine.scene.mesh;

import engine.shader.ShaderProgram;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL40.*;

public class ModelLoader
{
    private static ArrayList<Integer> vao = new ArrayList<Integer>();
    private static ArrayList<Integer> vbo = new ArrayList<Integer>();

    public static Model loadModel(String modelPath, ShaderProgram program)
    {
        ArrayList<Float> positions = new ArrayList<Float>();
        ArrayList<Float> colors = new ArrayList<Float>();
        ArrayList<Float> texCoords = new ArrayList<Float>();
        ArrayList<Float> normals = new ArrayList<Float>();

        ModelReader.readModel(modelPath, positions, texCoords, normals, colors);

        return getModel(listToArray(positions), listToArray(colors), listToArray(texCoords), listToArray(normals), program);
    }

    public static Model getModel(float[] positions, float[] colors, float[] texCoords, float[] normals, ShaderProgram program)
    {
        int vaoID = createVao();
        storeInAttribute(0, 3, positions);
        storeInAttribute(1, 4, colors);
        storeInAttribute(2, 2, texCoords);
        storeInAttribute(3, 3, normals);
        unbind();

        return new Model(vaoID, positions.length / 3, program);
    }

    private static int createVao()
    {
        int vaoID = glGenVertexArrays();
        vao.add(vaoID);
        glBindVertexArray(vaoID);

        return vaoID;
    }

    private static void storeInAttribute(int location, int vecSize, float[] data)
    {
        int vboID = glGenBuffers();
        vbo.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        FloatBuffer buffer = getFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(location, vecSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static FloatBuffer getFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    private static void unbind()
    {
        glBindVertexArray(0);
    }

    public static void deleteModels()
    {
        for(int i = 0; i < vao.size(); i++) glDeleteVertexArrays(vao.get(i));
        for(int i = 0; i < vbo.size(); i++) glDeleteBuffers(vbo.get(i));
    }

    private static float[] listToArray(ArrayList<Float> list)
    {
        float[] array = new float[list.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = list.get(i);
        }
        return array;
    }
}
