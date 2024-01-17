package com.gyoo.gluengine.Components.Shaders;

import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.utils.Chargeur;

public class ColorisateurBasique extends ProgramColorisateur {

    private int PROJECTION;

    public ColorisateurBasique() {
        super(Chargeur.avoirChargeur().ChargerTexteActif("Shaders/ColorisateurBasique.vert"), Chargeur.avoirChargeur().ChargerTexteActif("Shaders/ColorisateurBasique.frag"));
    }
    @Override
    public void lierAttributs() {
        super.lierAttribut(0,"position");
    }
    @Override
    public void avoirToutUniforme() {
        PROJECTION = super.avoirPositionUniforme("projection");
    }

    public void chargerMatriceProjection(Matrice4f mat){
        super.chargerUniformeMatrice(PROJECTION,mat);
    }
}
