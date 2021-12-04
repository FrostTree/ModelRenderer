package engine.audio;

public class Sound
{
    private int soundID;

    protected Sound(int soundID)
    {
        this.soundID = soundID;
    }

    public void play()
    {
        stop();
        SoundPlayer.playSound(soundID);
    }

    public void stop()
    {
        SoundPlayer.stopSound(soundID);
    }

    public void pause()
    {
        SoundPlayer.pauseSound(soundID);
    }

    public void loop(boolean shouldLoop)
    {
        SoundPlayer.loop(soundID, shouldLoop);
    }

    public void setVolume(float gain)
    {
        SoundPlayer.setVolume(soundID, gain);
    }

    public void setPitch(float pitch)
    {
        SoundPlayer.setPitch(soundID, pitch);
    }

    public int getSoundID()
    {
        return soundID;
    }
}