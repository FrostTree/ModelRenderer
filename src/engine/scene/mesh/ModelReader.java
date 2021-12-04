package engine.scene.mesh;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.util.ArrayList;

public class ModelReader
{
    public static void readModel(String modelpath, ArrayList<Float> positions, ArrayList<Float> texCoords, ArrayList<Float> normals, ArrayList<Float> colors)
    {
        //float[] positions {x, y, z, x, y, z}
        //float[] texCoords {x, y, x, y}
        //float[] normals {x, y, z}
        //float[] colors {r, g, b, a}

        loadModelFile(modelpath, positions, texCoords, normals, colors);
    }

    private static void loadModelFile(String filepath, ArrayList<Float> positions, ArrayList<Float> texCoords, ArrayList<Float> normals, ArrayList<Float> colors)
    {
        AIScene scene = Assimp.aiImportFile(filepath, Assimp.aiProcess_Triangulate);

        System.out.println(scene.mMeshes().limit());System.out.println(scene.mNumMeshes());
        PointerBuffer buffer = scene.mMeshes();

        for(int i = 0; i < buffer.limit(); i++)
        {
            AIMesh mesh = AIMesh.create(buffer.get(i));
            processMesh(mesh, positions, texCoords, normals, colors);
        }
    }

    private static void processMesh(AIMesh mesh, ArrayList<Float> positions, ArrayList<Float> texCoords, ArrayList<Float> normals, ArrayList<Float> colors)
    {
        AIVector3D.Buffer vectors = mesh.mVertices();

        for(int i = 0; i < vectors.limit(); i++)
        {
            AIVector3D vector = vectors.get(i);

            positions.add(vector.x());
            positions.add(vector.y());
            positions.add(vector.z());
        }

        AIVector3D.Buffer coords = mesh.mTextureCoords(0);

        for(int i = 0; i < coords.limit(); i++)
        {
            AIVector3D coord = coords.get(i);

            texCoords.add(coords.x());
            texCoords.add(coords.y());
        }

        AIVector3D.Buffer norms = mesh.mNormals();

        for(int i = 0; i < norms.limit(); i++)
        {
            AIVector3D norm = norms.get(i);

            normals.add(norm.x());
            normals.add(norm.y());
            normals.add(norm.z());
        }

        AIColor4D.Buffer vertexColors = mesh.mColors(0);

        for(int i = 0; i < vertexColors.limit(); i++)
        {
            AIColor4D vertexColor = vertexColors.get(i);

            colors.add(vertexColor.r());
            colors.add(vertexColor.g());
            colors.add(vertexColor.b());
            colors.add(vertexColor.a());
        }
    }
}