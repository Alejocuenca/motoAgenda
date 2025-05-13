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
    private static final int VERSION_BD = 3;

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
                COL_TIPO_USUARIO + " TEXT NOT NULL," +
                COL_ID_CREADOR + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COL_ID_CREADOR + ") REFERENCES " + TABLA_USUARIOS + "(" + COL_ID_USUARIO + "));";


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
                return;
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
        admin.put("id_creador", 99);
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
        mototaxista.put("id_creador", 1);
        db.insert("usuarios", null, mototaxista);

        ContentValues mototaxista2 = new ContentValues();
        mototaxista2.put("nombre", "Juanes");
        mototaxista2.put("apellido", "Lopez");
        mototaxista2.put("edad", 19);
        mototaxista2.put("correo", "juanes@ejemplo.com");
        mototaxista2.put("direccion", "Calle 10 #7-20");
        mototaxista2.put("usuario", "juanes");
        mototaxista2.put("contrasena", "juanes123");
        mototaxista2.put("tipo_usuario", "mototaxista");
        mototaxista2.put("id_creador", 1);
        db.insert("usuarios", null, mototaxista2);

        db.close();
    }

    public boolean insertarPasajero(String nombre, String apellido, int edad, String correo, String direccion,
                                    String institucion, String carrera, String anioGraduacion, String cursos,
                                    String habilidades, String musica, String genero, String deporte,
                                    String otrosIntereses, int idCreador) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("nombre", nombre);
        valores.put("apellido", apellido);
        valores.put("edad", edad);
        valores.put("correo", correo);
        valores.put("direccion", direccion);
        valores.put("institucion", institucion);
        valores.put("carrera", carrera);
        valores.put("anio_graduacion", anioGraduacion);
        valores.put("cursos", cursos);
        valores.put("habilidades", habilidades);
        valores.put("musica", musica);
        valores.put("genero", genero);
        valores.put("deporte", deporte);
        valores.put("otros_intereses", otrosIntereses);
        valores.put("id_creador", idCreador);

        long resultado = db.insert("pasajeros", null, valores);
        return resultado != -1;
    }

    public boolean insertarUsuario(String nombre, String apellido, int edad, String correo, String direccion,
                                    String usuario, String contrasena, String tipoUsuario, int idCreador) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("nombre", nombre);
        valores.put("apellido", apellido);
        valores.put("edad", edad);
        valores.put("correo", correo);
        valores.put("direccion", direccion);
        valores.put("usuario", usuario);
        valores.put("contrasena", contrasena);
        valores.put("tipo_usuario", tipoUsuario);
        valores.put("id_creador", idCreador);

        long resultado = db.insert("usuarios", null, valores);
        return resultado != -1;
    }

    public List<Pasajero> obtenerTodosLosPasajeros(int idCreador) {
        List<Pasajero> listaPasajeros = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_PASAJEROS + " WHERE id_creador = ?",
                new String[]{String.valueOf(idCreador)}, null);

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
                pasajero.setIdCreador(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CREADOR)));

                listaPasajeros.add(pasajero);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaPasajeros;
    }

    public List<Administrador> obtenerTodosLosUsuarios(int idCreador) {
        List<Administrador> listaUsuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_USUARIOS + " WHERE id_creador = ?",
                new String[]{String.valueOf(idCreador)}, null);

        if (cursor.moveToFirst()) {
            do {
                Administrador administrador = new Administrador();
                administrador.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_USUARIO)));
                administrador.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
                administrador.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(COL_APELLIDO)));
                administrador.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow(COL_EDAD)));
                administrador.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(COL_CORREO)));
                administrador.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(COL_DIRECCION)));
                administrador.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(COL_USUARIO)));
                administrador.setContrasena(cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTRASENA)));
                administrador.setTipoUsuario(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO_USUARIO)));
                administrador.setIdCreador(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CREADOR)));

                listaUsuarios.add(administrador);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaUsuarios;
    }

    public boolean actualizarPasajero(int id, ContentValues valores) {
        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.update("pasajeros", valores, "id_pasajero = ?", new String[]{String.valueOf(id)});
        db.close();
        return resultado > 0;
    }

    public boolean eliminarPasajero(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.delete("pasajeros", "id_pasajero = ?", new String[]{String.valueOf(id)});
        db.close();
        return resultado > 0;
    }


}

