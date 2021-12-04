package engine.audio;

import engine.system.Terminate;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.ALC11.*;

public class AudioDevice
{
    private static long device;
    private static long context;

    public static void openDevice()
    {
        device = alcOpenDevice((ByteBuffer)null);
        if(device == 0) Terminate.stopWithMessage("Could not open audio device.");

        ALCCapabilities deviceCapabilities = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer)null);
        if(context == 0) Terminate.stopWithMessage("Failed to generate audio context.");

        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCapabilities);
    }

    public static void closeDevice()
    {
        alcCloseDevice(device);
        alcDestroyContext(context);
    }
}