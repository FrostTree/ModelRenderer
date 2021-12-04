package engine.scene.mesh.texture;

import engine.image.Image;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL40.*;

public class TextureLoader
{
    private static ArrayList<Integer> textures = new ArrayList<Integer>();
    private static ArrayList<ByteBuffer> loadedImages = new ArrayList<ByteBuffer>();

    public static Texture loadTexture(Image image)
    {
        int textureID = glGenTextures();
        textures.add(textureID);

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        if(image.getChannels() == 4)  glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image.getImageData());
        else glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image.getWidth(), image.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, image.getImageData());

        glBindTexture(GL_TEXTURE_2D, 0);

        return new Texture(textureID);
    }

    public static Texture loadTexture(String imagePath)
    {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer imageData = STBImage.stbi_load(imagePath, width, height, channels, 0);
        loadedImages.add(imageData);

        int textureID = glGenTextures();
        textures.add(textureID);

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        if(channels.get(0) == 4)  glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);
        else glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, imageData);

        glBindTexture(GL_TEXTURE_2D, 0);

        return new Texture(textureID);
    }

    public static Texture loadEmptyColorTexture(int width, int height)
    {
        int textureID = glGenTextures();
        textures.add(textureID);
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureID, 0);

        glBindTexture(GL_TEXTURE_2D, 0);

        return new Texture(textureID);
    }

    public static Texture loadEmptyDepthTexture(int width, int height)
    {
        int textureID = glGenTextures();
        textures.add(textureID);
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_UNSIGNED_BYTE, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, textureID, 0);

        glBindTexture(GL_TEXTURE_2D, 0);

        return new Texture(textureID);
    }

    public static void deleteTextures()
    {
        for(int i = 0; i < textures.size(); i++) glDeleteTextures(textures.get(i));
        for(int i = 0; i < loadedImages.size(); i++) STBImage.stbi_image_free(loadedImages.get(i));
    }
}