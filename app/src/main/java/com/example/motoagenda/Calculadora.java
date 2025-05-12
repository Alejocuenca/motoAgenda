package com.example.motoagenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculadora extends AppCompatActivity {

    private TextView txt_pantalla;
    private Spinner spn_historial;

    private String entradaActual = "";
    private String operador = "";
    private double primerNumero = 0;
    private boolean esNuevaOperacion = true;
    private boolean resultadoMostrado = false;

    private ArrayList<String> historial = new ArrayList<>();
    private ArrayAdapter<String> adaptadorHistorial;

    private int idUsuario;
    private String tipoUsuario;
    ImageButton btn_volver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        txt_pantalla = findViewById(R.id.txt_pantalla);
        spn_historial = findViewById(R.id.spn_historial);
        btn_volver = findViewById(R.id.btn_volver);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adaptadorHistorial = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, historial);
        adaptadorHistorial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_historial.setAdapter(adaptadorHistorial);
        cargarHistorialDesdeSharedPreferences();

        SharedPreferences prefsSesion = getSharedPreferences("sesion_actual", MODE_PRIVATE);
        idUsuario = prefsSesion.getInt("id_usuario", -1);
        tipoUsuario = prefsSesion.getString("tipo_usuario", "");

        configurarBotonesNumericos();
        configurarBotonesOperadores();
        configurarBotonesExtras();
    }

    private void configurarBotonesNumericos() {
        int[] idsNumeros = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
                R.id.btn_8, R.id.btn_9
        };

        View.OnClickListener listener = v -> {
            if (esNuevaOperacion || resultadoMostrado) {
                entradaActual = "";
                esNuevaOperacion = false;
                resultadoMostrado = false;
            }
            Button btn = (Button) v;
            entradaActual += btn.getText().toString();
            txt_pantalla.setText(entradaActual);
        };

        for (int id : idsNumeros) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void configurarBotonesOperadores() {
        int[] idsOperadores = { R.id.btn_sumar, R.id.btn_restar, R.id.btn_multiplicar, R.id.btn_dividir };

        View.OnClickListener listener = v -> {
            if (!entradaActual.isEmpty()) {
                primerNumero = Double.parseDouble(entradaActual);
                Button btn = (Button) v;
                operador = btn.getText().toString();
                esNuevaOperacion = true;
            }
        };

        for (int id : idsOperadores) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void configurarBotonesExtras() {
        findViewById(R.id.btn_igual).setOnClickListener(v -> {
            if (!entradaActual.isEmpty() && !operador.isEmpty() && !resultadoMostrado) {
                double segundoNumero = Double.parseDouble(entradaActual);
                double resultado;

                switch (operador) {
                    case "+": resultado = primerNumero + segundoNumero; break;
                    case "-": resultado = primerNumero - segundoNumero; break;
                    case "*": resultado = primerNumero * segundoNumero; break;
                    case "/":
                        if (segundoNumero != 0) {
                            resultado = primerNumero / segundoNumero;
                        } else {
                            txt_pantalla.setText("Error: División entre 0");
                            return;
                        }
                        break;
                    default:
                        return;
                }

                String operacion = primerNumero + " " + operador + " " + segundoNumero + " = " + resultado;
                historial.add(operacion);
                guardarHistorialEnSharedPreferences();
                adaptadorHistorial.notifyDataSetChanged();

                Map<String, String> datos = new HashMap<>();
                datos.put("operacion_" + historial.size(), operacion);
                AdministradorDatosTemporales.guardarDatosTemporales(this, idUsuario, tipoUsuario, datos);

                txt_pantalla.setText(String.valueOf(resultado));
                resultadoMostrado = true;
                operador = "";
            }
        });

        findViewById(R.id.btn_limpiar).setOnClickListener(v -> {
            entradaActual = "";
            operador = "";
            primerNumero = 0;
            txt_pantalla.setText("0");
            esNuevaOperacion = true;
            resultadoMostrado = false;
        });

        findViewById(R.id.btn_punto).setOnClickListener(v -> {
            if (!entradaActual.contains(".")) {
                if (entradaActual.isEmpty()) entradaActual = "0";
                entradaActual += ".";
                txt_pantalla.setText(entradaActual);
            }
        });

        findViewById(R.id.btn_borrar_uno).setOnClickListener(v -> {
            if (!entradaActual.isEmpty()) {
                entradaActual = entradaActual.substring(0, entradaActual.length() - 1);
                txt_pantalla.setText(entradaActual.isEmpty() ? "0" : entradaActual);
            }
        });
    }

    private void guardarHistorialEnSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("historial_calculadora", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tamaño", historial.size());

        for (int i = 0; i < historial.size(); i++) {
            editor.putString("item_" + i, historial.get(i));
        }

        editor.apply();
    }

    private void cargarHistorialDesdeSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("historial_calculadora", MODE_PRIVATE);
        int tamaño = prefs.getInt("tamaño", 0);
        historial.clear();

        for (int i = 0; i < tamaño; i++) {
            historial.add(prefs.getString("item_" + i, ""));
        }

        if (adaptadorHistorial != null) {
            adaptadorHistorial.notifyDataSetChanged();
        }
    }


}
