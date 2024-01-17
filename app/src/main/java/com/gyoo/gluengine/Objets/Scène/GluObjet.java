package com.gyoo.gluengine.Objets.Scène;

import com.gyoo.gluengine.Components.ModèleBrut;
import com.gyoo.gluengine.Components.Shaders.ColorisateurBasique;
import com.gyoo.gluengine.Components.Transformée;

public class GluObjet {
    public ModèleBrut mesh;
    public ColorisateurBasique shader;
    public Transformée transformée;

    public GluObjet(ModèleBrut mesh){
        this.mesh = mesh;
        shader = new ColorisateurBasique();
        transformée = new Transformée();
    }
}