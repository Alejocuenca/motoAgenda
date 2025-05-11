package com.example.motoagenda;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

public class AdministradorDatosTemporales {
    public static void guardarDatosTemporales(Context context, int idUsuario, String tipoUsuario, Map<String, String> datos) {
        SharedPreferences prefs = context.getSharedPreferences("datos_temporales_" + idUsuario + "_" + tipoUsuario, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for (Map.Entry<String, String> entry : datos.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.apply();
    }

    public static Map<String, String> recuperarDatosTemporales(Context context, int idUsuario, String tipoUsuario) {
        SharedPreferences prefs = context.getSharedPreferences("datos_temporales_" + idUsuario + "_" + tipoUsuario, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            result.put(entry.getKey(), entry.getValue().toString());
        }
        return result;
    }

    public static void limpiarDatosTemporales(Context context, int idUsuario, String tipoUsuario) {
        SharedPreferences prefs = context.getSharedPreferences("datos_temporales_" + idUsuario + "_" + tipoUsuario, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
