package com.gyoo.gluengine.Components;

import com.gyoo.gluengine.Vectors.Matrix4f;
import com.gyoo.gluengine.Vectors.Vector2f;
import com.gyoo.gluengine.Vectors.Vector3f;

public class Transform2D{

    private Vector3f position = new Vector3f(0f);
    private float rotation = 0f;
    private Vector2f scale = new Vector2f(1f);

    public Matrix4f positionM = new Matrix4f();
    public Matrix4f rotationM = new Matrix4f();
    public Matrix4f scaleM = new Matrix4f();
    public Transform2D parent = null;

    public Transform2D(){
        positionM.translate(position);
        rotationM.rotate(new Vector3f(0f,0f,rotation));
        scaleM.scale(new Vector3f(scale.x,scale.y,1f));
    }

    public Matrix4f getTransformMatrix(){
        if(parent == null) {
            return Matrix4f.MultiplyMM(positionM, Matrix4f.MultiplyMM(rotationM, scaleM));
        }else {
            return Matrix4f.MultiplyMM( parent.getTransformMatrix(), Matrix4f.MultiplyMM(positionM, Matrix4f.MultiplyMM(rotationM, scaleM)) );
        }
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

    public void rotate(float r){
        rotation += r;
        rotationM.rotate(new Vector3f(0f,0f,r));
    }

    public void setRotation(float r){
        rotation = r;
        rotationM.setIdentity();
        rotationM.rotate(new Vector3f(0f,0f,r));
    }

    public float getRotation(){
        return rotation;
    }

    public void setScale(Vector2f s){
        scale = s;
        scaleM.setIdentity();
        scaleM.scale(new Vector3f(s.x,s.y,1f));
    }

    public void scale(Vector2f s){
        scale.add(s);
        scaleM.scale(new Vector3f(s.x,s.y,1f));
    }

    public Vector2f getScale(){
        return scale;
    }

    public void parent(Transform2D p){
        parent = p;
    }

    public  void unParent(){
        parent = null;
    }
}
