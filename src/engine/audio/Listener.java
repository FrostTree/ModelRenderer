package engine.audio;

import engine.scene.FirstPersonCamera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.openal.AL11.*;

public class Listener
{
    public static void update(FirstPersonCamera camera)
    {
        Vector3f position = camera.getPosition();
        alListener3f(AL_POSITION, position.x(), position.y(), position.z());

        Vector3f look = new Vector3f();
        Matrix4f matrix = camera.getMatrix();
        matrix.positiveZ(look).negate();

        Vector3f up = new Vector3f();
        matrix.positiveY(up);

        setOrientation(look, up);
    }

    private static void setOrientation(Vector3f look, Vector3f up)
    {
        float[] orientation = new float[6];
        orientation[0] = look.x();
        orientation[1] = look.y();
        orientation[2] = look.z();
        orientation[3] = up.x();
        orientation[4] = up.y();
        orientation[5] = up.z();

        alListenerfv(AL_ORIENTATION, orientation);
    }
}