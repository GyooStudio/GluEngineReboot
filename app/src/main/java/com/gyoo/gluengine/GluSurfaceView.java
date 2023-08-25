package com.gyoo.gluengine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class GluSurfaceView extends GLSurfaceView {

    com.gyoo.gluengine.Renderer renderer;
    public GluSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);

        renderer = new com.gyoo.gluengine.Renderer();
        setRenderer(renderer);
        Log.w("GluSurfaceView", "SurfaceView created");
    }

}
