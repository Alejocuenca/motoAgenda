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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class AgregarUsuario extends AppCompatActivity {

    EditText et_nombre, et_apellido, et_edad, et_correo, et_direccion, et_usuario, et_contrasena;
    Spinner sp_tipo_usuario;
    ImageButton btn_volver;
    Button btn_agregar_usuario, btn_cancelar_agregar_usuario;
    private BaseDeDatos baseDeDatos;

    private void limpiarCampos() {
        et_nombre.setText("");
        et_apellido.setText("");
        et_edad.setText("");
        et_correo.setText("");
        et_direccion.setText("");
        et_usuario.setText("");
        et_contrasena.setText("");
        sp_tipo_usuario.setSelection(0);
    }

    public abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefsSesion = getSharedPreferences("sesion_actual", MODE_PRIVATE);
        int idUsuario = prefsSesion.getInt("id_usuario", -1);
        String tipoUsuario = prefsSesion.getString("tipo_usuario", "");

        baseDeDatos = new BaseDeDatos(this);

        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_edad = findViewById(R.id.et_edad);
        et_correo = findViewById(R.id.et_correo);
        et_direccion = findViewById(R.id.et_direccion);
        et_usuario = findViewById(R.id.et_usuario);
        et_contrasena = findViewById(R.id.et_contrasena);

        sp_tipo_usuario = findViewById(R.id.sp_tipo_usuario);
        ArrayAdapter<String> adapterOpcionesUsuario = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"administrador", "mototaxista"}
        );
        adapterOpcionesUsuario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipo_usuario.setAdapter(adapterOpcionesUsuario);

        btn_volver = findViewById(R.id.btn_volver);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_agregar_usuario = findViewById(R.id.btn_agregar_usuario);
        btn_cancelar_agregar_usuario = findViewById(R.id.btn_cancelar_agregar_usuario);

        btn_cancelar_agregar_usuario.setOnClickListener(v -> {
            AdministradorDatosTemporales.limpiarDatosTemporales(AgregarUsuario.this, idUsuario, tipoUsuario);
            Intent intent = new Intent(AgregarUsuario.this, HomeAdministrador.class);
            startActivity(intent);
            finish();
            limpiarCampos();
        });

        btn_agregar_usuario.setOnClickListener(v -> {

            String nombre = et_nombre.getText().toString().trim();
            String apellido = et_apellido.getText().toString().trim();
            String edad = et_edad.getText().toString().trim();
            String correo = et_correo.getText().toString().trim();
            String direccion = et_direccion.getText().toString().trim();
            String usuario = et_usuario.getText().toString().trim();
            String contrasena = et_contrasena.getText().toString().trim();
            String tipoUsuarioSeleccionado = sp_tipo_usuario.getSelectedItem().toString();

            if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || correo.isEmpty() || direccion.isEmpty()
                    || usuario.isEmpty() || contrasena.isEmpty() || tipoUsuarioSeleccionado.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean exito = baseDeDatos.insertarUsuario(nombre, apellido, Integer.parseInt(edad), correo, direccion,
                    usuario, contrasena, tipoUsuarioSeleccionado, idUsuario);

            if (exito) {
                AdministradorDatosTemporales.limpiarDatosTemporales(AgregarUsuario.this, idUsuario, tipoUsuario);
                Toast.makeText(this, "Usuario agregado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AgregarUsuario.this, HomeAdministrador.class);
                startActivity(intent);
                finish();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show();
            }
        });

        Map<String, EditText> campos = new HashMap<>();
        campos.put("nombre", et_nombre);
        campos.put("apellido", et_apellido);
        campos.put("edad", et_edad);
        campos.put("correo", et_correo);
        campos.put("direccion", et_direccion);
        campos.put("usuario", et_usuario);
        campos.put("titulo", et_contrasena);

        for (Map.Entry<String, EditText> campo : campos.entrySet()) {
            campo.getValue().addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    Map<String, String> datos = new HashMap<>();
                    datos.put(campo.getKey(), s.toString());
                    AdministradorDatosTemporales.guardarDatosTemporales(AgregarUsuario.this, idUsuario, tipoUsuario, datos);
                }
            });
        }

        Map<String, String> datosRecuperados = AdministradorDatosTemporales.recuperarDatosTemporales(this, idUsuario, tipoUsuario);

        for (Map.Entry<String, EditText> campo : campos.entrySet()) {
            String valor = datosRecuperados.get(campo.getKey());
            if (valor != null) campo.getValue().setText(valor);
        }

        String tipo_usuario = datosRecuperados.get("tipo_usuario");
        if (tipo_usuario != null) {
            ArrayAdapter adapter = (ArrayAdapter) sp_tipo_usuario.getAdapter();
            int position = adapter.getPosition(tipo_usuario);
            if (position >= 0) sp_tipo_usuario.setSelection(position);
        }

        sp_tipo_usuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> datos = new HashMap<>();
                datos.put("tipo_usuario", parent.getItemAtPosition(position).toString());
                AdministradorDatosTemporales.guardarDatosTemporales(AgregarUsuario.this, idUsuario, tipoUsuario, datos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
}