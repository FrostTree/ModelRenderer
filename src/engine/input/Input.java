package engine.input;

public class Input
{
    public static int cursorX, cursorY;
    public static int cursorOffsetX = 0, cursorOffsetY = 0;

    public static boolean
            W_KEY = false,
            A_KEY = false,
            D_KEY = false,
            S_KEY = false;

    public static boolean[] keys = null;

    public static void update()
    {
        W_KEY = Keyboard.W_KEY;
        A_KEY = Keyboard.A_KEY;
        S_KEY = Keyboard.S_KEY;
        D_KEY = Keyboard.D_KEY;

        cursorX = Mouse.cursorX;
        cursorY = Mouse.cursorY;
        cursorOffsetX = Mouse.cursorOffsetX;
        cursorOffsetY = Mouse.cursorOffsetY;
        keys = Keyboard.keys;
    }
}
