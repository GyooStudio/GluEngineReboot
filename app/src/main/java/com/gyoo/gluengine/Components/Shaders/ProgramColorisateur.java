package com.gyoo.gluengine.Components.Shaders;

import android.opengl.GLES30;
import android.util.Log;

import com.gyoo.gluengine.Vecteurs.Matrice4f;
import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.Vecteurs.Vecteur3f;
import com.gyoo.gluengine.Vecteurs.Vecteur4f;

public abstract class ProgramColorisateur {

        public int IDPrograme;
        public int colorisateurPoint;
        public int colorisateurFragment;

        public String codePoint;
        public String codeFrag;

        public boolean colorisateurConstruit = false;

        public ProgramColorisateur(String codePoint, String codeFrag ){
            this.codePoint = codePoint;
            this.codeFrag = codeFrag;
        }

        public void buildShader(){
            colorisateurPoint = chargerColorisateur(GLES30.GL_VERTEX_SHADER, codePoint);
            colorisateurFragment = chargerColorisateur(GLES30.GL_FRAGMENT_SHADER, codeFrag);
            IDPrograme = GLES30.glCreateProgram();
            GLES30.glAttachShader(IDPrograme, colorisateurPoint);
            GLES30.glAttachShader(IDPrograme, colorisateurFragment);

            GLES30.glLinkProgram(IDPrograme);
            GLES30.glValidateProgram(IDPrograme);
            final int[] compileStatus = new int[1];
            GLES30.glGetProgramiv(IDPrograme, GLES30.GL_LINK_STATUS, compileStatus, 0);
            if(compileStatus[0] == 0){
                Log.e("Program","Something occurred with the program");
                Log.e("Program","[HERE ->]" + GLES30.glGetProgramInfoLog(IDPrograme));

                System.exit(-1);
            }

            commencer();
            lierAttributs();
            avoirToutUniforme();
            terminer();

            colorisateurConstruit = true;
        }

        public void définir(int code, String argument, boolean valeur){
            if(!valeur) {
                if (code == GLES30.GL_VERTEX_SHADER) {
                    codePoint = codePoint.replace("#define " + argument, " ");
                }else if(code == GLES30.GL_FRAGMENT_SHADER){
                    codeFrag = codeFrag.replace("#define " + argument, " ");
                }
            }
        }

        public abstract void lierAttributs();

        public void lierAttribut(int vbo, String attribut){
            GLES30.glBindAttribLocation(IDPrograme,vbo,attribut);
        }

        private int chargerColorisateur(int type, String code){
            int shader = GLES30.glCreateShader(type);

            GLES30.glShaderSource(shader,code);
            GLES30.glCompileShader(shader);
            final int[] compileStatus = new int[1];
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compileStatus, 0);
            if(compileStatus[0] == 0){
                System.err.println("Could not compile shader type " + type);
                //frag = 35632
                //vert = 35633
                System.err.println("[HERE ->]" + GLES30.glGetShaderInfoLog(shader));
                Log.e("Shader Source", GLES30.glGetShaderSource(shader));
                GLES30.glDeleteShader(shader);
                shader = 0;
                System.exit(-1);
            }

            return shader;
        }

        public abstract void avoirToutUniforme();

        public int avoirPositionUniforme(String uniforme){
            return GLES30.glGetUniformLocation(IDPrograme,uniforme);
        }

        public void chargerUniformeFloat(int position, float value){
            GLES30.glUniform1f(position,value);
        }

        public void chargerUniformeVecteur(int position, Vecteur3f vecteur){
            GLES30.glUniform3f(position,vecteur.x,vecteur.y,vecteur.z);
        }

        public void chargerUniformeVecteur(int position, Vecteur2f vecteur){
            GLES30.glUniform2f(position,vecteur.x,vecteur.y);
        }

        public void chargerUniformeVecteur(int position, Vecteur4f vecteur){
            GLES30.glUniform4f(position,vecteur.x,vecteur.y,vecteur.z,vecteur.w);
        }

        public void chargerUniformeBool(int position, boolean bool){
            if(bool){
                GLES30.glUniform1i(position,1);
            }else{
                GLES30.glUniform1i(position,0);
            }
        }

        public void chargerUniformeMatrice(int position, Matrice4f matrice){
            GLES30.glUniformMatrix4fv(position,1,false,matrice.mat,0);
        }

        public void chargerUniformeTexture(int position, int unitéTexture){
            GLES30.glUniform1i(position, unitéTexture);
        }

        public void chargerUniformeInt(int position, int numéro){
            GLES30.glUniform1i(position,numéro);
        }

        public void commencer(){
            GLES30.glUseProgram(IDPrograme);
        }

        public void terminer(){
            GLES30.glUseProgram(0);
        }
}
