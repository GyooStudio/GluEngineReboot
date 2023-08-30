package com.gyoo.gluengine;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.gyoo.gluengine.Objects.RawModel;
import com.gyoo.gluengine.Shaders.HelloTriangleShader;
import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector3f;
import com.gyoo.gluengine.utils.Loader;
import com.gyoo.gluengine.utils.Maths;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    Loader loader;
    RawModel HelloBall;
    HelloTriangleShader hts;
    Matrix4f projection;
    Matrix4f transform;
    Matrix4f rotation;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        loader = Loader.getLoader();
        HelloBall = loader.loadAssetModel("Objects/ico.obj");
        HelloBall.makeModel();
        hts = new HelloTriangleShader(loader.loadAssetText("Shaders/HelloTriangle.vert"),loader.loadAssetText("Shaders/HelloTriangle.frag"));
        hts.buildShader();

        projection = new Matrix4f();
        Matrix.perspectiveM(projection.mat,0,70f,1f,0.001f,100f);

        transform = Maths.createTransformationMatrix(new Vector3f(0f,0f,-3f),new Vector3f(0f), new Vector3f(1f));
        rotation = new Matrix4f();

        GLES30.glClearColor(0.8f,0.5f,0.2f,1f);
        Log.w("Renderer","Renderer created");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES30.glViewport(0,0, width, height);
        Matrix.perspectiveM(projection.mat,0,70f,(float)width/(float)height,0.001f,100f);
        Log.w("onSurfaceChanged","width : " + width + "height : " + height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        rotation.rotate(new Vector3f(1f,1f,0f));

        hts.start();

        GLES30.glBindVertexArray(HelloBall.vaoID);
        GLES30.glEnableVertexAttribArray(0);

        hts.loadProjectionMat(projection);
        hts.loadTransformMat(Matrix4f.MultiplyMM(transform,rotation));

        GLES30.glDrawElements(GLES30.GL_TRIANGLES,HelloBall.vertCount,GLES30.GL_UNSIGNED_INT,0);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glBindVertexArray(0);

        hts.stop();

        Log.w("onDrawFrame","FrameDrawing");
    }
}
