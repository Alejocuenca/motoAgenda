package com.example.motoagenda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText txt_usuario, txt_contrasena;
    Button btn_iniciar_sesion;
    BaseDeDatos baseDeDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_usuario = findViewById(R.id.txt_usuario);
        txt_contrasena = findViewById(R.id.txt_contrasena);
        btn_iniciar_sesion = findViewById(R.id.btn_iniciar_sesion);

        baseDeDatos = new BaseDeDatos(this);
        baseDeDatos.insertarUsuariosDePrueba(); //Elilminar esta linea

    }

    public void iniciarSesion(View view) {
        String usuario = txt_usuario.getText().toString().trim();
        String contrasena = txt_contrasena.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioAutenticado usuarioAutenticado = verificarCredenciales(usuario, contrasena);

        if (usuarioAutenticado != null) {
            Toast.makeText(this, "Inicio de sesión exitoso como " + usuarioAutenticado.tipo, Toast.LENGTH_SHORT).show();

            SharedPreferences prefs = getSharedPreferences("sesion_actual", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("id_usuario", usuarioAutenticado.id);
            editor.putString("tipo_usuario", usuarioAutenticado.tipo.toLowerCase());
            editor.apply();

            if (usuarioAutenticado.tipo.equalsIgnoreCase("administrador")) {

            } else if (usuarioAutenticado.tipo.equalsIgnoreCase("mototaxista")) {
                Intent intent = new Intent(MainActivity.this, HomeMototaxista.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
    
    public class UsuarioAutenticado {
        int id;
        String tipo;

        public UsuarioAutenticado(int id, String tipo) {
            this.id = id;
            this.tipo = tipo;
        }
    }

    private UsuarioAutenticado verificarCredenciales(String usuario, String contrasena) {
        SQLiteDatabase db = baseDeDatos.getReadableDatabase();
        UsuarioAutenticado usuarioAutenticado = null;

        Cursor cursor = db.rawQuery(
                "SELECT id_usuario, tipo_usuario FROM usuarios WHERE usuario = ? AND contrasena = ?",
                new String[]{usuario, contrasena}
        );

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String tipo = cursor.getString(1);
            usuarioAutenticado = new UsuarioAutenticado(id, tipo);
        }

        cursor.close();
        db.close();
        return usuarioAutenticado;
    }


}