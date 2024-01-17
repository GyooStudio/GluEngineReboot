package com.gyoo.gluengine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.gyoo.gluengine.Vecteurs.Vecteur2f;
import com.gyoo.gluengine.utils.Chargeur;

public class MainActivity extends AppCompatActivity {

    private GluSurfaceView gluSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.w("Glu Engine", "Bonjour tout le monde!");

        ActivityManager am = ( ActivityManager ) getSystemService ( Context.ACTIVITY_SERVICE );
        ConfigurationInfo info = am.getDeviceConfigurationInfo();

        if(!(Float.parseFloat(info.getGlEsVersion()) >= 3.0f)){
            Log.e("Glu Engine",info.getGlEsVersion() + " est la version maximale supportée de OpenGL es, qui est inférieure à 3.0. Terminaison du programme...");
            System.exit(-1);
        }else{
            Log.w("Glu Engine","OpenGL es " + info.getGlEsVersion() + " est supporté et est plus grand ou égale à 3.0. Continuer.");
        }

        gluSurfaceView = new GluSurfaceView(this);

        Chargeur.initier(this);
        Ressources ressources = Ressources.avoirRessoucres();
        ressources.dimÉcranPixels = new Vecteur2f(getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels);
        ressources.densitéPixel = getResources().getDisplayMetrics().xdpi;

        setContentView(gluSurfaceView);

        Log.w("MainActivity", "Surfaceview créé");

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
