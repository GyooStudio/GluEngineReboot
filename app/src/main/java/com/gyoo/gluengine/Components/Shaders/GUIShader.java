package com.gyoo.gluengine.Components.Shaders;

import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector2f;

public class GUIShader extends ShaderProgram{
    public static int COMPONENT_TYPE = 6;

    private int TRANSFORM;
    private int SCREEN_DIM;

    public GUIShader(String vertCode, String fragCode) {
        super(vertCode, fragCode, COMPONENT_TYPE);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"position");
    }

    @Override
    public void getAllUniforms() {
        TRANSFORM = super.getUniformLocation("Transform");
        SCREEN_DIM = super.getUniformLocation("screen");
    }

    public void loadTransform(Matrix4f t){
        super.loadUniformMatrix(TRANSFORM,t);
    }

    public void loadScreenDim(Vector2f s){
        super.loadUniformVector(SCREEN_DIM,s);
    }
}
