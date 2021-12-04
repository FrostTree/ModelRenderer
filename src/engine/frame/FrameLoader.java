package engine.frame;

import engine.scene.mesh.texture.Texture;
import engine.scene.mesh.texture.TextureLoader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL40.*;

public class FrameLoader
{
    private static ArrayList<Integer> frames = new ArrayList<Integer>();

    public static Frame loadFrame(int width, int height)
    {
        int fboID = glGenFramebuffers();
        frames.add(fboID);
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);

        Texture colorTexture = TextureLoader.loadEmptyColorTexture(width, height);
        Texture depthTexture = TextureLoader.loadEmptyDepthTexture(width, height);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        return new Frame(fboID, colorTexture.getTextureID(), depthTexture.getTextureID());
    }

    public static void deleteFrames()
    {
        for(int i = 0; i < frames.size(); i++) glDeleteFramebuffers(frames.get(i));
    }
}