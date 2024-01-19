package com.gyoo.gluengine.utils;

import android.opengl.Matrix;

//import com.gyoo.gluengine.Scène.Camera;
//import com.glu.engine.Scène.Scène;
import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;

public class Maths {

    public static Matrice4f créerMatriceTransformée(Vecteur3f translation, Vecteur3f rotation, Vecteur3f échelle){
        Matrice4f matrix = new Matrice4f();
        matrix.faireIdentité();
        Matrix.translateM(matrix.mat,0,translation.x,translation.y,translation.z);
        Matrix.rotateM(matrix.mat,0,rotation.x,1.0f,0.0f,0.0f);
        Matrix.rotateM(matrix.mat,0,rotation.y,0.0f,1.0f,0.0f);
        Matrix.rotateM(matrix.mat,0,rotation.z,0.0f,0.0f,1.0f);
        Matrix.scaleM(matrix.mat, 0,échelle.x,échelle.y,échelle.z);
        return matrix;
    }

    public static Matrice4f créerMatriceTransformée(Vecteur2f translation, float rotation, Vecteur2f échelle){
        Matrice4f matrix = new Matrice4f();
        matrix.faireIdentité();
        Matrix.translateM(matrix.mat,0,translation.x,translation.y,0);
        Matrix.rotateM(matrix.mat,0,rotation,0.0f,0.0f,1.0f);
        Matrix.scaleM(matrix.mat, 0,échelle.x,échelle.y,1.0f);
        return matrix;
    }

    public Matrice4f créerMatriceTranslation(Vecteur3f translation){
        Matrice4f matrix = new Matrice4f();
        matrix.faireIdentité();
        Matrix.translateM(matrix.mat,0,translation.x,translation.y,0);
        return matrix;
    }

    public Matrice4f créerMatriceÉchelle(Vecteur3f échelle){
        Matrice4f matrix = new Matrice4f();
        matrix.faireIdentité();
        Matrix.scaleM(matrix.mat, 0,échelle.x,échelle.y,1.0f);
        return matrix;
    }

    public static Matrice4f créerMatriceRotation(Vecteur3f rotation){
        Matrice4f matrix = new Matrice4f();
        matrix.faireIdentité();
        Matrix.rotateM(matrix.mat,0,rotation.x,1.0f,0.0f,0.0f);
        Matrix.rotateM(matrix.mat,0,rotation.y,0.0f,1.0f,0.0f);
        Matrix.rotateM(matrix.mat,0,rotation.z,0.0f,0.0f,1.0f);
        return matrix;
    }

    /*public static Matrice4f generateViewMatrix(Camera camera){
        Matrice4f matrix = new Matrice4f();
        matrix.setIdentity();
        Matrix.rotateM(matrix.mat,0,-camera.getRotation().x,1.0f,0.0f,0.0f);
        Matrix.rotateM(matrix.mat,0,-camera.getRotation().y,0.0f,1.0f,0.0f);
        Matrix.rotateM(matrix.mat,0,-camera.getRotation().z,0.0f,0.0f,1.0f);
        Matrix.translateM(matrix.mat,0,-camera.getPosition().x,-camera.getPosition().y,-camera.getPosition().z);
        return matrix;
    }

    public static Matrice4f generatePrevViewMatrix(Camera camera){
        Matrice4f matrix = new Matrice4f();
        matrix.setIdentity();
        Matrix.rotateM(matrix.mat,0,-camera.getPrevRotation().x,1.0f,0.0f,0.0f);
        Matrix.rotateM(matrix.mat,0,-camera.getPrevRotation().y,0.0f,1.0f,0.0f);
        Matrix.rotateM(matrix.mat,0,-camera.getPrevRotation().z,0.0f,0.0f,1.0f);
        Matrix.translateM(matrix.mat,0,-camera.getPrevPosition().x,-camera.getPrevPosition().y,-camera.getPrevPosition().z);
        return matrix;
    }*/

