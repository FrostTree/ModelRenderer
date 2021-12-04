package engine.audio;

import engine.system.Terminate;
import org.lwjgl.BufferUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.openal.AL11.*;

public class AudioLoader
{
    private static ArrayList<Integer> audioBuffers = new ArrayList<Integer>();

    public static Audio loadAudio(String audioPath)
    {
        int format = -1;
        int sampleRate = 0;
        ByteBuffer audioBuffer = null;
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(audioPath));
            AudioFormat audioFormat = stream.getFormat();
            if(audioFormat.getChannels() == 1)
            {
                if(audioFormat.getSampleSizeInBits() == 8) format = AL_FORMAT_MONO8;
                else format = AL_FORMAT_MONO16;
            }
            else if(audioFormat.getChannels() == 2)
            {
                if(audioFormat.getSampleSizeInBits() == 8) format = AL_FORMAT_STEREO8;
                else format = AL_FORMAT_STEREO16;
            }

            byte[] audioData = stream.readAllBytes();
            audioBuffer = BufferUtils.createByteBuffer(audioData.length);
            audioBuffer.put(audioData);
            audioBuffer.flip();

            sampleRate = (int)(audioFormat.getSampleRate());
        }
        catch(Exception e)
        {
            Terminate.stopWithMessage("ERROR: could not find audio file!");
        }

        int audioBufferID = alGenBuffers();
        audioBuffers.add(audioBufferID);
        alBufferData(audioBufferID, format, audioBuffer, sampleRate);

        return new Audio(audioBufferID);
    }

    public static void deleteAudioBuffers()
    {
        for(int i = 0; i < audioBuffers.size(); i++) alDeleteBuffers(audioBuffers.get(i));
    }
}