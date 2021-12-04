package engine.input;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard
{
    protected static boolean
    W_KEY = false,
    A_KEY = false,
    D_KEY = false,
    S_KEY = false;

    protected static boolean[] keys = new boolean[1000];

    public static void keyCallback(long window, int key, int scancode, int action, int mods)
    {
        if(action == GLFW_PRESS)
        {
            if(key == -1) return;
            keys[key] = true;
        }
        else if(action == GLFW_RELEASE)
        {
            if(key == -1) return;
            keys[key] = false;
        }
    }
}
