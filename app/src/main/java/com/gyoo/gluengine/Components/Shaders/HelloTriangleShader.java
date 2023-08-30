package com.gyoo.gluengine.Components.Shaders;

import com.gyoo.gluengine.Vectors.Matrix4f;

public class HelloTriangleShader extends ShaderProgram {

    public static int COMPONENT_TYPE = 4;

    private int TRANS;
    public HelloTriangleShader(String vertCode, String fragCode) {
        super(vertCode, fragCode, COMPONENT_TYPE);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"position");
    }

    @Override
    public void getAllUniforms() {
        TRANS = super.getUniformLocation("Transform");
    }

    public void loadTransformMat(Matrix4f t){
        super.loadUniformMatrix(TRANS, t);
    }
}
