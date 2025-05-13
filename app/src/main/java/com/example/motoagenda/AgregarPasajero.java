package com.example.motoagenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class AgregarPasajero extends AppCompatActivity {

    EditText et_nombre, et_apellido, et_edad, et_correo, et_direccion;
    EditText et_institucion, et_titulo, et_anio_graduacion, et_cursos, et_habilidades;
    CheckBox cb_salsa, cb_baladas, cb_bachata, cb_rock;
    RadioGroup rg_genero;
    RadioButton rb_masculino, rb_femenino;
    Spinner sp_deporte;
    EditText et_otros_intereses;
    Button btn_agregar_pasajero, btn_cancelar_agregar_pasajero;
    ImageButton btn_volver;

    public abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }

    private void limpiarCampos() {
        et_nombre.setText("");
        et_apellido.setText("");
        et_edad.setText("");
        et_correo.setText("");
        et_direccion.setText("");
        et_institucion.setText("");
        et_titulo.setText("");
        et_anio_graduacion.setText("");
        et_cursos.setText("");
        et_habilidades.setText("");
        et_otros_intereses.setText("");

        cb_salsa.setChecked(false);
        cb_baladas.setChecked(false);
        cb_bachata.setChecked(false);
        cb_rock.setChecked(false);

        rg_genero.clearCheck();

        sp_deporte.setSelection(0);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_pasajero);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences prefsSesion = getSharedPreferences("sesion_actual", MODE_PRIVATE);
        int idUsuario = prefsSesion.getInt("id_usuario", -1);
        String tipoUsuario = prefsSesion.getString("tipo_usuario", "");

        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_edad = findViewById(R.id.et_edad);
        et_correo = findViewById(R.id.et_correo);
        et_direccion = findViewById(R.id.et_direccion);
        et_institucion = findViewById(R.id.et_institucion);
        et_titulo = findViewById(R.id.et_titulo);
        et_anio_graduacion = findViewById(R.id.et_anio_graduacion);
        et_cursos = findViewById(R.id.et_cursos);
        et_habilidades = findViewById(R.id.et_habilidades);
        et_otros_intereses = findViewById(R.id.et_otros_intereses);

        cb_salsa = findViewById(R.id.cb_salsa);
        cb_baladas = findViewById(R.id.cb_baladas);
        cb_bachata = findViewById(R.id.cb_bachata);
        cb_rock = findViewById(R.id.cb_rock);

        rg_genero = findViewById(R.id.rg_genero);
        rb_masculino = findViewById(R.id.rb_masculino);
        rb_femenino = findViewById(R.id.rb_femenino);

        sp_deporte = findViewById(R.id.sp_deporte);
        ArrayAdapter<String> adapterDeportes = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Fútbol", "Natación", "Baloncesto", "Boxeo", "Ciclismo", "Otro"}
        );
        adapterDeportes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_deporte.setAdapter(adapterDeportes);


        btn_volver = findViewById(R.id.btn_volver);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_agregar_pasajero = findViewById(R.id.btn_agregar_pasajero);
        btn_cancelar_agregar_pasajero = findViewById(R.id.btn_cancelar_agregar_pasajero);

        btn_cancelar_agregar_pasajero.setOnClickListener(v -> {
            AdministradorDatosTemporales.limpiarDatosTemporales(AgregarPasajero.this, idUsuario, tipoUsuario);
            Intent intent = new Intent(AgregarPasajero.this, HomeMototaxista.class);
            startActivity(intent);
            finish();
            limpiarCampos();
        });

        btn_agregar_pasajero.setOnClickListener(v -> {

            String nombre = et_nombre.getText().toString().trim();
            String apellido = et_apellido.getText().toString().trim();
            String edad = et_edad.getText().toString().trim();
            String correo = et_correo.getText().toString().trim();
            String direccion = et_direccion.getText().toString().trim();
            String institucion = et_institucion.getText().toString().trim();
            String titulo = et_titulo.getText().toString().trim();
            String anioGraduacion = et_anio_graduacion.getText().toString().trim();
            String cursos = et_cursos.getText().toString().trim();
            String habilidades = et_habilidades.getText().toString().trim();
            String otros = et_otros_intereses.getText().toString().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || correo.isEmpty() || direccion.isEmpty()
                    || institucion.isEmpty() || titulo.isEmpty() || anioGraduacion.isEmpty() || cursos.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            String genero;
            if (rb_masculino.isChecked()) {
                genero = "Masculino";
            } else if (rb_femenino.isChecked()) {
                genero = "Femenino";
            } else {
                genero = "Masculino";
            }

            String preferenciasMusicales = "";
            if (cb_salsa.isChecked()) preferenciasMusicales += "Salsa ";
            if (cb_baladas.isChecked()) preferenciasMusicales += "Baladas ";
            if (cb_bachata.isChecked()) preferenciasMusicales += "Bachata ";
            if (cb_rock.isChecked()) preferenciasMusicales += "Rock ";

            String deporte = sp_deporte.getSelectedItem().toString();

            BaseDeDatos db = new BaseDeDatos(this);
            boolean exito = db.insertarPasajero(nombre, apellido, Integer.parseInt(edad), correo, direccion,
                    institucion, titulo, anioGraduacion, cursos, habilidades,
                    preferenciasMusicales.trim(), genero, deporte, otros, idUsuario);

            if (exito) {
                AdministradorDatosTemporales.limpiarDatosTemporales(AgregarPasajero.this, idUsuario, tipoUsuario);
                Toast.makeText(this, "Pasajero agregado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AgregarPasajero.this, HomeMototaxista.class);
                startActivity(intent);
                finish();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al guardar el pasajero", Toast.LENGTH_SHORT).show();
            }

        });

        Map<String, EditText> campos = new HashMap<>();
        campos.put("nombre", et_nombre);
        campos.put("apellido", et_apellido);
        campos.put("edad", et_edad);
        campos.put("correo", et_correo);
        campos.put("direccion", et_direccion);
        campos.put("institucion", et_institucion);
        campos.put("titulo", et_titulo);
        campos.put("anioGraduacion", et_anio_graduacion);
        campos.put("cursos", et_cursos);
        campos.put("habilidades", et_habilidades);
        campos.put("otros", et_otros_intereses);

        for (Map.Entry<String, EditText> campo : campos.entrySet()) {
            campo.getValue().addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    Map<String, String> datos = new HashMap<>();
                    datos.put(campo.getKey(), s.toString());
                    AdministradorDatosTemporales.guardarDatosTemporales(AgregarPasajero.this, idUsuario, tipoUsuario, datos);
                }
            });
        }

        Map<String, String> datosRecuperados = AdministradorDatosTemporales.recuperarDatosTemporales(this, idUsuario, tipoUsuario);

        for (Map.Entry<String, EditText> campo : campos.entrySet()) {
            String valor = datosRecuperados.get(campo.getKey());
            if (valor != null) campo.getValue().setText(valor);
        }

        cb_salsa.setChecked("true".equals(datosRecuperados.get("salsa")));
        cb_baladas.setChecked("true".equals(datosRecuperados.get("baladas")));
        cb_bachata.setChecked("true".equals(datosRecuperados.get("bachata")));
        cb_rock.setChecked("true".equals(datosRecuperados.get("rock")));

        CompoundButton.OnCheckedChangeListener checkListener = (buttonView, isChecked) -> {
            Map<String, String> datos = new HashMap<>();
            datos.put(buttonView.getTag().toString(), String.valueOf(isChecked));
            AdministradorDatosTemporales.guardarDatosTemporales(AgregarPasajero.this, idUsuario, tipoUsuario, datos);
        };

        cb_salsa.setTag("salsa");
        cb_baladas.setTag("baladas");
        cb_bachata.setTag("bachata");
        cb_rock.setTag("rock");

        cb_salsa.setOnCheckedChangeListener(checkListener);
        cb_baladas.setOnCheckedChangeListener(checkListener);
        cb_bachata.setOnCheckedChangeListener(checkListener);
        cb_rock.setOnCheckedChangeListener(checkListener);

        // Restaurar RadioButton
        String genero = datosRecuperados.get("genero");
        if ("Masculino".equals(genero)) rb_masculino.setChecked(true);
        else if ("Femenino".equals(genero)) rb_femenino.setChecked(true);

        // Guardar RadioButton
        rb_masculino.setOnClickListener(v -> {
            Map<String, String> datos = new HashMap<>();
            datos.put("genero", "Masculino");
            AdministradorDatosTemporales.guardarDatosTemporales(this, idUsuario, tipoUsuario, datos);
        });

        rb_femenino.setOnClickListener(v -> {
            Map<String, String> datos = new HashMap<>();
            datos.put("genero", "Femenino");
            AdministradorDatosTemporales.guardarDatosTemporales(this, idUsuario, tipoUsuario, datos);
        });

        // Restaurar Spinner
        String deporte = datosRecuperados.get("deporte");
        if (deporte != null) {
            ArrayAdapter adapter = (ArrayAdapter) sp_deporte.getAdapter();
            int position = adapter.getPosition(deporte);
            if (position >= 0) sp_deporte.setSelection(position);
        }

        // Guardar Spinner
        sp_deporte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> datos = new HashMap<>();
                datos.put("deporte", parent.getItemAtPosition(position).toString());
                AdministradorDatosTemporales.guardarDatosTemporales(AgregarPasajero.this, idUsuario, tipoUsuario, datos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

}