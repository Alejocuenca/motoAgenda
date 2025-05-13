package com.example.motoagenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BuscarUsuario extends AppCompatActivity {

    EditText et_nombre, et_apellido, et_edad, et_correo, et_direccion, et_id_buscar, et_usuario, et_contrasena;
    Spinner sp_tipo_usuario;
    Button btn_buscar_usuario, btn_guardar_cambios_usuario, btn_eliminar_usuario;
    ImageButton btn_volver;
    BaseDeDatos baseDeDatos = new BaseDeDatos(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_id_buscar = findViewById(R.id.et_id_buscar);
        btn_buscar_usuario = findViewById(R.id.btn_buscar_usuario);
        btn_guardar_cambios_usuario = findViewById(R.id.btn_guardar_cambios_usuario);
        btn_eliminar_usuario = findViewById(R.id.btn_eliminar_usuario);
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
                new String[]{"administrador", "mototaxi"}
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

        btn_buscar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBuscarStr = et_id_buscar.getText().toString().trim();

                if (idBuscarStr.isEmpty()) {
                    Toast.makeText(BuscarUsuario.this, "Por favor, ingresa un ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                int idBuscar = Integer.parseInt(idBuscarStr);
                SQLiteDatabase db = baseDeDatos.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE id_usuario = ?", new String[]{String.valueOf(idBuscar)});

                if (cursor.moveToFirst()) {
                    et_nombre.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    et_apellido.setText(cursor.getString(cursor.getColumnIndexOrThrow("apellido")));
                    et_edad.setText(cursor.getString(cursor.getColumnIndexOrThrow("edad")));
                    et_correo.setText(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
                    et_direccion.setText(cursor.getString(cursor.getColumnIndexOrThrow("direccion")));
                    et_usuario.setText(cursor.getString(cursor.getColumnIndexOrThrow("usuario")));
                    et_contrasena.setText(cursor.getString(cursor.getColumnIndexOrThrow("contrasena")));

                    String tipoUsuario = cursor.getString(cursor.getColumnIndexOrThrow("tipo_usuario"));
                    ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) sp_tipo_usuario.getAdapter();
                    if (adapter != null) {
                        int pos = adapter.getPosition(tipoUsuario);
                        if (pos >= 0) {
                            sp_tipo_usuario.setSelection(pos);
                        }
                    }

                    btn_eliminar_usuario.setVisibility(View.VISIBLE);
                    btn_guardar_cambios_usuario.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(BuscarUsuario.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                db.close();
            }
        });

        btn_guardar_cambios_usuario.setOnClickListener(v -> {
            int id = Integer.parseInt(et_id_buscar.getText().toString());

            ContentValues valores = new ContentValues();
            valores.put("nombre", et_nombre.getText().toString());
            valores.put("apellido", et_apellido.getText().toString());
            valores.put("edad", et_edad.getText().toString());
            valores.put("correo", et_correo.getText().toString());
            valores.put("direccion", et_direccion.getText().toString());
            valores.put("usuario", et_usuario.getText().toString());
            valores.put("contrasena", et_contrasena.getText().toString());

            valores.put("tipo_usuario", sp_tipo_usuario.getSelectedItem().toString());

            boolean actualizado = baseDeDatos.actualizarUsuario(id, valores);

            if (actualizado) {
                Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BuscarUsuario.this, HomeAdministrador.class);
                startActivity(intent);
                finish();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
            }
        });

        btn_eliminar_usuario.setOnClickListener(v -> {
            int id = Integer.parseInt(et_id_buscar.getText().toString());

            boolean eliminado = baseDeDatos.eliminarUsuario(id);

            if (eliminado) {
                Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BuscarUsuario.this, HomeAdministrador.class);
                startActivity(intent);
                finish();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
            }
        });

    }

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
}