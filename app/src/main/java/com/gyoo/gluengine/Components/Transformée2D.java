package com.gyoo.gluengine.Components;

import com.gyoo.gluengine.Ressources;
import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;

public class Transformée2D {

    private Vecteur3f position = new Vecteur3f(0f);
    private float rotation = 0f;
    private Vecteur2f échelle = new Vecteur2f(1f);

    public Matrice4f positionM = new Matrice4f();
    public Matrice4f rotationM = new Matrice4f();
    public Matrice4f échelleM = new Matrice4f();
    public Transformée2D parent = null;
    private Ressources ressources = Ressources.avoirRessoucres();

    public Transformée2D(){
        positionM.translation(position);
        rotationM.rotation(new Vecteur3f(0f,0f,rotation));
        échelleM.échelle(new Vecteur3f(échelle.x, échelle.y,1f));
    }
    public Transformée2D(Transformée2D c){
        position = c.position.copier();
        rotation = c.rotation;
        échelle = c.échelle.copier();

        positionM = c.positionM.copier();
        rotationM = c.rotationM.copier();
        échelleM = c.échelleM.copier();
        parent = c.parent;
    }

    public Matrice4f avoirMatriceTransformée(){
        if(parent == null) {
            return Matrice4f.MultiplierMM(positionM, Matrice4f.MultiplierMM(rotationM, échelleM));
        }else {
            return Matrice4f.MultiplierMM( parent.avoirMatriceTransformée(), Matrice4f.MultiplierMM(positionM, Matrice4f.MultiplierMM(rotationM, échelleM)) );
        }
    }
    /** Définir la position en pixels. **/
    public void positionner(Vecteur3f p){
        position = p.copier();
        positionM.faireIdentité();
        positionM.translation(p);
    }
    /** Définir la position en points. 1 point = 16 mm **/
    public void positionnerPt(Vecteur3f p){
        position = Vecteur3f.mult( p, ressources.densitéPixel);
        positionM.faireIdentité();
        positionM.translation(position);
    }

    /** Ajouter à la position en pixels. **/
    public void déplacer(Vecteur3f t){
        position.addi(t);
        positionM.translation(t);
    }
    /** Ajouter à la position en points. 1 point = 16 mm **/
    public void déplacerPt(Vecteur3f t){
        position.addi( Vecteur3f.mult(t, ressources.densitéPixel) );
        positionM.translation( Vecteur3f.mult(t, ressources.densitéPixel) );
    }

    public Vecteur3f avoirPosition(){
        return position.copier();
    }
    public Vecteur3f avoirPositionPt(){ return Vecteur3f.mult(position, ressources.densitéPixel); }

    /** Ajouter à la rotation. **/
    public void tourner(float r){
        rotation += r;
        rotationM.rotation(new Vecteur3f(0f,0f,r));
    }
    /** Définir la rotation **/
    public void orienter(float r){
        rotation = r;
        rotationM.faireIdentité();
        rotationM.rotation(new Vecteur3f(0f,0f,r));
    }

    public float avoirRotation(){
        return rotation;
    }

    /** Définir l'échelle en pixels. **/
    public void échelle(Vecteur2f s){
        échelle = s;
        échelleM.faireIdentité();
        échelleM.échelle(new Vecteur3f(s.x,s.y,1f));
    }
    /** Définir l'échelle en points. 1 point = 16 mm **/
    public void échellePt(Vecteur2f s){
        échelle = Vecteur2f.mult(s, ressources.densitéPixel);
        échelleM.faireIdentité();
        échelleM.échelle(new Vecteur3f(échelle.x, échelle.y,1f));
    }

    /** Ajouter à l'échelle en pixels. **/
    public void échelonner(Vecteur2f s){
        échelle.addi(s);
        échelleM.échelle(new Vecteur3f(s.x,s.y,1f));
    }
    /** Ajouter à l'échelle en points. 1 point = 16 mm **/
    public void échelonnerPt(Vecteur2f s){
        Vecteur2f s2 = Vecteur2f.mult(s, ressources.densitéPixel);
        échelle.addi(s2);
        échelleM.échelle(new Vecteur3f(s2.x,s2.y,1f));
    }

    public Vecteur2f avoirÉchelle(){
        return échelle;
    }
    /** 1 point = 16 mm **/
    public Vecteur2f avoirÉchellePt(){ return Vecteur2f.mult(échelle, ressources.densitéPixel); }

    public void donnerParent(Transformée2D p){
        parent = p;
    }

    public  void retirerParent(){
        parent = null;
    }

    public Transformée2D copier(){
        return new Transformée2D(this);
    }
}
