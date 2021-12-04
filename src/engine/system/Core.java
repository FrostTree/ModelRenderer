package engine.system;

import engine.audio.AudioDevice;

public class Core
{
    public static void initializeEngine()
    {
        AudioDevice.openDevice();
    }
}