package com.gyoo.gluengine.Objets.IUG;

import com.gyoo.gluengine.Components.GTexture;
import com.gyoo.gluengine.Components.ModèleBrut;
import com.gyoo.gluengine.Components.Shaders.ColorisateurIUG;
import com.gyoo.gluengine.Components.Transformée2D;
import com.gyoo.gluengine.Vecteurs.Vecteur4f;
import com.gyoo.gluengine.utils.Chargeur;

public class IUGQuad {
    public ModèleBrut mesh;
    public ColorisateurIUG shader;
    public Transformée2D transformée2D;
    public GTexture texture;
    public IUGQuad parent;

    private float[] positions = {
            -0.5f,-0.5f,0f,
            0.5f,-0.5f,0f,
            -0.5f,0.5f,0f,
            0.5f,0.5f,0f
    };

    public IUGQuad(){
        Chargeur chargeur = Chargeur.avoirChargeur();
        mesh = Chargeur.ChargerVersVAO(positions);
        shader = new ColorisateurIUG();
        shader.chargerCouleur(new Vecteur4f(0.0f,0.0f,1.0f,1.0f));
        shader.buildShader();
        transformée2D = new Transformée2D();
    }

    public void addTexture(GTexture t){
        texture = t;
    }

    public void parent(IUGQuad p){
        parent = p;
        transformée2D.parent(p.transformée2D);
    }

    public void unParent(){
        parent = null;
        transformée2D.unParent();
    }
}
