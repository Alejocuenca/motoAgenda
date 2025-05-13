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

import java.util.List;

public class HomeMototaxista extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager ;
    private List<Pasajero> ListaPasajeros;
    private BaseDeDatos baseDeDatos;

    FloatingActionButton fab_agregar_pasajero, fab_calculadora;

    ImageButton btn_buscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_mototaxista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_buscar = findViewById(R.id.btn_buscar);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMototaxista.this, BuscarPasajero.class);
                startActivity(intent);
            }
        });

        fab_agregar_pasajero = findViewById(R.id.fab_agregar_pasajero);
        fab_calculadora = findViewById(R.id.fab_calculadora);

        fab_agregar_pasajero.setOnClickListener(v -> {
            Intent intent = new Intent(HomeMototaxista.this, AgregarPasajero.class);
            startActivity(intent);
        });

        fab_calculadora.setOnClickListener(v -> {
            Intent intent = new Intent(HomeMototaxista.this, Calculadora.class);
            startActivity(intent);
        });

        baseDeDatos = new BaseDeDatos(this);
        baseDeDatos.insertarPasajerosDePrueba();

        recyclerView = findViewById(R.id.rv_listaPasajeros);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecycleViewAdapter(ListaPasajeros, HomeMototaxista.this);
        recyclerView.setAdapter(mAdapter);

        cargarDatosDesdeBD();
    }
    private void cargarDatosDesdeBD() {
        SharedPreferences prefsSesion = getSharedPreferences("sesion_actual", MODE_PRIVATE);
        int idUsuario = prefsSesion.getInt("id_usuario", -1);
        ListaPasajeros = baseDeDatos.obtenerTodosLosPasajeros(idUsuario);

        if (mAdapter == null) {
            mAdapter = new RecycleViewAdapter(ListaPasajeros, HomeMototaxista.this);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.actualizarLista(ListaPasajeros);
        }
    }

}