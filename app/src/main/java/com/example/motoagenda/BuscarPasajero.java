package com.example.motoagenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class BuscarPasajero extends AppCompatActivity {

    EditText et_nombre, et_apellido, et_edad, et_correo, et_direccion, et_id_buscar;
    EditText et_institucion, et_titulo, et_anio_graduacion, et_cursos, et_habilidades;
    CheckBox cb_salsa, cb_baladas, cb_bachata, cb_rock;
    RadioGroup rg_genero;
    RadioButton rb_masculino, rb_femenino;
    Spinner sp_deporte;
    EditText et_otros_intereses;
    Button btn_buscar_pasajero, btn_guardar_cambios_pasajero, btn_eliminar_pasajero;
    ImageButton btn_volver;
    BaseDeDatos baseDeDatos = new BaseDeDatos(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar_pasajero);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_id_buscar = findViewById(R.id.et_id_buscar);
        btn_buscar_pasajero = findViewById(R.id.btn_buscar_pasajero);
        btn_guardar_cambios_pasajero = findViewById(R.id.btn_guardar_cambios_pasajero);
        btn_eliminar_pasajero = findViewById(R.id.btn_eliminar_pasajero);
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

        btn_buscar_pasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBuscarStr = et_id_buscar.getText().toString().trim();

                if (idBuscarStr.isEmpty()) {
                    Toast.makeText(BuscarPasajero.this, "Por favor, ingresa un ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                int idBuscar = Integer.parseInt(idBuscarStr);
                SQLiteDatabase db = baseDeDatos.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM pasajeros WHERE id_pasajero = ?", new String[]{String.valueOf(idBuscar)});

                if (cursor.moveToFirst()) {
                    // Información personal
                    et_nombre.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    et_apellido.setText(cursor.getString(cursor.getColumnIndexOrThrow("apellido")));
                    et_edad.setText(cursor.getString(cursor.getColumnIndexOrThrow("edad")));
                    et_correo.setText(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
                    et_direccion.setText(cursor.getString(cursor.getColumnIndexOrThrow("direccion")));

                    // Información académica
                    et_institucion.setText(cursor.getString(cursor.getColumnIndexOrThrow("institucion")));
                    et_titulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("carrera")));
                    et_anio_graduacion.setText(cursor.getString(cursor.getColumnIndexOrThrow("anio_graduacion")));
                    et_cursos.setText(cursor.getString(cursor.getColumnIndexOrThrow("cursos")));
                    et_habilidades.setText(cursor.getString(cursor.getColumnIndexOrThrow("habilidades")));

                    String musica = cursor.getString(cursor.getColumnIndexOrThrow("musica"));

                    cb_salsa.setChecked(musica.contains("Salsa"));
                    cb_baladas.setChecked(musica.contains("Baladas"));
                    cb_bachata.setChecked(musica.contains("Bachata"));
                    cb_rock.setChecked(musica.contains("Rock"));


                    // Género
                    String genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"));
                    if (genero != null) {
                        if (genero.equals("Masculino")) {
                            rb_masculino.setChecked(true);
                        } else if (genero.equals("Femenino")) {
                            rb_femenino.setChecked(true);
                        }
                    }

                    // Deporte (Spinner)
                    String deporte = cursor.getString(cursor.getColumnIndexOrThrow("deporte"));
                    ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) sp_deporte.getAdapter();
                    if (adapter != null) {
                        int pos = adapter.getPosition(deporte);
                        if (pos >= 0) {
                            sp_deporte.setSelection(pos);
                        }
                    }

                    // Otros intereses
                    et_otros_intereses.setText(cursor.getString(cursor.getColumnIndexOrThrow("otros_intereses")));

                    btn_eliminar_pasajero.setVisibility(View.VISIBLE);
                    btn_guardar_cambios_pasajero.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(BuscarPasajero.this, "Pasajero no encontrado", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                db.close();
            }
        });

        btn_guardar_cambios_pasajero.setOnClickListener(v -> {
            int id = Integer.parseInt(et_id_buscar.getText().toString());

            ContentValues valores = new ContentValues();
            valores.put("nombre", et_nombre.getText().toString());
            valores.put("apellido", et_apellido.getText().toString());
            valores.put("edad", et_edad.getText().toString());
            valores.put("correo", et_correo.getText().toString());
            valores.put("direccion", et_direccion.getText().toString());
            valores.put("institucion", et_institucion.getText().toString());
            valores.put("carrera", et_titulo.getText().toString());
            valores.put("anio_graduacion", et_anio_graduacion.getText().toString());
            valores.put("cursos", et_cursos.getText().toString());
            valores.put("habilidades", et_habilidades.getText().toString());
            valores.put("otros_intereses", et_otros_intereses.getText().toString());

            String preferenciasMusicales = "";
            if (cb_salsa.isChecked()) preferenciasMusicales += "Salsa ";
            if (cb_baladas.isChecked()) preferenciasMusicales += "Baladas ";
            if (cb_bachata.isChecked()) preferenciasMusicales += "Bachata ";
            if (cb_rock.isChecked()) preferenciasMusicales += "Rock ";

            valores.put("musica", preferenciasMusicales.trim());


            // Género
            String genero = rb_masculino.isChecked() ? "Masculino" : rb_femenino.isChecked() ? "Femenino" : "";
            valores.put("genero", genero);

            // Deporte
            valores.put("deporte", sp_deporte.getSelectedItem().toString());

            // Guardar en BD

            boolean actualizado = baseDeDatos.actualizarPasajero(id, valores);

            if (actualizado) {
                Toast.makeText(this, "Pasajero actualizado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BuscarPasajero.this, HomeMototaxista.class);
                startActivity(intent);
                finish();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al actualizar el pasajero", Toast.LENGTH_SHORT).show();
            }
        });

        btn_eliminar_pasajero.setOnClickListener(v -> {
            int id = Integer.parseInt(et_id_buscar.getText().toString());

            boolean eliminado = baseDeDatos.eliminarPasajero(id);

            if (eliminado) {
                Toast.makeText(this, "Pasajero eliminado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BuscarPasajero.this, HomeMototaxista.class);
                startActivity(intent);
                finish();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al eliminar el pasajero", Toast.LENGTH_SHORT).show();
            }
        });
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

}