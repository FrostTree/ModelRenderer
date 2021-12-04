package engine.window;

import engine.input.Input;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.system.Terminate;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.glfw.GLFW.*;

public class Window
{
    private int width, height;
    private String title;
    private long windowID;

    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create()
    {
        if(!glfwInit()) Terminate.stopWithMessage("Failed to initialize glfw.");

        glfwWindowHint(GLFW_SAMPLES, 8);
        windowID = glfwCreateWindow(width, height, title, 0, 0);
        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(1);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(windowID, (vidMode.width() - width) /2, (vidMode.height() - height) /2);
        glfwShowWindow(windowID);

        GL.createCapabilities();
        GL30.glEnable(GL30.GL_MULTISAMPLE);

        createInput();
    }

    private void createInput()
    {
        glfwSetKeyCallback(windowID, Keyboard::keyCallback);
        glfwSetCursorPosCallback(windowID, Mouse::cursorPosCallback);
    }

    public void disableCursor(boolean isDisabled)
    {
        int status = GLFW_CURSOR_NORMAL;
        if(isDisabled) status = GLFW_CURSOR_DISABLED;
        glfwSetInputMode(windowID, GLFW_CURSOR, status);

    }

    public void update()
    {
        glfwPollEvents();
        Input.update();
    }

    public void render()
    {
        glfwSwapBuffers(windowID);
    }

    public void setTitle(String title)
    {
        glfwSetWindowTitle(windowID, title);
    }

    public boolean shouldClose()
    {
        return glfwWindowShouldClose(windowID);
    }

    public void setCursorPos(int xPos, int yPos)
    {
        glfwSetCursorPos(windowID, xPos, yPos);
    }

    public static void terminate()
    {
        glfwTerminate();
    }
}