    public static float plusPetitQue(float a, float b){
        if(a<b){
            return 1.0f;
        }
        return 0.0f;
    }

    public static Vecteur2f pointLePlusPrèsSurLaLigne(Vecteur2f ptUn, Vecteur2f ptDeux, Vecteur2f ptTest){
        Vecteur2f AB = Vecteur2f.sous(ptUn,ptDeux);
        return Vecteur2f.addi(ptUn, Vecteur2f.mult( Vecteur2f.norm(AB), Vecteur2f.scal( AB, Vecteur2f.sous(ptUn,ptTest) ) / AB.longueur() )); // OD = OA + norm(AB)scal(AB, AC)/long(AB)
    }

    public static float mix(float a, float b, float m){
        return (a*(1-m))+(b*m);
    }

    /*public static Matrice4f[] generateSunTransformMatrix(Scène scene){
        Matrice4f proj = new Matrice4f();
        proj.setIdentity();
        Matrix.perspectiveM(proj.mat,0,scene.FOV,1f,Scène.NEAR_PLANE,scene.sunLight.shadowDist);
        Matrice4f camTransform = generateViewMatrix(scene.camera);
        float[] res = new float[16];
        Matrix.multiplyMM(res,0, proj.mat, 0,camTransform.mat,0);
        proj.mat = res;
        Matrix.invertM(res, 0,proj.mat,0);
        proj.mat = res;
        Matrice4f sunView = Maths.createRotationMatrix(Vecteur3f.lookAt(new Vecteur3f(0),scene.sunLight.direction,0).negative());

        Vecteur3f[] NDC = new Vecteur3f[]{
                new Vecteur3f(-1f,-1f,-1f),
                new Vecteur3f(1f,-1f,-1f),
                new Vecteur3f(-1f,1f,-1f),
                new Vecteur3f(1f,1f,-1f),
                new Vecteur3f(-1f,-1f,1f),
                new Vecteur3f(1f,-1f,1f),
                new Vecteur3f(-1f,1f,1f),
                new Vecteur3f(1f,1f,1f),
        };

        Vecteur3f min = new Vecteur3f( Float.MAX_VALUE);
        Vecteur3f max = new Vecteur3f( -Float.MAX_VALUE);
        Vecteur3f center = new Vecteur3f(0);

        for(int i = 0; i < 8; i++){
            float[] vector = new float[]{NDC[i].x,NDC[i].y,NDC[i].z,1f};
            float[] result = new float[]{0f,0f,0f,0f};

            Matrix.multiplyMV(result,0,proj.mat,0,vector,0);
            result[0] = result[0]/result[3];
            result[1] = result[1]/result[3];
            result[2] = result[2]/result[3];
            result[3] = 1f;
            Matrix.multiplyMV(vector,0,sunView.mat,0,result,0);
            center.add(new Vecteur3f(vector[0],vector[1],vector[2]));

            min.x = Float.min(vector[0],min.x);
            min.y = Float.min(vector[1],min.y);
            min.z = Float.min(vector[2],min.z);
            max.x = Float.max(vector[0],max.x);
            max.y = Float.max(vector[1],max.y);
            max.z = Float.max(vector[2],max.z);
        }

        center.scale(1f/8f);

        /*float[] vector = new float[]{0f,0f,-1f,1f};
        float[] result = new float[]{0f,0f,0f,0f};
        Matrix.multiplyMV(result,0,sunView.mat,0,vector,0);

        center = new Vecteur3f(result[0],result[1],result[2]);*

        Matrice4f transformée = Maths.createTransformationMatrix(center.negative(),Vecteur3f.lookAt(new Vecteur3f(0),scene.sunLight.direction,0).negative(),new Vecteur3f(1f));

        proj.mat = new float[]{
                2f/(max.x-min.x),0f,0f,0f,
                0f,2f/(max.y-min.y),0f,0f,
                0f,0f,-2f/(2f*scene.sunLight.shadowDist),0f,
                0f,0f,0f,1f
        };

        return new Matrice4f[]{transformée,proj};
    }*/
}
