package com.gyoo.gluengine.Components;

import com.gyoo.gluengine.Ressources;
import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;

public class Transformée2D {

    private Vecteur3f position = new Vecteur3f(0f);
    private float rotation = 0f;
    private Vecteur2f scale = new Vecteur2f(1f);

    public Matrice4f positionM = new Matrice4f();
    public Matrice4f rotationM = new Matrice4f();
    public Matrice4f scaleM = new Matrice4f();
    public Transformée2D parent = null;
    private Ressources ressources = Ressources.avoirRessoucres();

    public Transformée2D(){
        positionM.translation(position);
        rotationM.rotation(new Vecteur3f(0f,0f,rotation));
        scaleM.échelle(new Vecteur3f(scale.x,scale.y,1f));
    }

    public Matrice4f getTransformMatrix(){
        if(parent == null) {
            return Matrice4f.MultiplierMM(positionM, Matrice4f.MultiplierMM(rotationM, scaleM));
        }else {
            return Matrice4f.MultiplierMM( parent.getTransformMatrix(), Matrice4f.MultiplierMM(positionM, Matrice4f.MultiplierMM(rotationM, scaleM)) );
        }
    }

    public void setPosition(Vecteur3f p){
        position = p.copier();
        positionM.faireIdentité();
        positionM.translation(p);
    }
    public void setPositionPt(Vecteur3f p){
        position = Vecteur3f.mult( p, ressources.densitéPixel);
        positionM.faireIdentité();
        positionM.translation(position);
    }

    public void translate(Vecteur3f t){
        position.addi(t);
        positionM.translation(t);
    }
    public void translatePt(Vecteur3f t){
        position.addi( Vecteur3f.mult(t, ressources.densitéPixel) );
        positionM.translation( Vecteur3f.mult(t, ressources.densitéPixel) );
    }

    public Vecteur3f getPosition(){
        return position.copier();
    }
    public Vecteur3f getPositionPt(){ return Vecteur3f.mult(position, ressources.densitéPixel); }

    public void rotate(float r){
        rotation += r;
        rotationM.rotation(new Vecteur3f(0f,0f,r));
    }

    public void setRotation(float r){
        rotation = r;
        rotationM.faireIdentité();
        rotationM.rotation(new Vecteur3f(0f,0f,r));
    }

    public float getRotation(){
        return rotation;
    }

    public void setScale(Vecteur2f s){
        scale = s;
        scaleM.faireIdentité();
        scaleM.échelle(new Vecteur3f(s.x,s.y,1f));
    }
    public void setScalePt(Vecteur2f s){
        scale = Vecteur2f.mult(s, ressources.densitéPixel);
        scaleM.faireIdentité();
        scaleM.échelle(new Vecteur3f(scale.x,scale.y,1f));
    }

    public void scale(Vecteur2f s){
        scale.addi(s);
        scaleM.échelle(new Vecteur3f(s.x,s.y,1f));
    }
    public void scalePt(Vecteur2f s){
        Vecteur2f s2 = Vecteur2f.mult(s, ressources.densitéPixel);
        scale.addi(s2);
        scaleM.échelle(new Vecteur3f(s2.x,s2.y,1f));
    }

    public Vecteur2f getScale(){
        return scale;
    }
    public Vecteur2f getScalePt(){ return Vecteur2f.mult(scale, ressources.densitéPixel); }

    public void parent(Transformée2D p){
        parent = p;
    }

    public  void unParent(){
        parent = null;
    }
}
