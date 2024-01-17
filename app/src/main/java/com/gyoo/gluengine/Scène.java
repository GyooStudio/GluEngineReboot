package com.gyoo.gluengine;

import com.gyoo.gluengine.Objets.IUG.IUGQuad;
import com.gyoo.gluengine.Objets.Scène.GluObjet;

import java.util.ArrayList;

public class Scène {

    public ArrayList<IUGQuad> IUGQuads = new ArrayList<>();
    public ArrayList<GluObjet> gluObjets = new ArrayList<>();

    public Scène(){}

    public void ajouterGUIQuad(IUGQuad q){
        IUGQuads.add(q);
    }

    public void ajouterGluObject(GluObjet o){
        gluObjets.add(o);}
}
