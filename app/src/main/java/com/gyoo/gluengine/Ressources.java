package com.gyoo.gluengine;

import android.annotation.SuppressLint;

import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;

public class Ressources {

    public Vecteur2f dimÉcranPixels = new Vecteur2f(0f);
    private static Ressources ressources;
    public float densitéPixel; // px/po

    private Ressources(){}

    public static Ressources avoirRessoucres() {
        if(ressources == null){
            ressources = new Ressources();
        }
        return ressources;
    }

    public float PtÀPx(float Pt){return Pt / ( (16f/25.4f) * densitéPixel);}
    public Vecteur2f PtÀPx(Vecteur2f Pt){return Vecteur2f.mult(Pt, 1f/( (16f/25.4f) * densitéPixel));}
    public Vecteur3f PtÀPx(Vecteur3f Pt){return Vecteur3f.mult(Pt, 1f/( (16f/25.4f) * densitéPixel));}
    public float PxÀPt(float Px){return Px / ( (16f/25.4f) * densitéPixel);}
    public Vecteur2f PxÀPt(Vecteur2f Px){return Vecteur2f.mult(Px, 1f/( (16f/25.4f) * densitéPixel));}
    public Vecteur3f PxÀPt(Vecteur3f Px){return Vecteur3f.mult(Px, 1f/( (16f/25.4f) * densitéPixel));}
}
