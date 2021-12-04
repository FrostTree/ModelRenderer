package engine.frame;

import static org.lwjgl.opengl.GL40.*;

public class Frame
{
    private int fboID;
    private int colorTextureID;
    private int depthTextureID;

    protected Frame(int fboID, int colorTextureID, int depthTextureID)
    {
        this.fboID = fboID;
        this.colorTextureID = colorTextureID;
        this.depthTextureID = depthTextureID;
    }

    public void start()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
    }

    public void stop()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int getColorTextureID()
    {
        return colorTextureID;
    }

    public int getDepthTextureID()
    {
        return depthTextureID;
    }
}
