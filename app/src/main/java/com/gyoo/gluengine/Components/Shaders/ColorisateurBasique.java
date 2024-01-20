package com.gyoo.gluengine.Components.Shaders;

import android.opengl.GLES30;
import android.util.Log;

import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.utils.Chargeur;
import com.gyoo.gluengine.utils.Maths;

public class ColorisateurBasique extends ProgramColorisateur {

    private int PROJECTION;
    private int TRANSFORMÉE;
    private int VUE;
    private int ROTATION;

    public ColorisateurBasique() {
        super(Chargeur.avoirChargeur().ChargerTexteActif("Colorisateurs/ColorisateurBasique.vert"), Chargeur.avoirChargeur().ChargerTexteActif("Colorisateurs/ColorisateurBasique.frag"));
    }
    @Override
    public void lierAttributs() {
        super.lierAttribut(0,"position");
        super.lierAttribut(1,"uv");
        super.lierAttribut(2,"normale");
        super.lierAttribut(3,"tangeante");
        super.lierAttribut(4,"bitangeante");
    }
    @Override
    public void lierToutUniforme() {
        PROJECTION = super.avoirPositionUniforme("projection");
        TRANSFORMÉE = super.avoirPositionUniforme("transformee");
        VUE = super.avoirPositionUniforme("vue");
        ROTATION = super.avoirPositionUniforme("rotation");

        super.chargerUniformeTexture(super.avoirPositionUniforme("texture"),0);
    }

    public void chargerMatrices(Matrice4f proj, Matrice4f vue, Matrice4f trans, Matrice4f rot){
        super.chargerUniformeMatrice(PROJECTION,proj);
        super.chargerUniformeMatrice(TRANSFORMÉE,trans);
        super.chargerUniformeMatrice(VUE,vue);
        super.chargerUniformeMatrice(ROTATION,rot);
    }
}
