package engine.input;

public class Mouse
{
    protected static int cursorX = 0, cursorY = 0;
    protected static int cursorOffsetX = 0, cursorOffsetY = 0;

    public static void cursorPosCallback(long window, double xpos, double ypos)
    {
        cursorOffsetX = (int)(xpos) - cursorX;
        cursorOffsetY = (int)(ypos) - cursorY;
        cursorX = (int)(xpos);
        cursorY = (int)(ypos) * -1;
    }
}
