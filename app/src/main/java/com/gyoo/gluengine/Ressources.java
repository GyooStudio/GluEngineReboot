package com.gyoo.gluengine;

import com.gyoo.gluengine.Vectors.Vector2f;

public class Ressources {

    public Vector2f screenDimPixels = new Vector2f(0f);
    private static Ressources ressources;

    private void Ressources(){}

    public static Ressources getRessources() {
        if(ressources == null){
            ressources = new Ressources();
        }
        return ressources;
    }
}
