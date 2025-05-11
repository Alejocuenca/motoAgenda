package com.example.motoagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "mi_app.db";
    private static final int VERSION_BD = 1;

    public static final String TABLA_USUARIOS = "usuarios";
    public static final String COL_ID_USUARIO = "id_usuario";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_APELLIDO = "apellido";
    public static final String COL_EDAD = "edad";
    public static final String COL_CORREO = "correo";
    public static final String COL_DIRECCION = "direccion";
    public static final String COL_USUARIO = "usuario";
    public static final String COL_CONTRASENA = "contrasena";
    public static final String COL_TIPO_USUARIO = "tipo_usuario";

    public static final String TABLA_PASAJEROS = "pasajeros";
    public static final String COL_ID_PASAJERO = "id_pasajero";
    public static final String COL_INSTITUCION = "institucion";
    public static final String COL_CARRERA = "carrera";
    public static final String COL_ANIO_GRADUACION = "anio_graduacion";
    public static final String COL_CURSOS = "cursos";
    public static final String COL_HABILIDADES = "habilidades";
    public static final String COL_MUSICA = "musica";
    public static final String COL_GENERO = "genero";
    public static final String COL_DEPORTE = "deporte";
    public static final String COL_OTROS_INTERESES = "otros_intereses";
    public static final String COL_ID_CREADOR = "id_creador";

    public BaseDeDatos(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String crearTablaUsuarios = "CREATE TABLE " + TABLA_USUARIOS + " (" +
                COL_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOMBRE + " TEXT NOT NULL, " +
                COL_APELLIDO + " TEXT NOT NULL, " +
                COL_EDAD + " INTEGER NOT NULL, " +
                COL_CORREO + " TEXT NOT NULL, " +
                COL_DIRECCION + " TEXT NOT NULL, " +
                COL_USUARIO + " TEXT NOT NULL, " +
                COL_CONTRASENA + " TEXT NOT NULL, " +
                COL_TIPO_USUARIO + " TEXT NOT NULL);";


        String crearTablaPasajeros = "CREATE TABLE " + TABLA_PASAJEROS + " (" +
                COL_ID_PASAJERO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOMBRE + " TEXT NOT NULL, " +
                COL_APELLIDO + " TEXT NOT NULL, " +
                COL_EDAD + " INTEGER NOT NULL, " +
                COL_CORREO + " TEXT NOT NULL, " +
                COL_DIRECCION + " TEXT NOT NULL, " +
                COL_INSTITUCION + " TEXT, " +
                COL_CARRERA + " TEXT, " +
                COL_ANIO_GRADUACION + " TEXT, " +
                COL_CURSOS + " TEXT, " +
                COL_HABILIDADES + " TEXT, " +
                COL_MUSICA + " TEXT, " +
                COL_GENERO + " TEXT, " +
                COL_DEPORTE + " TEXT, " +
                COL_OTROS_INTERESES + " TEXT, " +
                COL_USUARIO + " TEXT NOT NULL, " +
                COL_CONTRASENA + " TEXT NOT NULL, " +
                COL_ID_CREADOR + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COL_ID_CREADOR + ") REFERENCES " + TABLA_USUARIOS + "(" + COL_ID_USUARIO + "));";

        db.execSQL(crearTablaUsuarios);
        db.execSQL(crearTablaPasajeros);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PASAJEROS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        onCreate(db);
    }

    public void insertarUsuariosDePrueba() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM usuarios", null);
        if (cursor.moveToFirst()) {
            int cantidad = cursor.getInt(0);
            if (cantidad > 0) {
                cursor.close();
                db.close();
                return; // Ya hay usuarios, no insertamos
            }
        }
        cursor.close();


        ContentValues admin = new ContentValues();
        admin.put("nombre", "Carlos");
        admin.put("apellido", "Ramírez");
        admin.put("edad", 35);
        admin.put("correo", "carlos@ejemplo.com");
        admin.put("direccion", "Carrera 45 #12-34");
        admin.put("usuario", "admin");
        admin.put("contrasena", "admin123");
        admin.put("tipo_usuario", "administrador");
        db.insert("usuarios", null, admin);


        ContentValues mototaxista = new ContentValues();
        mototaxista.put("nombre", "Laura");
        mototaxista.put("apellido", "Torres");
        mototaxista.put("edad", 29);
        mototaxista.put("correo", "laura@ejemplo.com");
        mototaxista.put("direccion", "Calle 9 #8-20");
        mototaxista.put("usuario", "mototaxi");
        mototaxista.put("contrasena", "moto123");
        mototaxista.put("tipo_usuario", "mototaxista");
        db.insert("usuarios", null, mototaxista);

        db.close();
    }

    public void insertarPasajerosDePrueba() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM pasajeros", null);
        if (cursor.moveToFirst()) {
            int cantidad = cursor.getInt(0);
            if (cantidad > 0) {
                cursor.close();
                db.close();
                return; // Ya hay pasajeros, no insertamos
            }
        }
        cursor.close();

        for (int i = 1; i <= 10; i++) {
            ContentValues pasajero = new ContentValues();
            pasajero.put("nombre", "Pasajero" + i);
            pasajero.put("apellido", "Apellido" + i);
            pasajero.put("edad", 20 + i);
            pasajero.put("correo", "pasajero" + i + "@ejemplo.com");
            pasajero.put("direccion", "Calle " + i + " #45-67");
            pasajero.put("institucion", "Institución " + i);
            pasajero.put("carrera", "Carrera " + i);
            pasajero.put("anio_graduacion", "202" + (i % 10));
            pasajero.put("cursos", "Curso A, Curso B");
            pasajero.put("habilidades", "Habilidad X, Habilidad Y");
            pasajero.put("musica", "Rock, Pop");
            pasajero.put("genero", (i % 2 == 0) ? "Masculino" : "Femenino");
            pasajero.put("deporte", "Fútbol, Natación");
            pasajero.put("otros_intereses", "Cine, Comida");
            pasajero.put("usuario", "user" + i);
            pasajero.put("contrasena", "pass" + i);
            pasajero.put("id_creador", 2); // Asegúrate de que el mototaxista tenga ID = 2

            db.insert("pasajeros", null, pasajero);
        }

        db.close();
    }


    public List<Pasajero> obtenerTodosLosPasajeros() {
        List<Pasajero> listaPasajeros = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_PASAJEROS, null);

        if (cursor.moveToFirst()) {
            do {
                Pasajero pasajero = new Pasajero();
                pasajero.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_PASAJERO)));
                pasajero.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
                pasajero.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(COL_APELLIDO)));
                pasajero.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow(COL_EDAD)));
                pasajero.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(COL_CORREO)));
                pasajero.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(COL_DIRECCION)));
                pasajero.setInstitucion(cursor.getString(cursor.getColumnIndexOrThrow(COL_INSTITUCION)));
                pasajero.setCarrera(cursor.getString(cursor.getColumnIndexOrThrow(COL_CARRERA)));
                pasajero.setAnioGraduacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_ANIO_GRADUACION)));
                pasajero.setCursos(cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSOS)));
                pasajero.setHabilidades(cursor.getString(cursor.getColumnIndexOrThrow(COL_HABILIDADES)));
                pasajero.setMusica(cursor.getString(cursor.getColumnIndexOrThrow(COL_MUSICA)));
                pasajero.setGenero(cursor.getString(cursor.getColumnIndexOrThrow(COL_GENERO)));
                pasajero.setDeporte(cursor.getString(cursor.getColumnIndexOrThrow(COL_DEPORTE)));
                pasajero.setOtrosIntereses(cursor.getString(cursor.getColumnIndexOrThrow(COL_OTROS_INTERESES)));
                pasajero.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(COL_USUARIO)));
                pasajero.setContrasena(cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTRASENA)));
                pasajero.setIdCreador(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CREADOR)));

                listaPasajeros.add(pasajero);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaPasajeros;
    }

}

