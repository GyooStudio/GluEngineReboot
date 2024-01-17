package com.gyoo.gluengine.Objects.GUI;

import com.gyoo.gluengine.Components.GTexture;
import com.gyoo.gluengine.Components.RawModel;
import com.gyoo.gluengine.Components.Shaders.GUIShader;
import com.gyoo.gluengine.Components.Transform2D;
import com.gyoo.gluengine.Vectors.Vector4f;
import com.gyoo.gluengine.utils.Loader;

public class GUIQuad {
    public RawModel mesh;
    public GUIShader shader;
    public Transform2D transform2D;
    public GTexture texture;
    public GUIQuad parent;

    private float[] positions = {
            -0.5f,-0.5f,0f,
            0.5f,-0.5f,0f,
            -0.5f,0.5f,0f,
            0.5f,0.5f,0f
    };

    public GUIQuad(){
        Loader loader = Loader.getLoader();
        mesh = Loader.loadToVAO(positions);
        shader = new GUIShader(loader.loadAssetText("Shaders/GUI.vert"),loader.loadAssetText("Shaders/GUI.frag"));
        shader.loadColor(new Vector4f(0.0f,0.0f,1.0f,1.0f));
        shader.buildShader();
        transform2D = new Transform2D();
    }

    public void addTexture(GTexture t){
        texture = t;
    }

    public void parent(GUIQuad p){
        parent = p;
        transform2D.parent(p.transform2D);
    }

    public void unParent(){
        parent = null;
        transform2D.unParent();
    }
}
