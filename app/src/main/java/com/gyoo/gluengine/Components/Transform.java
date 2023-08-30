package com.gyoo.gluengine.Components;

import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector3f;

public class Transform extends ComponentBase{

    public static int COMPONENT_TYPE = 1;

    private Vector3f position = new Vector3f(0f);
    private Vector3f rotation = new Vector3f(0f);
    private Vector3f scale = new Vector3f(1f);

    public Matrix4f positionM = new Matrix4f();
    public Matrix4f rotationM = new Matrix4f();
    public Matrix4f scaleM = new Matrix4f();

    public Transform(){
        super(1);
    }

    public Matrix4f getTransformMatrix(){
        return Matrix4f.MultiplyMM(positionM,Matrix4f.MultiplyMM(scaleM,rotationM));
    }

    public void setPosition(Vector3f p){
        position = p.copy();
        positionM.setIdentity();
        positionM.translate(p);
    }

    public void translate(Vector3f t){
        position.add(t);
        positionM.translate(t);
    }

    public Vector3f getPosition(){
        return position;
    }

    public void rotate(Vector3f r){
        rotation.add(r);
        rotationM.rotate(r);
    }

    public void setRotation(Vector3f r){
        rotation = r.copy();
        rotationM.setIdentity();
        rotationM.rotate(r);
    }

    public Vector3f getRotation(){
        return rotation;
    }

    public void setScale(Vector3f s){
        scale = s;
        scaleM.setIdentity();
        scaleM.scale(s);
    }

    public void scale(Vector3f s){
        scale.add(s);
        scaleM.scale(s);
    }

    public Vector3f getScale(){
        return scale;
    }
}
