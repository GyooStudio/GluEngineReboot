package com.gyoo.gluengine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.gyoo.gluengine.utils.Loader;

public class MainActivity extends AppCompatActivity {

    private GluSurfaceView gluSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.w("Glu Engine", "HelloWorld");

        ActivityManager am = ( ActivityManager ) getSystemService ( Context.ACTIVITY_SERVICE );
        ConfigurationInfo info = am.getDeviceConfigurationInfo();

        if(!(Float.parseFloat(info.getGlEsVersion()) >= 3.0f)){
            Log.e("Glu Engine",info.getGlEsVersion() + " is the maximum supported OpenGL version. 3.0 is required, exiting...");
            System.exit(-1);
        }else{
            Log.w("Glu Engine","OpenGL es " + info.getGlEsVersion() + " supported, higher than 3.0, proceeding.");
        }

        gluSurfaceView = new GluSurfaceView(this);

        Loader.init(this);

        setContentView(gluSurfaceView);

        Log.w("MainActivity", "Surfaceview created");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
