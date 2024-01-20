package com.gyoo.gluengine;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.gyoo.gluengine.Components.GTexture;
import com.gyoo.gluengine.Components.Transformée2D;
import com.gyoo.gluengine.Objets.IUG.IUGQuad;
import com.gyoo.gluengine.Objets.Scène.Caméra;
import com.gyoo.gluengine.Objets.Scène.GluObjet;
import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;
import com.gyoo.gluengine.utils.Chargeur;
import com.gyoo.gluengine.utils.Maths;

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
    int frameBuffer = 0;
    int dim = 0;
    private GTexture textureTest;
    private Caméra caméra = new Caméra();
    private Terrain terrain = new Terrain();
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        chargeur = Chargeur.avoirChargeur();
        ressources = Ressources.avoirRessoucres();

        projection = new Matrice4f();
        Matrix.perspectiveM(projection.mat,0,70f,ressources.dimÉcranPixels.x/ressources.dimÉcranPixels.y,0.25f,100f);
        caméra.position = new Vecteur3f(105,105,105);
        caméra.rotation = Vecteur3f.dirigerVers(caméra.position,new Vecteur3f(0),0);
        terrain.construireTerrain();
        GluObjet cube = new GluObjet( terrain.avoirModèle() );
        cube.transformée.positionner(new Vecteur3f(0,0,0));
        scène.ajouterGluObject(cube);

        int[] frameBuffer = new int[1];
        GLES30.glGenFramebuffers(1,frameBuffer,0);
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER,frameBuffer[0]);
        GTexture frame = new GTexture((int)ressources.dimÉcranPixels.x/2,(int)ressources.dimÉcranPixels.x/2,false,true,false,false, false);

        int[] depthBuffer = new int[1];
        GLES30.glGenRenderbuffers(1,depthBuffer,0);
        GLES30.glBindRenderbuffer(GLES30.GL_RENDERBUFFER,depthBuffer[0]);
        GLES30.glRenderbufferStorage(GLES30.GL_RENDERBUFFER,GLES30.GL_DEPTH_COMPONENT16,(int)ressources.dimÉcranPixels.x/2,(int)ressources.dimÉcranPixels.x/2);
        GLES30.glFramebufferRenderbuffer(GLES30.GL_FRAMEBUFFER,GLES30.GL_DEPTH_ATTACHMENT,GLES30.GL_RENDERBUFFER,depthBuffer[0]);


        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, frame.ID, 0);
        int[] drawBuffer = new int[]{
                GLES30.GL_COLOR_ATTACHMENT0};
        GLES30.glDrawBuffers(1, drawBuffer, 0);

        if (GLES30.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER) != GLES30.GL_FRAMEBUFFER_COMPLETE) {
            Log.e("frameBuffer", "frameBuffer failed " + GLES30.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER));
        }

        this.frameBuffer = frameBuffer[0];

        GLES30.glClearColor(0.8f,0.5f,0.2f,1f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //GLES30.glEnable(GLES30.GL_CULL_FACE);
        //GLES30.glCullFace(GLES30.GL_BACK);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glEnable(GLES30.GL_BLEND);
        Log.w("Renderer","Renderer créé");

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int largeur, int hauteur) {
        GLES30.glViewport(0,0, largeur/2, hauteur/2);
        ressources.dimÉcranPixels = new Vecteur2f( (float) largeur, (float) hauteur );
        dim = (int)Math.min(ressources.dimÉcranPixels.x,ressources.dimÉcranPixels.y)/2;
        GTexture frame = new GTexture(dim,dim,false,true,false,false, false);
        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, frame.ID, 0);
        Matrix.perspectiveM(projection.mat,0,70f,(float)largeur/(float)hauteur,0.25f,100f);
        Log.w("onSurfaceChanged","largeur : " + largeur + "hauteur : " + hauteur);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER,0);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        //scène.gluObjets.get(0).transformée.tourner(new Vecteur3f(0,0.5f,0));
        //scène.gluObjets.get(0).transformée.positionner(new Vecteur3f(0,0,-100f*((float)Math.sin((float)compteurFPS * 0.05f) * 0.5f + 0.5f)));
        //terrain.construireTerrain();
        //scène.gluObjets.get(0).modèle = terrain.avoirModèle();

        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER,frameBuffer);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glViewport(0,0,dim,dim);
        peindre(scène);
        GLES30.glBindFramebuffer(GLES30.GL_READ_FRAMEBUFFER,frameBuffer);
        GLES30.glBindFramebuffer(GLES30.GL_DRAW_FRAMEBUFFER,0);
        GLES30.glBlitFramebuffer(0,0,dim,dim,0,0,(int)ressources.dimÉcranPixels.x,(int)ressources.dimÉcranPixels.y,GLES30.GL_COLOR_BUFFER_BIT,GLES30.GL_NEAREST);

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
        for (GluObjet objet : scène.gluObjets) {
            objet.colo.commencer();

            GLES30.glBindVertexArray(objet.modèle.vaoID);
            GLES30.glEnableVertexAttribArray(0);
            GLES30.glEnableVertexAttribArray(1);
            GLES30.glEnableVertexAttribArray(2);
            GLES30.glEnableVertexAttribArray(3);
            GLES30.glEnableVertexAttribArray(4);

            //GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
            //GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textureTest.ID);

            objet.colo.chargerMatrices(projection, caméra.avoirTransformationMatrice(),objet.transformée.avoirMatriceTransformée(), objet.transformée.rotationM);
            GLES30.glDrawElements(GLES30.GL_TRIANGLES,objet.modèle.nbPoint,GLES30.GL_UNSIGNED_INT,0);

            GLES30.glDisableVertexAttribArray(4);
            GLES30.glDisableVertexAttribArray(3);
            GLES30.glDisableVertexAttribArray(2);
            GLES30.glDisableVertexAttribArray(1);
            GLES30.glDisableVertexAttribArray(0);
            GLES30.glBindVertexArray(0);

            objet.colo.terminer();
        }

        for (IUGQuad quad : scène.IUGQuads) {
            quad.colo.commencer();

            GLES30.glBindVertexArray(quad.modèle.vaoID);
            GLES30.glEnableVertexAttribArray(0);

            if(quad.texture != null){
                GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
                GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, quad.texture.ID);
                quad.colo.aTexture(true);
            }else{
                quad.colo.aTexture(false);
            }

            quad.colo.chargerDimÉcran(ressources.dimÉcranPixels);

            quad.colo.chargerCouleur(quad.colo.color);
            quad.colo.chargerTransformée(quad.transformée2D.avoirMatriceTransformée());

            GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP,0,quad.modèle.nbPoint);

            GLES30.glDisableVertexAttribArray(0);
            GLES30.glBindVertexArray(0);

            quad.colo.terminer();
        }
    }
}
