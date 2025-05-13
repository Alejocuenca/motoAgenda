package com.example.motoagenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SesionHelper {

    public static void cerrarSesion(Context context) {
        // Eliminar los datos de sesión
        SharedPreferences prefs = context.getSharedPreferences("sesion_actual", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("id_usuario");
        editor.remove("tipo_usuario");
        editor.apply();

        // Redirigir a la pantalla de login
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}

