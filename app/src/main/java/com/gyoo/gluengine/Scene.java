package com.gyoo.gluengine;

import com.gyoo.gluengine.Components.GTexture;
import com.gyoo.gluengine.Components.RawModel;
import com.gyoo.gluengine.Components.Shaders.GUIShader;
import com.gyoo.gluengine.Components.Shaders.HelloTriangleShader;
import com.gyoo.gluengine.Components.Transform;
import com.gyoo.gluengine.Components.Transform2D;
import com.gyoo.gluengine.Objects.GUI.GUIQuad;

import java.util.ArrayList;

public class Scene {

    public ArrayList<GUIQuad> guiQuads = new ArrayList<>();

    public Scene(){}

    public void addGUIQuad(GUIQuad q){
        guiQuads.add(q);
    }
}
