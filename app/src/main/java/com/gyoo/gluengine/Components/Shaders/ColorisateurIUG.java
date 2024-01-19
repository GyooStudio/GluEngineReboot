package com.gyoo.gluengine.Components.Shaders;

import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.Vecteurs.Vecteur4f;
import com.gyoo.gluengine.utils.Chargeur;

public class ColorisateurIUG extends ProgramColorisateur {
    public static int COMPONENT_TYPE = 6;

    private int TRANSFORMÉE;
    private int DIM_ÉCRAN;
    private int COULEUR;
    private int A_TEXTURE;

    public Vecteur4f color = new Vecteur4f(1f);

    public ColorisateurIUG() {
        super(Chargeur.avoirChargeur().ChargerTexteActif("Colorisateurs/IUG.vert"), Chargeur.avoirChargeur().ChargerTexteActif("Colorisateurs/IUG.frag"));
    }

    @Override
    public void lierAttributs() {
        super.lierAttribut(0,"position");
    }

    @Override
    public void avoirToutUniforme() {
        TRANSFORMÉE = super.avoirPositionUniforme("Transformée");
        DIM_ÉCRAN = super.avoirPositionUniforme("screen");
        COULEUR = super.avoirPositionUniforme("color");
        A_TEXTURE = super.avoirPositionUniforme("isTextured");
    }

    public void chargerTransformée(Matrice4f t){
        super.chargerUniformeMatrice(TRANSFORMÉE,t);
    }

    public void chargerDimÉcran(Vecteur2f s){
        super.chargerUniformeVecteur(DIM_ÉCRAN,s);
    }
    public void chargerCouleur(Vecteur4f c){
        super.chargerUniformeVecteur(COULEUR,c);
    }
    public void aTexture(boolean i){
        super.chargerUniformeBool(A_TEXTURE,i);
    }
}
