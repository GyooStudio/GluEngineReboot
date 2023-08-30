package com.gyoo.gluengine.Objects;

import com.gyoo.gluengine.Components.ComponentBase;

import java.util.ArrayList;

public class GameObject {

    public ArrayList<ComponentBase> components = new ArrayList<>();

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
}
