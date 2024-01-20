package com.gyoo.gluengine;

import com.gyoo.gluengine.Components.ModèleBrut;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;
import com.gyoo.gluengine.utils.Chargeur;

public class Terrain {

    public int tailleX = 100;
    public int tailleY = 100;
    public int tailleZ = 100;

    public byte[] blocs = new byte[tailleX*tailleY*tailleZ];

    public ModèleBrut modèle;
    private Chargeur chargeur = Chargeur.avoirChargeur();

    public Terrain(){
        for (int i = 0; i < blocs.length; i++) {
            blocs[i] = 1;
        }
    }
    public void construireTerrain(){
        int nombreDeFaces = 0;
        int DXP = 1;                //Delta de i pour obtenir le bloc adjascent en +x
        int DXN = -1;               //Delta de i pour obtenir le bloc adjascent en -x
        int DYP = tailleX;          //Delta de i pour obtenir le bloc adjascent en +y
        int DYN = -tailleX;         //Delta de i pour obtenir le bloc adjascent en -y
        int DZP = tailleX*tailleY;  //Delta de i pour obtenir le bloc adjascent en +z
        int DZN = -tailleX*tailleY; //Delta de i pour obtenir le bloc adjascent en -z
        byte[] masque = new byte[blocs.length];
        byte[] nbFaces = new byte[blocs.length];
        for (int i = 0; i < blocs.length; i++) {

            int xpos = Math.floorMod(i,tailleX);
            int ypos = Math.floorMod( Math.floorDiv(i,tailleY), tailleY);
            int zpos = Math.floorDiv( i,tailleX*tailleY );

            if(  xpos == 0          || ( xpos != 0         && i + DXN > -1            && blocs[i + DXN] == 0) )  {nombreDeFaces++; nbFaces[i]++; masque[i] = (byte) (masque[i] | 0b00000001);}
            if(  xpos == tailleX-1  || ( xpos != tailleX-1 && i + DXP < blocs.length  && blocs[i + DXP] == 0) )  {nombreDeFaces++; nbFaces[i]++; masque[i] = (byte) (masque[i] | 0b00000010);}
            if(  ypos == 0          || ( ypos != 0         && i + DYN > -1            && blocs[i + DYN] == 0) )  {nombreDeFaces++; nbFaces[i]++; masque[i] = (byte) (masque[i] | 0b00000100);}
            if(  ypos == tailleY-1  || ( ypos != tailleY-1 && i + DYP < blocs.length  && blocs[i + DYP] == 0) )  {nombreDeFaces++; nbFaces[i]++; masque[i] = (byte) (masque[i] | 0b00001000);}
            if(  zpos == 0          || ( zpos != 0         && i + DZN > -1            && blocs[i + DZN] == 0) )  {nombreDeFaces++; nbFaces[i]++; masque[i] = (byte) (masque[i] | 0b00010000);}
            if(  zpos == tailleZ-1  || ( zpos != tailleZ-1 && i + DZP < blocs.length  && blocs[i + DZP] == 0) )  {nombreDeFaces++; nbFaces[i]++; masque[i] = (byte) (masque[i] | 0b00100000);}
        }

        int nombre = blocs.length;
        float[] positions = new float[nombreDeFaces*4*3];
        float[] normales = new float[nombreDeFaces*4*3];
        float[] uv = new float[nombreDeFaces*4*2];
        int[] in = new int[nombreDeFaces*6];
        int indexP = 0;
        int indexN = 0;
        int indexUV = 0;
        int indexIn = 0;
        for (int n = 0; n < nombre; n++){
            int xpos = Math.floorMod(n,tailleX);
            int ypos = Math.floorMod( Math.floorDiv(n,tailleY), tailleY);
            int zpos = Math.floorDiv( n,tailleX*tailleY );
            int[] face = new int[]{-1, 0, 0};
            for (int i = 0; i < 6; i++){
                boolean découverte = false;
                switch (i){
                    case 0:
                        découverte = 0b00000001 == (masque[n] & 0b00000001);
                        break;
                    case 1:
                        découverte = 0b00000010 == (masque[n] & 0b00000010);
                        break;
                    case 2:
                        découverte = 0b00000100 == (masque[n] & 0b00000100);
                        break;
                    case 3:
                        découverte = 0b00001000 == (masque[n] & 0b00001000);
                        break;
                    case 4:
                        découverte = 0b00010000 == (masque[n] & 0b00010000);
                        break;
                    case 5:
                        découverte = 0b00100000 == (masque[n] & 0b00100000);
                        break;
                }
                if(découverte) {
                    // Position originale. Représente la normale et le centre de la face.
                    Vecteur3f po = new Vecteur3f(face[0], face[1], face[2]);
                    // Position additionnée. Additionnée à la normale, représente un coin.
                    float[] pa = new float[]{(face[0] == 0) ? 1f : 0f, (face[1] == 0) ? 1f : 0f, (face[2] == 0) ? 1f : 0f};
                    // Contient les axes utilisés par pa.
                    int[] axes = new int[]{(pa[0] == 1) ? 0 : 1, (pa[2] == 0) ? 1 : 2};
                    //v1
                    positions[indexP + 0] = (po.x + pa[0]) * 0.5f + xpos; //x
                    positions[indexP + 1] = (po.y + pa[1]) * 0.5f + ypos; //y
                    positions[indexP + 2] = (po.z + pa[2]) * 0.5f + zpos; //z

                    //v2 inverse le premier axe qui n'est pas 0 dans pa.
                    positions[indexP + 3] = (po.x + pa[0]) * 0.5f * ((axes[0] == 0) ? -1f : 1f) + xpos; //x | ( si l'axe 0 est x ) alors *-1
                    positions[indexP + 4] = (po.y + pa[1]) * 0.5f * ((axes[0] == 1) ? -1f : 1f) + ypos; //y | ( si l'axe 0 est y ) alors *-1
                    positions[indexP + 5] = (po.z + pa[2]) * 0.5f + zpos; //z

                    //v3 inverse le deuxième axe qui n'est pas 0 dans pa.
                    positions[indexP + 6] = (po.x + pa[0]) * 0.5f + xpos; //x
                    positions[indexP + 7] = (po.y + pa[1]) * 0.5f * ((axes[1] == 1) ? -1f : 1f) + ypos; //y | ( si l'axe 1 est y ) alors *-1
                    positions[indexP + 8] = (po.z + pa[2]) * 0.5f * ((axes[1] == 2) ? -1f : 1f) + zpos; //z | ( si l'axe 1 est z ) alors *-1

                    //v4 inverse les deux axes qui ne sont pas 0 dans pa.
                    positions[indexP + 9] = (po.x + pa[0]) * 0.5f * ((axes[0] == 0) ? -1f : 1f) + xpos; //x | ( si l'axe 0 est x ) alors *-1
                    positions[indexP +10] = (po.y + pa[1]) * 0.5f* ((axes[0] == 1 || pa[1] == 1) ? -1f : 1f) + ypos; //y | ( si l'axe 0 ou l'axe 1 est y ) alors *-1
                    positions[indexP +11] = (po.z + pa[2]) * 0.5f* ((axes[1] == 2) ? -1f : 1f) + zpos; //z | ( si l'axe 1 est z ) alors *-1

                    //Rentrer les normales
                    normales[indexN + 0] = face[0];
                    normales[indexN + 1] = face[1];
                    normales[indexN + 2] = face[2];
                    normales[indexN + 3] = face[0];
                    normales[indexN + 4] = face[1];
                    normales[indexN + 5] = face[2];
                    normales[indexN + 6] = face[0];
                    normales[indexN + 7] = face[1];
                    normales[indexN + 8] = face[2];
                    normales[indexN + 9] = face[0];
                    normales[indexN +10] = face[1];
                    normales[indexN +11] = face[2];

                    //Rentrer les UVs
                    uv[indexUV + 0] = 0;
                    uv[indexUV + 1] = 0;
                    uv[indexUV + 2] = 1;
                    uv[indexUV + 3] = 0;
                    uv[indexUV + 4] = 0;
                    uv[indexUV + 5] = 1;
                    uv[indexUV + 6] = 1;
                    uv[indexUV + 7] = 1;

                    //Faire les triangles
                    //Pour les rentrer dans le sens inverse des aiguilles d'une montre, il faut rentrer un ordre dépendant de la normale.
                    if (face[0] == 1 || face[1] == -1 || face[2] == 1) {
                        //axe = x
                        in[indexIn + 0] = indexP/3 + 0; //v1
                        in[indexIn + 1] = indexP/3 + 1; //v2
                        in[indexIn + 2] = indexP/3 + 2; //v3
                        in[indexIn + 3] = indexP/3 + 2; //v3
                        in[indexIn + 4] = indexP/3 + 1; //v2
                        in[indexIn + 5] = indexP/3 + 3; //v4
                    } else if (face[0] == -1 || face[1] == 1 || face[2] == -1) {
                        //axe = y
                        in[indexIn + 0] = indexP/3 + 0; //v1
                        in[indexIn + 1] = indexP/3 + 2; //v3
                        in[indexIn + 2] = indexP/3 + 1; //v2
                        in[indexIn + 3] = indexP/3 + 1; //v2
                        in[indexIn + 4] = indexP/3 + 2; //v3
                        in[indexIn + 5] = indexP/3 + 3; //v4
                    }
                    indexP += 4*3;
                    indexN += 4*3;
                    indexUV += 4*2;
                    indexIn += 6;
                }
                //changer vers la prochaine face
                face = new int[]{(face[0] == -1) ? 1 : 0, (face[0] == 1) ? -1 : (face[1] == -1) ? 1 : 0, (face[1] == 1) ? -1 : (face[2] == -1) ? 1 : 0};
            }
        }
        modèle = Chargeur.ChargerVersVAO(positions,uv,normales,in);
        modèle.construire();
    }

    public ModèleBrut avoirModèle(){return modèle;}
}
