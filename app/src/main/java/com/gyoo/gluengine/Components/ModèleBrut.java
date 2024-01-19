package com.gyoo.gluengine.Components;

import com.gyoo.gluengine.utils.Chargeur;

public class ModèleBrut {
    public int vaoID;
    public int nbPoint;
    public String name = "name";
    public boolean isDirty = true;

    public float[] positions;
    public float[] uv;
    public float[] norm;
    public int[] indices;

    public ModèleBrut(int vaoID, int nbPoint, float[] positions, float[] uv, float[] norm, int[] indices){
        this.vaoID = vaoID;
        this.nbPoint = nbPoint;
        isDirty = false;
        this.positions = positions;
        this.uv = uv;
        this.norm = norm;
        this.indices = indices;
    }

    public ModèleBrut(float[] positions, float[] uv, float[] norm, int[] indices){
        this.positions = positions.clone();
        this.uv = uv.clone();
        this.norm = norm.clone();
        this.indices = indices.clone();
        nbPoint = indices.length;
        vaoID = 0;
        isDirty = true;
    }

    public void makeModel(){
        ModèleBrut model = Chargeur.ChargerVersVAO(positions,uv,norm,indices);
        vaoID = model.vaoID;
        isDirty = false;
        //this.positions = null;
        //this.uv = null;
        //this.norm = null;
        //this.indices = null;
    }

}
