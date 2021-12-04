package engine.audio;

import static org.lwjgl.openal.AL11.*;

public class SoundPlayer
{
    protected static void playSound(int soundID)
    {
        alSourcePlay(soundID);
    }

    protected static void stopSound(int soundID)
    {
        alSourceStop(soundID);
    }

    protected static void pauseSound(int soundID)
    {
        alSourcePause(soundID);
    }

    protected static void loop(int soundID, boolean shouldLoop)
    {
        alSourcei(soundID, AL_LOOPING, shouldLoop ? AL_TRUE : AL_FALSE);
    }

    protected static void setVolume(int soundID, float gain)
    {
        alSourcef(soundID, AL_GAIN, gain);
    }

    protected static void setPitch(int soundID, float pitch)
    {
        alSourcef(soundID, AL_PITCH, pitch);
    }
}