package engine.audio;

public class Audio
{
    private int audioID;

    protected Audio(int audioID)
    {
        this.audioID = audioID;
    }

    public int getAudioID()
    {
        return audioID;
    }
}