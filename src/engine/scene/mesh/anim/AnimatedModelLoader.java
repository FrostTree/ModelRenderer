package engine.scene.mesh.anim;

import engine.scene.mesh.ModelReader;
import engine.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL40.*;

public class AnimatedModelLoader
{
    private static ArrayList<Integer> vao = new ArrayList<Integer>();
    private static ArrayList<Integer> vbo = new ArrayList<Integer>();

    public static AnimatedModel loadAnimatedModel(String modelPath, ShaderProgram program)
    {
        ArrayList<Float> positions = new ArrayList<Float>();
        ArrayList<Float> colors = new ArrayList<Float>();
        ArrayList<Float> texCoords = new ArrayList<Float>();
        ArrayList<Float> normals = new ArrayList<Float>();
        ArrayList<Integer> jointsID = new ArrayList<Integer>();
        ArrayList<Float> weights = new ArrayList<Float>();
        ArrayList<Animation> animations = new ArrayList<Animation>();
        ArrayList<Matrix4f> bindPoseTransforms = new ArrayList<Matrix4f>();

        ModelReader.readModel(modelPath, positions, colors, texCoords, normals);
        AnimationReader.readAnimation(modelPath, jointsID, weights, animations, bindPoseTransforms, positions.size() / 3);
        BindPose bindPose = new BindPose(listToArrayMatrix(bindPoseTransforms));

        return getAnimatedModel(listToArray(positions), listToArray(colors), listToArray(texCoords), listToArray(normals), listToArrayInt(jointsID), listToArray(weights), bindPose, program);
    }

    private static AnimatedModel getAnimatedModel(float[] positions, float[] colors, float[] texCoords, float[] normals, int[] jointID, float[] weights, BindPose bindPose, ShaderProgram program)
    {
        int vaoID = createVao();
        storeInAttribute(0, 3, positions);
        storeInAttribute(1, 4, colors);
        storeInAttribute(2, 2, texCoords);
        storeInAttribute(3, 3, normals);
        storeInAttributeInt(4, 4, jointID);
        storeInAttribute(5, 4, weights);
        unbind();

        return new AnimatedModel(vaoID, positions.length / 3, bindPose, null, program);
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

    private static void storeInAttributeInt(int location, int vecSize, int[] data)
    {
        int vboID = glGenBuffers();
        vbo.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        IntBuffer buffer = getIntBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribIPointer(location, vecSize, GL_INT, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static FloatBuffer getFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    private static IntBuffer getIntBuffer(int[] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
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

    private static int[] listToArrayInt(ArrayList<Integer> list)
    {
        int[] array = new int[list.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = list.get(i);
        }
        return array;
    }

    private static Matrix4f[] listToArrayMatrix(ArrayList<Matrix4f> list)
    {
        Matrix4f[] array = new Matrix4f[list.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = list.get(i);
        }
        return array;
    }
}