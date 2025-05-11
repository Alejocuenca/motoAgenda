package com.example.motoagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "mi_app.db";
    private static final int VERSION_BD = 1;

    // Tabla usuarios
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

    // Tabla pasajeros
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

}

