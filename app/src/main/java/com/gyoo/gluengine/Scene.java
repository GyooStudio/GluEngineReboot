package com.gyoo.gluengine;

import com.gyoo.gluengine.Components.GTexture;
import com.gyoo.gluengine.Components.RawModel;
import com.gyoo.gluengine.Components.Shaders.GUIShader;
import com.gyoo.gluengine.Components.Shaders.HelloTriangleShader;
import com.gyoo.gluengine.Components.Transform;
import com.gyoo.gluengine.Components.Transform2D;
import com.gyoo.gluengine.Objects.GameObject;

import java.util.ArrayList;

public class Scene {
    public ArrayList<GameObject> gameObjects = new ArrayList<>();

    /*public ArrayList<RawModel> rawModels = new ArrayList<>();
    public ArrayList<GTexture> gTextures = new ArrayList<>();
    public ArrayList<Transform> transforms = new ArrayList<>();
    public ArrayList<Transform2D> transform2DS = new ArrayList<>();
    public ArrayList<GUIShader> guiShaders = new ArrayList<>();
    public ArrayList<HelloTriangleShader> */

    public Scene(){}

    public void addObject(GameObject o){
        gameObjects.add(o);
    }
}
