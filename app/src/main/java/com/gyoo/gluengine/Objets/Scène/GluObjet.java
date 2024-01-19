package com.gyoo.gluengine.Objets.Scène;

import com.gyoo.gluengine.Components.ModèleBrut;
import com.gyoo.gluengine.Components.Shaders.ColorisateurBasique;
import com.gyoo.gluengine.Components.Transformée;

public class GluObjet {
    public ModèleBrut modèle;
    public ColorisateurBasique colo;
    public Transformée transformée;

    public GluObjet(ModèleBrut modèle){
        this.modèle = modèle;
        colo = new ColorisateurBasique();
        transformée = new Transformée();
    }

    public GluObjet(GluObjet copier){
        modèle = copier.modèle;
        colo = copier.colo;
        transformée = copier.transformée;
    }

    public GluObjet copier(){
        GluObjet r = new GluObjet(this.modèle);
        r.colo = colo.cop
    }
}