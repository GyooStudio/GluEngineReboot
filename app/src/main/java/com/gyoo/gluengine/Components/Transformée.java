package com.gyoo.gluengine.Components;

import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;

public class Transformée {

    private Vecteur3f position = new Vecteur3f(0f);
    private Vecteur3f rotation = new Vecteur3f(0f);
    private Vecteur3f scale = new Vecteur3f(1f);

    public Matrice4f positionM = new Matrice4f();
    public Matrice4f rotationM = new Matrice4f();
    public Matrice4f scaleM = new Matrice4f();

    public Transformée parent = null;

    public Transformée(){
        positionM.translation(position);
        rotationM.rotation(rotation);
        scaleM.échelle(scale);
    }

    public Matrice4f getTransformMatrix(){
        if(parent == null) {
            return Matrice4f.MultiplierMM(positionM, Matrice4f.MultiplierMM(scaleM, rotationM));
        }else{
            return Matrice4f.MultiplierMM( parent.getTransformMatrix(), Matrice4f.MultiplierMM(positionM, Matrice4f.MultiplierMM(scaleM, rotationM) ) );
        }
    }

    public void setPosition(Vecteur3f p){
        position = p.copier();
        positionM.faireIdentité();
        positionM.translation(p);
    }

    public void translate(Vecteur3f t){
        position.addi(t);
        positionM.translation(t);
    }

    public Vecteur3f getPosition(){
        return position;
    }

    public void rotate(Vecteur3f r){
        rotation.addi(r);
        rotationM.rotation(r);
    }

    public void setRotation(Vecteur3f r){
        rotation = r.copier();
        rotationM.faireIdentité();
        rotationM.rotation(r);
    }

    public Vecteur3f getRotation(){
        return rotation;
    }

    public void setScale(Vecteur3f s){
        scale = s;
        scaleM.faireIdentité();
        scaleM.échelle(s);
    }

    public void scale(Vecteur3f s){
        scale.addi(s);
        scaleM.échelle(s);
    }

    public Vecteur3f getScale(){
        return scale;
    }

    public void parent(Transformée p){
        parent = p;
    }

    public void unParent(){
        parent = null;
    }
}
