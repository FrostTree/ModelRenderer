import engine.input.Input;
import engine.render.Projection;
import engine.render.Renderer;
import engine.scene.FirstPersonCamera;
import engine.scene.entity.Entity;
import engine.scene.entity.Transform;
import engine.scene.mesh.Model;
import engine.scene.mesh.ModelLoader;
import engine.shader.Shader;
import engine.shader.ShaderLoader;
import engine.shader.ShaderProgram;
import engine.shader.ShaderProgramLoader;
import engine.system.Core;
import engine.system.Terminate;
import engine.window.Window;

public class Main implements Runnable
{
    private Window window;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private FirstPersonCamera fpCam;
    private Entity house;
    private ShaderProgram program;

    public static void main(String[] args)
    {
        new Main().start();
    }

    private void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    private void init()
    {
        Core.initializeEngine();

        window = new Window(WIDTH, HEIGHT, "Engine");
        window.create();
        window.setCursorPos(0, 0);
        window.disableCursor(true);

        Shader vs = ShaderLoader.loadShader("res/Vertex Shader.glsl", ShaderLoader.VERTEX);
        Shader fs = ShaderLoader.loadShader("res/Fragment Shader.glsl", ShaderLoader.FRAGMENT);
        program = ShaderProgramLoader.loadShaderProgram(vs, fs);

        Model mod = ModelLoader.loadModel("res/models/House.dae", program);
        house = new Entity(mod, new Transform(0, 0, 0, -90, 0, 0, 1, 1, 1));

        fpCam = new FirstPersonCamera(0, 0, 5);
    }

    public void run()
    {
        init();

        while(!window.shouldClose())
        {
            update();
            render();
        }

        Terminate.stop();
    }

    private void update()
    {
        window.update();
        fpCam.setLookDir((float)(Input.cursorX) * -0.1f, (float)(Input.cursorY) * 0.1f);

        if(Input.keys[(int)('W')]) fpCam.translateAlongYaw(0, 0, -0.05f);
        if(Input.keys[(int)('D')]) fpCam.translateAlongYaw(0.05f, 0, 0);
        if(Input.keys[(int)('A')]) fpCam.translateAlongYaw(-0.05f, 0, 0);
        if(Input.keys[(int)('S')]) fpCam.translateAlongYaw(0, 0, 0.05f);
        if(Input.keys[(int)('E')]) fpCam.translate(0, 0.05f, 0);
        if(Input.keys[(int)('Q')]) fpCam.translate(0, -0.05f, 0);

        if(Input.keys[256]) Terminate.stop();// if escape key pressed
    }

    private void render()
    {
        Renderer.clear(0.5f, 0.5f, 0.5f);
        house.render(fpCam.getMatrix(), Projection.getPerspectiveMatrix(55, WIDTH, HEIGHT));
        window.render();
    }
}