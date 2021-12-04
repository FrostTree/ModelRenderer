package engine.image;

import java.nio.ByteBuffer;

public class Image
{
    private ByteBuffer imageData;
    private int width;
    private int height;
    private int channels;

    protected Image(ByteBuffer imageData, int width, int height, int channels)
    {
        this.imageData = imageData;
        this.width = width;
        this.height = height;
        this.channels = channels;
    }

    public ByteBuffer getImageData()
    {
        return imageData;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getChannels()
    {
        return channels;
    }
}
