package engine.scene.mesh.texture;

public class Texture
{
    private int textureID;

    protected Texture(int textureID)
    {
        this.textureID = textureID;
    }

    public int getTextureID()
    {
        return textureID;
    }
}
