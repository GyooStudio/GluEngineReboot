package com.gyoo.gluengine;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.gyoo.gluengine.Components.GTexture;
import com.gyoo.gluengine.Components.RawModel;
import com.gyoo.gluengine.Components.Shaders.GUIShader;
import com.gyoo.gluengine.Components.Transform2D;
import com.gyoo.gluengine.Objects.GUI.GUIQuad;
import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector2f;
import com.gyoo.gluengine.Vectors.Vector3f;
import com.gyoo.gluengine.Vectors.Vector4f;
import com.gyoo.gluengine.utils.Loader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    Loader loader;
    Ressources ressources;
    long FPStimer;
    int FPSCounter;
    float FPS;
    GUIQuad quad;
    Transform2D quadTrans = new Transform2D();
    Scene scene = new Scene();
    Matrix4f projection;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        loader = Loader.getLoader();
        ressources = Ressources.getRessources();

        GTexture texture = loader.loadAssetTexture("Textures/fusé.png");
        texture.makeTexture();

        quad = new GUIQuad();

        quadTrans = quad.transform2D;
        quadTrans.setPosition(new Vector3f(0,0,-0.5f) );
        quadTrans.setScale(new Vector2f(600f));
        quad.addTexture(texture);

        scene.addGUIQuad(quad);

        projection = new Matrix4f();
        Matrix.perspectiveM(projection.mat,0,70f,1f,0.001f,100f);

        GLES30.glClearColor(0.8f,0.5f,0.2f,1f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glEnable(GLES30.GL_CULL_FACE);
        GLES30.glCullFace(GLES30.GL_BACK);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glEnable(GLES30.GL_BLEND);
        Log.w("Renderer","Renderer created");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES30.glViewport(0,0, width, height);
        ressources.screenDimPixels = new Vector2f( (float) width, (float) height );
        Matrix.perspectiveM(projection.mat,0,70f,(float)width/(float)height,0.001f,100f);
        Log.w("onSurfaceChanged","width : " + width + "height : " + height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        render(scene);

        FPS += 1f/( (float)( System.currentTimeMillis() - FPStimer) / 1000f );
        FPStimer = System.currentTimeMillis();
        FPSCounter++;
        if(FPSCounter >= 120) {
            Log.w("FPS", FPS/(float)FPSCounter + "");
            FPSCounter = 0;
            FPS = 0f;
        }
    }

    private void render(Scene scene){
        for (GUIQuad quad : scene.guiQuads) {
            quad.shader.start();

            GLES30.glBindVertexArray(quad.mesh.vaoID);
            GLES30.glEnableVertexAttribArray(0);

            if(quad.texture != null){
                GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
                GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, quad.texture.ID);
                quad.shader.isTextured(true);
            }else{
                quad.shader.isTextured(false);
            }

            quad.shader.loadScreenDim(ressources.screenDimPixels);

            quad.shader.loadColor(quad.shader.color);
            quad.shader.loadTransform(quad.transform2D.getTransformMatrix());

            GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP,0,quad.mesh.vertCount);

            GLES30.glDisableVertexAttribArray(0);
            GLES30.glBindVertexArray(0);

            quad.shader.stop();
        }
    }
}
