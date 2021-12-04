package engine.scene;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class FirstPersonCamera
{
    private float pitch = 0, yaw = 0;
    private float roll = 0;

    private Vector3f position;
    private Vector3f orientation = new Vector3f(0, 1, 0);

    public FirstPersonCamera(float xPos, float yPos, float zPos)
    {
        position = new Vector3f(xPos, yPos, zPos);
    }

    public void translate(float xPos, float yPos, float zPos)
    {
        position.add(new Vector3f(xPos, yPos, zPos));
    }

    public void translateAlongYaw(float xPos, float yPos, float zPos)
    {
        Vector3f offset = new Vector3f(xPos, yPos, zPos);
        offset.rotateY((float)(Math.toRadians(yaw)), offset);
        position.add(offset);
    }

    public void setPosition(float xPos, float yPos, float zPos)
    {
        position = new Vector3f(xPos, yPos, zPos);
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void rotateLookDir(float yawOffset, float pitchOffset)
    {
        pitch += pitchOffset;
        yaw += yawOffset;
    }

    public void setLookDir(float yaw, float pitch)
    {
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public void setPitchRestriction(float restrictionTop, float restrictionBottom)
    {

    }

    public float getPitch()
    {
        return pitch;
    }

    public float getYaw()
    {
        return yaw;
    }

    public void rotateOrientation(float rotation)
    {
        roll += rotation;
    }

    public void setOrientation(float rotation)
    {
        roll = rotation;
    }

    public float getOrientation()
    {
        return roll;
    }

    public Matrix4f getMatrix()
    {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();

        Vector3f lookPoint = new Vector3f(0, 0, -1);
        lookPoint.rotateX((float)(Math.toRadians(pitch)), lookPoint);
        lookPoint.rotateY((float)(Math.toRadians(yaw)), lookPoint);
        lookPoint.add(position, lookPoint);

        orientation.rotateZ((float)(Math.toRadians(roll)), orientation);

        matrix.lookAt(position, lookPoint, orientation, matrix);
        return matrix;
    }
}
