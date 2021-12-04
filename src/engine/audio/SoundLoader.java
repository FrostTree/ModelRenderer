package engine.audio;

import java.util.ArrayList;

import static org.lwjgl.openal.AL11.*;

public class SoundLoader
{
    private static ArrayList<Integer> sounds = new ArrayList<Integer>();

    public static Sound loadSound(Audio audio, float xPos, float yPos, float zPos)
    {
        int sourceID = alGenSources();
        sounds.add(sourceID);
        alSourcei(sourceID, AL_BUFFER, audio.getAudioID());
        alSource3f(sourceID, AL_POSITION, xPos, yPos, zPos);

        return new Sound(sourceID);
    }

    public static void deleteSources()
    {
        for(int i = 0; i < sounds.size(); i++) alDeleteSources(sounds.get(i));
    }
}