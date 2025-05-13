package com.example.motoagenda;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdministrador extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleViewAdapterAdministrador mAdapterAdmistrador;
    private RecyclerView.LayoutManager layoutManager ;
    private List<Administrador> ListaAdministradores = new ArrayList<Administrador>();
    private BaseDeDatos baseDeDatos;

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

        recyclerView = findViewById(R.id.rv_listaAdministradores);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapterAdmistrador = new RecycleViewAdapterAdministrador(ListaAdministradores, HomeAdministrador.this);
        recyclerView.setAdapter(mAdapterAdmistrador);

        baseDeDatos = new BaseDeDatos(this);
        cargarDatosDesdeBD();
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