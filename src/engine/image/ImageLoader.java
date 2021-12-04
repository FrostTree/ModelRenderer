package engine.image;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class ImageLoader
{
    private static ArrayList<ByteBuffer> images = new ArrayList<ByteBuffer>();

    public static Image loadImage(String imagePath)
    {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer imageData = STBImage.stbi_load(imagePath, width, height, channels, 0);
        images.add(imageData);

        return new Image(imageData, width.get(0), height.get(0), channels.get(0));
    }

    public static void deleteImages()
    {
        for(int i = 0; i < images.size(); i++) STBImage.stbi_image_free(images.get(i));
    }
}
