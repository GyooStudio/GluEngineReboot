package com.gyoo.gluengine.Objets.Scène;

import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;

public class Caméra {
    public Vecteur3f position = new Vecteur3f(0);
    public Vecteur3f rotation = new Vecteur3f(0);

    public Caméra(){}

    public Matrice4f avoirTransformationMatrice(){
        Matrice4f m = new Matrice4f();
        m.translation(position.opposé());
        m.rotation(rotation.opposé());
        return m;
    }
}
