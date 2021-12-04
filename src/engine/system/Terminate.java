package engine.system;

import engine.audio.AudioDevice;
import engine.audio.AudioLoader;
import engine.audio.SoundLoader;
import engine.frame.FrameLoader;
import engine.image.ImageLoader;
import engine.scene.mesh.ModelLoader;
import engine.scene.mesh.anim.AnimatedModelLoader;
import engine.scene.mesh.texture.TextureLoader;
import engine.shader.ShaderLoader;
import engine.shader.ShaderProgramLoader;
import engine.window.Window;

public class Terminate
{
    public static void stop()
    {
        SoundLoader.deleteSources();
        AudioLoader.deleteAudioBuffers();
        AudioDevice.closeDevice();
        Window.terminate();
        TextureLoader.deleteTextures();
        ImageLoader.deleteImages();
        FrameLoader.deleteFrames();
        ModelLoader.deleteModels();
        AnimatedModelLoader.deleteModels();
        ShaderProgramLoader.deletePrograms();
        ShaderLoader.deleteShaders();
        System.exit(0);
    }

    public static void stopWithMessage(String message)
    {
        System.out.println(message);
        stop();
    }
}
