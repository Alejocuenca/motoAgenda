package com.example.motoagenda;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
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

    // Guarda el historial de operaciones para un usuario específico
    public static void guardarHistorialCalculadora(Context context, int idUsuario, String tipoUsuario, ArrayList<String> historial) {
        SharedPreferences prefs = context.getSharedPreferences("historial_calculadora_" + idUsuario + "_" + tipoUsuario, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("tamaño", historial.size());
        for (int i = 0; i < historial.size(); i++) {
            editor.putString("item_" + i, historial.get(i));
        }

        editor.apply();
    }

    // Recupera el historial de operaciones para un usuario específico
    public static ArrayList<String> recuperarHistorialCalculadora(Context context, int idUsuario, String tipoUsuario) {
        SharedPreferences prefs = context.getSharedPreferences("historial_calculadora_" + idUsuario + "_" + tipoUsuario, Context.MODE_PRIVATE);
        int tamaño = prefs.getInt("tamaño", 0);
        ArrayList<String> historial = new ArrayList<>();

        for (int i = 0; i < tamaño; i++) {
            historial.add(prefs.getString("item_" + i, ""));
        }

        return historial;
    }


}

