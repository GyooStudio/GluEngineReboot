package com.gyoo.gluengine;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.gyoo.gluengine.Components.ComponentBase;
import com.gyoo.gluengine.Components.RawModel;
import com.gyoo.gluengine.Components.Shaders.HelloTriangleShader;
import com.gyoo.gluengine.Components.Transform;
import com.gyoo.gluengine.Objects.GameObject;
import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector2f;
import com.gyoo.gluengine.Vectors.Vector3f;
import com.gyoo.gluengine.utils.Loader;
import com.gyoo.gluengine.utils.Maths;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    Loader loader;
    Ressources ressources;
    GameObject ball = new GameObject();
    RawModel HelloBall;
    HelloTriangleShader hts;
    Matrix4f projection;
    Matrix4f transform;
    Matrix4f rotation;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        loader = Loader.getLoader();
        ressources = Ressources.getRessources();
        Transform t = new Transform();
        RawModel r = loader.loadAssetModel("Objects/ico.obj");
        r.makeModel();
        hts = new HelloTriangleShader(loader.loadAssetText("Shaders/HelloTriangle.vert"),loader.loadAssetText("Shaders/HelloTriangle.frag"));
        hts.buildShader();
        ball.addComponent(t);
        ball.addComponent(r);
        ball.addComponent(hts);

        t.setPosition( new Vector3f(0,0,-3) );

        projection = new Matrix4f();
        Matrix.perspectiveM(projection.mat,0,70f,1f,0.001f,100f);

        GLES30.glClearColor(0.8f,0.5f,0.2f,1f);
        GLES30.glEnable(GLES30.GL_DEPTH);
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

        hts = ball.getComponent(HelloTriangleShader.COMPONENT_TYPE);
        hts.start();
        RawModel model = ball.getComponent(RawModel.COMPONENT_TYPE);
        Transform transform = ball.getComponent(Transform.COMPONENT_TYPE);
        transform.rotate(new Vector3f(1f,1f,1f));

        GLES30.glBindVertexArray(model.vaoID);
        GLES30.glEnableVertexAttribArray(0);

        hts.loadProjectionMat(projection);
        hts.loadTransformMat(transform.getTransformMatrix());

        GLES30.glDrawElements(GLES30.GL_TRIANGLES,model.vertCount,GLES30.GL_UNSIGNED_INT,0);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glBindVertexArray(0);

        hts.stop();

        Log.w("onDrawFrame","FrameDrawing");
    }
}
