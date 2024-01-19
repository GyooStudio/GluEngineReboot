package com.gyoo.gluengine.Components;

import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;

public class Transformée {

    private Vecteur3f position = new Vecteur3f(0f);
    private Vecteur3f rotation = new Vecteur3f(0f);
    private Vecteur3f échelle = new Vecteur3f(1f);

    public Matrice4f positionM = new Matrice4f();
    public Matrice4f rotationM = new Matrice4f();
    public Matrice4f échelleM = new Matrice4f();

    public Transformée parent = null;

    public Transformée(){
        positionM.translation(position);
        rotationM.rotation(rotation);
        échelleM.échelle(échelle);
    }

    public Matrice4f avoirMatriceTransformée(){
        if(parent == null) {
            return Matrice4f.MultiplierMM(positionM, Matrice4f.MultiplierMM(échelleM, rotationM));
        }else{
            return Matrice4f.MultiplierMM( parent.avoirMatriceTransformée(), Matrice4f.MultiplierMM(positionM, Matrice4f.MultiplierMM(échelleM, rotationM) ) );
        }
    }

    /** Définir la position **/
    public void positionner(Vecteur3f p){
        position = p.copier();
        positionM.faireIdentité();
        positionM.translation(p);
    }
    /** Ajouter à la position **/
    public void déplacer(Vecteur3f t){
        position.addi(t);
        positionM.translation(t);
    }
    /** Obtenir la position **/
    public Vecteur3f avoirPosition(){
        return position;
    }
    /** Ajouter à la rotation **/
    public void tourner(Vecteur3f r){
        rotation.addi(r);
        rotationM.rotation(r);
    }
    /** Définir la rotation **/
    public void orienter(Vecteur3f r){
        rotation = r.copier();
        rotationM.faireIdentité();
        rotationM.rotation(r);
    }
    /** Obtenir la rotation **/
    public Vecteur3f avoirRotation(){
        return rotation;
    }
    /** Ajouter à l'échelle **/
    public void échelonner(Vecteur3f s){
        échelle = s;
        échelleM.faireIdentité();
        échelleM.échelle(s);
    }
    /** Définir l'échelle **/
    public void échelle(Vecteur3f s){
        échelle.addi(s);
        échelleM.échelle(s);
    }
    /** Obtenir l'échelle **/
    public Vecteur3f avoirÉchelle(){
        return échelle;
    }

    public void donnerParent(Transformée p){
        parent = p;
    }

    public void retirerParent(){
        parent = null;
    }
}
