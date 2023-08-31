package com.gyoo.gluengine;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.gyoo.gluengine.Components.RawModel;
import com.gyoo.gluengine.Components.Shaders.GUIShader;
import com.gyoo.gluengine.Components.Shaders.HelloTriangleShader;
import com.gyoo.gluengine.Components.Shaders.ShaderProgram;
import com.gyoo.gluengine.Components.Transform;
import com.gyoo.gluengine.Components.Transform2D;
import com.gyoo.gluengine.Objects.GameObject;
import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector2f;
import com.gyoo.gluengine.Vectors.Vector3f;
import com.gyoo.gluengine.Vectors.Vector4f;
import com.gyoo.gluengine.utils.Loader;
import com.gyoo.gluengine.utils.Maths;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    Loader loader;
    Ressources ressources;
    long FPStimer;
    int FPSCounter;
    float FPS;
    GameObject ball = new GameObject();
    GameObject quad = new GameObject();
    Transform2D quadTrans = new Transform2D();
    GameObject child = new GameObject();
    GameObject child2 = new GameObject();
    Scene scene = new Scene();
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
        quadTrans = new Transform2D();
        quadTrans.setPosition(new Vector3f(0,0,-0.5f) );
        quadTrans.setScale(new Vector2f(10f));
        GUIShader gs = new GUIShader(loader.loadAssetText("Shaders/GUI.vert"),loader.loadAssetText("Shaders/GUI.frag"));
        gs.buildShader();

        quad.addComponent(r);
        quad.addComponent(quadTrans);
        quad.addComponent(gs);
        quad.isTransparent = true;

        scene.addObject(ball);
        scene.addObject(quad);

        GameObject parent = quad;
        GameObject child;
        for (int i = 0; i < 50; i++) {
            child = new GameObject();

            r = Loader.loadToVAO(positions);
            Transform2D t2d = new Transform2D();
            t2d.setPosition( new Vector3f(1f,0f,-0.01f));
            t2d.setScale(new Vector2f(1.1f));
            gs = new GUIShader(loader.loadAssetText("Shaders/GUI.vert"),loader.loadAssetText("Shaders/GUI.frag"));
            gs.buildShader();
            gs.color = new Vector4f(1f,1f,1f,0.1f);

            child.addComponent(r);
            child.addComponent(t2d);
            child.addComponent(gs);
            child.parent(parent);
            child.isTransparent = true;

            parent = child;

            scene.addObject(child);
        }

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

        for (GameObject g : scene.gameObjects) {
            Transform2D t2d = g.getComponent(Transform2D.COMPONENT_TYPE);
            if(t2d != null){
                t2d.rotate(2f);
            }
        }

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
        for (GameObject object: scene.gameObjects) {
            RawModel mesh = object.getComponent(RawModel.COMPONENT_TYPE);
            Transform transform = object.getComponent(Transform.COMPONENT_TYPE);
            Transform2D transform2D = object.getComponent(Transform2D.COMPONENT_TYPE);
            HelloTriangleShader htShader = object.getComponent(HelloTriangleShader.COMPONENT_TYPE);
            GUIShader guiShader = object.getComponent(GUIShader.COMPONENT_TYPE);
            /*if(object.isTransparent){
                GLES30.glEnable(GLES30.GL_BLEND);
            }else{
                GLES30.glDisable(GLES30.GL_BLEND);
            }*/

            if(mesh != null){
                if ( htShader != null){

                    htShader.start();

                    GLES30.glBindVertexArray(mesh.vaoID);
                    GLES30.glEnableVertexAttribArray(0);

                    if(transform == null && transform2D == null){
                        htShader.loadTransformMat(new Matrix4f());
                    } else if(transform2D != null && transform == null){
                        htShader.loadTransformMat( transform2D.getTransformMatrix() );
                    } else {
                        htShader.loadTransformMat( Matrix4f.MultiplyMM( projection,transform.getTransformMatrix() ) );
                    }

                    if(mesh.indices != null){
                        GLES30.glDrawElements(GLES30.GL_TRIANGLES,mesh.vertCount,GLES30.GL_UNSIGNED_INT,0);
                    }else{
                        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,mesh.vertCount);
                    }

                    GLES30.glDisableVertexAttribArray(0);
                    GLES30.glBindVertexArray(0);

                    htShader.stop();

                } else if(guiShader != null){

                    guiShader.start();

                    GLES30.glBindVertexArray(mesh.vaoID);
                    GLES30.glEnableVertexAttribArray(0);

                    if(transform == null && transform2D == null){
                        guiShader.loadTransform(new Matrix4f());
                    } else if(transform2D != null && transform == null){
                        guiShader.loadTransform( transform2D.getTransformMatrix() );
                    } else {
                        guiShader.loadTransform( Matrix4f.MultiplyMM( projection,transform.getTransformMatrix() ) );
                    }

                    guiShader.loadScreenDim(ressources.screenDimPixels);
                    guiShader.loadColor(guiShader.color);

                    if(mesh.indices != null){
                        GLES30.glDrawElements(GLES30.GL_TRIANGLES,mesh.vertCount,GLES30.GL_UNSIGNED_INT,0);
                    }else{
                        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,mesh.vertCount);
                    }

                    GLES30.glDisableVertexAttribArray(0);
                    GLES30.glBindVertexArray(0);

                    guiShader.stop();

                }
            }
        }
    }
}
