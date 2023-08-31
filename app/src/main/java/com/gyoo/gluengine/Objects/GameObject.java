package com.gyoo.gluengine.Objects;

import com.gyoo.gluengine.Components.ComponentBase;
import com.gyoo.gluengine.Components.Transform;
import com.gyoo.gluengine.Components.Transform2D;

import java.util.ArrayList;

public class GameObject {

    public ArrayList<ComponentBase> components = new ArrayList<>();

    public GameObject parent = null;
    //public ArrayList<GameObject> childs = new ArrayList<>();
    public boolean isTransparent = false;

    public GameObject(){}

    public void addComponent(ComponentBase c){
        components.add(c);
    }

    public <E> E getComponent(int componentType){
        for (int i = 0; i < components.size(); i++) {
            if( components.get(i).componentType == componentType){
                return (E) components.get(i);
            }
        }
        return null;
    }

    public void parent(GameObject p){
        parent = p;
        Transform t = getComponent(Transform.COMPONENT_TYPE);
        Transform2D t2D = getComponent(Transform2D.COMPONENT_TYPE);

        if(t != null){
            t.parent(p);
        }else if(t2D != null){
            t2D.parent(p);
        }
    }

    public void unParent(){
        parent = null;
        Transform t = getComponent(Transform.COMPONENT_TYPE);
        Transform2D t2D = getComponent(Transform2D.COMPONENT_TYPE);

        if(t != null){
            t.unParent();
        }else if(t2D != null){
            t2D.unParent();
        }
    }
}
