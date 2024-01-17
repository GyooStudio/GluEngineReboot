package com.gyoo.gluengine.Components.Shaders;

import com.gyoo.gluengine.Components.GTexture;
import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector2f;
import com.gyoo.gluengine.Vectors.Vector4f;

public class GUIShader extends ShaderProgram{
    public static int COMPONENT_TYPE = 6;

    private int TRANSFORM;
    private int SCREEN_DIM;
    private int COLOR;
    private int IS_TEXTURED;

    public Vector4f color = new Vector4f(1f);

    public GUIShader(String vertCode, String fragCode) {
        super(vertCode, fragCode);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"position");
    }

    @Override
    public void getAllUniforms() {
        TRANSFORM = super.getUniformLocation("Transform");
        SCREEN_DIM = super.getUniformLocation("screen");
        COLOR = super.getUniformLocation("color");
        IS_TEXTURED = super.getUniformLocation("isTextured");
    }

    public void loadTransform(Matrix4f t){
        super.loadUniformMatrix(TRANSFORM,t);
    }

    public void loadScreenDim(Vector2f s){
        super.loadUniformVector(SCREEN_DIM,s);
    }
    public void loadColor(Vector4f c){
        super.loadUniformVector(COLOR,c);
    }
    public void isTextured(boolean i){
        super.loadUniformBoolean(IS_TEXTURED,i);
    }
}
