package com.gyoo.gluengine;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.gyoo.gluengine.Components.GTexture;
import com.gyoo.gluengine.Components.Transformée2D;
import com.gyoo.gluengine.Objets.IUG.IUGQuad;
import com.gyoo.gluengine.Objets.Scène.GluObjet;
import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;
import com.gyoo.gluengine.utils.Chargeur;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class Renderer implements GLSurfaceView.Renderer {

    Chargeur chargeur;
    Ressources ressources;
    long chronoFPS;
    int compteurFPS;
    float FPS;
    IUGQuad quad;
    Transformée2D quadTrans = new Transformée2D();
    Scène scène = new Scène();
    Matrice4f projection;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        chargeur = Chargeur.avoirChargeur();
        ressources = Ressources.avoirRessoucres();

        GTexture texture = chargeur.chargerTextureActif("Textures/fusé.png");
        texture.makeTexture();

        quad = new IUGQuad();

        quadTrans = quad.transformée2D;
        quadTrans.setPosition(new Vecteur3f(600f,0,-0.5f) );
        quadTrans.setScalePt(new Vecteur2f(1f));
        //quad.addTexture(texture);

        scène.ajouterGUIQuad(quad);

        projection = new Matrice4f();
        Matrix.perspectiveM(projection.mat,0,70f,1f,0.001f,100f);

        GLES30.glClearColor(0.8f,0.5f,0.2f,1f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glEnable(GLES30.GL_CULL_FACE);
        GLES30.glCullFace(GLES30.GL_BACK);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glEnable(GLES30.GL_BLEND);
        Log.w("Renderer","Renderer créé");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int largeur, int hauteur) {
        GLES30.glViewport(0,0, largeur, hauteur);
        ressources.dimÉcranPixels = new Vecteur2f( (float) largeur, (float) hauteur );
        Matrix.perspectiveM(projection.mat,0,70f,(float)largeur/(float)hauteur,0.001f,100f);
        Log.w("onSurfaceChanged","largeur : " + largeur + "hauteur : " + hauteur);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        peindre(scène);

        FPS += 1f/( (float)( System.currentTimeMillis() - chronoFPS) / 1000f );
        chronoFPS = System.currentTimeMillis();
        compteurFPS++;
        if(compteurFPS >= 120) {
            Log.w("FPS", FPS/(float) compteurFPS + "");
            compteurFPS = 0;
            FPS = 0f;
        }
    }

    private void peindre(Scène scène){
        for (GluObjet object : scène.gluObjets){
            object.shader.commencer();
        }

        for (IUGQuad quad : scène.IUGQuads) {
            quad.shader.commencer();

            GLES30.glBindVertexArray(quad.mesh.vaoID);
            GLES30.glEnableVertexAttribArray(0);

            if(quad.texture != null){
                GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
                GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, quad.texture.ID);
                quad.shader.aTexture(true);
            }else{
                quad.shader.aTexture(false);
            }

            quad.shader.chargerDimÉcran(ressources.dimÉcranPixels);

            quad.shader.chargerCouleur(quad.shader.color);
            quad.shader.chargerTransformée(quad.transformée2D.getTransformMatrix());

            GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP,0,quad.mesh.vertCount);

            GLES30.glDisableVertexAttribArray(0);
            GLES30.glBindVertexArray(0);

            quad.shader.terminer();
        }
    }
}
