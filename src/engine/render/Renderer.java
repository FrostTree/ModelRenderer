package engine.render;

import engine.scene.entity.AnimatedEntity;
import engine.scene.entity.Entity;
import engine.scene.mesh.Model;
import engine.shader.ShaderProgram;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL40.*;

public class Renderer
{
    public static final int WIREFRAME = 0;

    private static boolean[] hints = new boolean[1];

    public static void clear(float r, float g, float b)
    {
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(r, g, b, 1);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void setRenderHints(int hint, boolean val)
    {
        hints[hint] = val;
    }

    public static void render(Entity entity, Matrix4f camera, Matrix4f projection)
    {
        if(hints[WIREFRAME]) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        else glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        entity.getModel().getShaderProgram().start();

        entity.getModel().getShaderProgram().uploadMatrix4f("transform", entity.getTransform().getMatrix());
        entity.getModel().getShaderProgram().uploadMatrix4f("view", camera);
        entity.getModel().getShaderProgram().uploadMatrix4f("projection", projection);

        glBindVertexArray(entity.getModel().getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glDrawArrays(GL_TRIANGLES, 0, entity.getModel().getVertexCount());
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glBindVertexArray(0);

        entity.getModel().getShaderProgram().stop();
    }

    public static void render(AnimatedEntity entity, Matrix4f camera, Matrix4f projection)
    {
        if(hints[WIREFRAME]) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        else glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        entity.getAnimatedModel().getShaderProgram().start();

        entity.getAnimatedModel().getShaderProgram().uploadMatrix4f("transform", entity.getTransform().getMatrix());
        entity.getAnimatedModel().getShaderProgram().uploadMatrix4f("view", camera);
        entity.getAnimatedModel().getShaderProgram().uploadMatrix4f("projection", projection);

        int jointTransformAmount = entity.getAnimatedModel().getBindPose().getTransformsAmount();
        for(int i = 0; i < jointTransformAmount; i++)
        {
            Matrix4f matrix = entity.getAnimatedModel().getBindPose().getTransform(i);
            entity.getAnimatedModel().getShaderProgram().uploadMatrix4f("jointTransformations[" + String.valueOf(i) + "]", matrix);
        }

        glBindVertexArray(entity.getAnimatedModel().getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glEnableVertexAttribArray(4);
        glEnableVertexAttribArray(5);
        glDrawArrays(GL_TRIANGLES, 0, entity.getAnimatedModel().getVertexCount());
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glDisableVertexAttribArray(4);
        glDisableVertexAttribArray(5);
        glBindVertexArray(0);

        entity.getAnimatedModel().getShaderProgram().stop();
    }

    public static void renderScreen(Model model, ShaderProgram program, int colorTextureID, int depthTextureID)
    {
        program.start();

        program.uploadTexture("colorTexture", 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, colorTextureID);

        program.uploadTexture("depthTexture", 1);
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, depthTextureID);

        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount());
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glBindVertexArray(0);

        program.stop();
    }
}
