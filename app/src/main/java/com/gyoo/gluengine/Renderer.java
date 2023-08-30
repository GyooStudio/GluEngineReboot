package com.gyoo.gluengine;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.gyoo.gluengine.Components.RawModel;
import com.gyoo.gluengine.Components.Shaders.GUIShader;
import com.gyoo.gluengine.Components.Shaders.HelloTriangleShader;
import com.gyoo.gluengine.Components.Transform;
import com.gyoo.gluengine.Components.Transform2D;
import com.gyoo.gluengine.Objects.GameObject;
import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector2f;
import com.gyoo.gluengine.Vectors.Vector3f;
import com.gyoo.gluengine.utils.Loader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    Loader loader;
    Ressources ressources;
    long FPStimer;
    int FPSCounter;
    float FPS;
    GameObject ball = new GameObject();
    GameObject quad = new GameObject();
    Matrix4f projection;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        loader = Loader.getLoader();
        ressources = Ressources.getRessources();
        Transform t = new Transform();
        RawModel r = loader.loadAssetModel("Objects/ico.obj");
        r.makeModel();
        HelloTriangleShader hts = new HelloTriangleShader(loader.loadAssetText("Shaders/HelloTriangle.vert"),loader.loadAssetText("Shaders/HelloTriangle.frag"));
        hts.buildShader();
        ball.addComponent(t);
        ball.addComponent(r);
        ball.addComponent(hts);

        t.setPosition( new Vector3f(0,0,-3) );

        float[] positions = {
                -0.5f,-0.5f,0f,
                0.5f,-0.5f,0f,
                -0.5f,0.5f,0f,

                -0.5f,0.5f,0f,
                0.5f,-0.5f,0f,
                0.5f,0.5f,0f
        };

        r = Loader.loadToVAO(positions);
        Transform2D t2d = new Transform2D();
        t2d.setPosition(new Vector3f(0,0,-0.5f) );
        t2d.setScale(new Vector2f(600f));
        GUIShader gs = new GUIShader(loader.loadAssetText("Shaders/GUI.vert"),loader.loadAssetText("Shaders/GUI.frag"));
        gs.buildShader();

        quad.addComponent(r);
        quad.addComponent(t2d);
        quad.addComponent(gs);

        projection = new Matrix4f();
        Matrix.perspectiveM(projection.mat,0,70f,1f,0.001f,100f);

        GLES30.glClearColor(0.8f,0.5f,0.2f,1f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glEnable(GLES30.GL_CULL_FACE);
        GLES30.glCullFace(GLES30.GL_BACK);
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
        GLES30.glEnable(GLES30.GL_CULL_FACE);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        HelloTriangleShader hts = ball.getComponent(HelloTriangleShader.COMPONENT_TYPE);
        hts.start();
        RawModel model = ball.getComponent(RawModel.COMPONENT_TYPE);
        Transform transform = ball.getComponent(Transform.COMPONENT_TYPE);
        transform.rotate(new Vector3f(1f,1f,1f));

        GLES30.glBindVertexArray(model.vaoID);
        GLES30.glEnableVertexAttribArray(0);

        hts.loadTransformMat( Matrix4f.MultiplyMM( projection,transform.getTransformMatrix() ) );

        GLES30.glDrawElements(GLES30.GL_TRIANGLES,model.vertCount,GLES30.GL_UNSIGNED_INT,0);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glBindVertexArray(0);

        hts.stop();

        GLES30.glDisable(GLES30.GL_CULL_FACE);
        GUIShader gs = quad.getComponent(GUIShader.COMPONENT_TYPE);
        gs.start();
        model = quad.getComponent(RawModel.COMPONENT_TYPE);
        Transform2D t2d = quad.getComponent(Transform2D.COMPONENT_TYPE);
        t2d.setPosition(new Vector3f(600f*(float) Math.cos((double) System.currentTimeMillis()/1000.0),600f*(float) Math.sin((double) System.currentTimeMillis()/1000.0),-0.01f));

        GLES30.glBindVertexArray(model.vaoID);
        GLES30.glEnableVertexAttribArray(0);

        gs.loadTransform( t2d.getTransformMatrix() );
        gs.loadScreenDim(ressources.screenDimPixels);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,model.vertCount);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glBindVertexArray(0);

        gs.stop();


        FPS += 1f/( (float)( System.currentTimeMillis() - FPStimer) / 1000f );
        FPStimer = System.currentTimeMillis();
        FPSCounter++;
        if(FPSCounter >= 120) {
            Log.w("FPS", FPS/(float)FPSCounter + "");
            FPSCounter = 0;
            FPS = 0f;
        }
    }
}
