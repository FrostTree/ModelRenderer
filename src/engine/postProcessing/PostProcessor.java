package engine.postProcessing;

import engine.frame.Frame;
import engine.render.Renderer;
import engine.scene.mesh.Model;
import engine.scene.mesh.ModelLoader;
import engine.shader.ShaderProgram;

public class PostProcessor
{
    private static float[] positions = {1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f};
    private static float[] colors = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    private static float[] texCoords = {1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
    private static float[] normals = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};

    private static Model quad = ModelLoader.getModel(positions, colors, texCoords, normals, null);

    public static Frame invertColor(Frame frame)
    {
        return null;
    }

    public static void customEffect(Frame input, ShaderProgram program, Frame output)
    {
        if(output != null)
        {
            output.start();
            Renderer.renderScreen(quad, program, input.getColorTextureID(), input.getDepthTextureID());
            output.stop();
        }
        else Renderer.renderScreen(quad, program, input.getColorTextureID(), input.getDepthTextureID());
    }
}