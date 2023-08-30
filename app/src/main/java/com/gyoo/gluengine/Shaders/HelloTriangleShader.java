package com.gyoo.gluengine.Shaders;

import com.gyoo.gluengine.Vectors.Matrix4f;

public class HelloTriangleShader extends ShaderProgram {

    private int PROJ;
    private int TRANS;
    public HelloTriangleShader(String vertCode, String fragCode) {
        super(vertCode, fragCode);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0,"position");
    }

    @Override
    public void getAllUniforms() {
        PROJ = super.getUniformLocation("Proj");
        TRANS = super.getUniformLocation("Transform");
    }

    public void loadProjectionMat(Matrix4f p){
        super.loadUniformMatix(PROJ,p);
    }

    public void loadTransformMat(Matrix4f t){
        super.loadUniformMatix(TRANS, t);
    }
}
