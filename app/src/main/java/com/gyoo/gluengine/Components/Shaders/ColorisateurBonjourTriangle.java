package com.gyoo.gluengine.Components.Shaders;

import com.gyoo.gluengine.Vecteurs.Matrice4f;

public class ColorisateurBonjourTriangle extends ProgramColorisateur {


    private int TRANS;
    public ColorisateurBonjourTriangle(String vertCode, String fragCode) {
        super(vertCode, fragCode);
    }

    @Override
    public void lierAttributs() {
        super.lierAttribut(0,"position");
    }

    @Override
    public void lierToutUniforme() {
        TRANS = super.avoirPositionUniforme("Transformée");
    }

    public void chargerMatriceTransformée(Matrice4f t){
        super.chargerUniformeMatrice(TRANS, t);
    }
}
