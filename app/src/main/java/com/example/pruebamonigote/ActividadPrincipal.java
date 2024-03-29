package com.example.pruebamonigote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class ActividadPrincipal extends AppCompatActivity {

    private static boolean preferenciasCargadas = false;
    String user ="";
    AlertDialog.Builder dialog;
    public static boolean dialogo = false;
    private Context c = this;
    private Activity a = this;
    public static ActividadPrincipal actividadPrincipal;
    DrawerLayout elmenudesplegable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(c,a);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        actividadPrincipal = this;

        //en caso de venir de la actividad registrarse, hay que cerrarla
        if (ActividadRegistrarse.actividadregistrarse!=null){
            ActividadRegistrarse.actividadregistrarse.finish();
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("User");
        }

        // pedir el permiso para pedir notifiaciones
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        TextView tvRutinasPers = findViewById(R.id.tvRutinasPers);
        tvRutinasPers.setText(getString(R.string.str117)+" "+user+"! "+getString(R.string.str25));


        // crear el fichero "Nombreuser.txt" que contiene el indice de las rutinas creadas por el usuario 'user' en caso de no existir
        // path --> /app/app/com.example.pruebamonigote/files/Nombreuser.txt
        if (!fileExist(user+".txt")){
            try {
                OutputStreamWriter fichero = new OutputStreamWriter(c.openFileOutput(user+".txt", Context.MODE_PRIVATE));
                fichero.close();
            } catch (IOException e) { }
        }

        //gestion de rotacion
        if (dialogo){
            DialogoCrearRutina dR = new DialogoCrearRutina();
            dR.onCreateDialog(this, user);
        }

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas=false;
    }


    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public void explorar(View v) {
        Intent intent = new Intent(this, ActividadListaRutinasPred.class);
        startActivity(intent);
    }
    public void crear(View v) {
        DialogoCrearRutina dR = new DialogoCrearRutina();
        dR.onCreateDialog(this, user);
        ActividadPrincipal.dialogo=true;
    }

    public void ver(View v) {
        Intent intent = new Intent(this, ActividadMisRutinas.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    /*********************************************************
     *                          IDIOMAS
     *********************************************************/
    public void idiomaIngles(View v) {
        GestorIdiomas.storeLang="en";
        GestorIdiomas.cambiarIdioma("en",c,a);
        GestorIdiomas.guardarPreferencias(c,a);
        a.finish();
        a.startActivity(a.getIntent());
    }
    public void idiomaEspañol(View v) {
        GestorIdiomas.storeLang="es";
        GestorIdiomas.cambiarIdioma("es",c,a);
        GestorIdiomas.guardarPreferencias(c,a);
        a.finish();
        a.startActivity(a.getIntent());
    }
}

