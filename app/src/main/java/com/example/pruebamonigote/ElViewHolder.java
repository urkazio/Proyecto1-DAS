package com.example.pruebamonigote;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ElViewHolder extends RecyclerView.ViewHolder {
    public TextView nombre;
    public TextView descripcion;

    public ImageView laimagen;

    /**
     * Para gestionar la interacción del usuario.
     * Hay que poder acceder a ese control de la selección desde
     * la clase que extiende ViewHolder
     **/
    public boolean[] seleccion;

    public ElViewHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.tvNombreEjer);
        descripcion = itemView.findViewById(R.id.tvSeriesRepes);
        laimagen = itemView.findViewById(R.id.imageView);

        /**listener del itemView para interraccionar con las imagenes al ser clickadas**/
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // obtener el nombre de la rutina ne minusculas y sin espacios ya
                // que asi se lama el txt que contiene los ejercicios

                //quitar espacios en blanco y ponerlo en minusculas
                String nombreRutina = nombre.getText().toString().replaceAll("\\s+","").toLowerCase();
                System.out.println(nombreRutina);

                String idImagen = String.valueOf(laimagen.getImageAlpha());
                System.out.println(idImagen);

                // solo quiero que se abra la ActividadEjerciciosRutinas si clicko en un
                // cardview estando en ActividadListaRutinasPred. El elemento diferenciador
                // de este cardview es que la imagen es el logo de mi app
                if (laimagen.getDrawable().getConstantState() == view.getContext().getResources().getDrawable( R.drawable.logo).getConstantState()){
                    Intent intent = new Intent(view.getContext(),ActividadEjerciciosRutinas.class);
                    intent.putExtra("nombreRutina", nombreRutina);
                    view.getContext().startActivity(intent);
                }


            }
        });

    }

}

