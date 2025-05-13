package com.example.motoagenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeAdministrador extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleViewAdapterAdministrador mAdapterAdmistrador;
    private RecyclerView.LayoutManager layoutManager ;
    private List<Administrador> ListaAdministradores = new ArrayList<Administrador>();
    private BaseDeDatos baseDeDatos;
    FloatingActionButton fab_agregar_usuario;
    ImageButton btn_buscar, btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_administrador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_buscar = findViewById(R.id.btn_buscar);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdministrador.this, BuscarUsuario.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rv_listaAdministradores);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapterAdmistrador = new RecycleViewAdapterAdministrador(ListaAdministradores, HomeAdministrador.this);
        recyclerView.setAdapter(mAdapterAdmistrador);

        baseDeDatos = new BaseDeDatos(this);
        cargarDatosDesdeBD();

        fab_agregar_usuario = findViewById(R.id.fab_agregar_usuario);

        fab_agregar_usuario.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdministrador.this, AgregarUsuario.class);
            startActivity(intent);
        });
    }
    private void cargarDatosDesdeBD() {
        SharedPreferences prefsSesion = getSharedPreferences("sesion_actual", MODE_PRIVATE);
        int idUsuario = prefsSesion.getInt("id_usuario", -1);
        ListaAdministradores = baseDeDatos.obtenerTodosLosUsuarios(idUsuario);

        if (mAdapterAdmistrador == null) {
            mAdapterAdmistrador = new RecycleViewAdapterAdministrador(ListaAdministradores, HomeAdministrador.this);
            recyclerView.setAdapter(mAdapterAdmistrador);
        } else {
            mAdapterAdmistrador.actualizarLista(ListaAdministradores);
        }
    }
}