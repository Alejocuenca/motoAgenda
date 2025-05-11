package com.example.motoagenda;

public class Pasajero {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    private String direccion;
    private String institucion;
    private String carrera;
    private String anioGraduacion;
    private String cursos;
    private String habilidades;
    private String musica;
    private String genero;
    private String deporte;
    private String otrosIntereses;
    private String usuario;
    private String contrasena;
    private int idCreador;

    // Constructor vacío
    public Pasajero() {}

    // Constructor completo
    public Pasajero(int id, String nombre, String apellido, int edad, String correo, String direccion,
                    String institucion, String carrera, String anioGraduacion, String cursos,
                    String habilidades, String musica, String genero, String deporte,
                    String otrosIntereses, String usuario, String contrasena, int idCreador) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.direccion = direccion;
        this.institucion = institucion;
        this.carrera = carrera;
        this.anioGraduacion = anioGraduacion;
        this.cursos = cursos;
        this.habilidades = habilidades;
        this.musica = musica;
        this.genero = genero;
        this.deporte = deporte;
        this.otrosIntereses = otrosIntereses;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.idCreador = idCreador;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getInstitucion() { return institucion; }
    public void setInstitucion(String institucion) { this.institucion = institucion; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getAnioGraduacion() { return anioGraduacion; }
    public void setAnioGraduacion(String anioGraduacion) { this.anioGraduacion = anioGraduacion; }

    public String getCursos() { return cursos; }
    public void setCursos(String cursos) { this.cursos = cursos; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public String getMusica() { return musica; }
    public void setMusica(String musica) { this.musica = musica; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDeporte() { return deporte; }
    public void setDeporte(String deporte) { this.deporte = deporte; }

    public String getOtrosIntereses() { return otrosIntereses; }
    public void setOtrosIntereses(String otrosIntereses) { this.otrosIntereses = otrosIntereses; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public int getIdCreador() { return idCreador; }
    public void setIdCreador(int idCreador) { this.idCreador = idCreador; }
}

