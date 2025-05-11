package com.example.motoagenda;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeMototaxista extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager ;
    private List<Pasajero> ListaPasajeros;
    private BaseDeDatos baseDeDatos;


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
        ListaPasajeros = baseDeDatos.obtenerTodosLosPasajeros();

        if (mAdapter == null) {
            mAdapter = new RecycleViewAdapter(ListaPasajeros, HomeMototaxista.this);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.actualizarLista(ListaPasajeros);
        }
    }

}