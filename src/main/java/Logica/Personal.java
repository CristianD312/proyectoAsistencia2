package Logica;

public class Personal {
    private int id_profe;
    private String nombre;
    private String apellido;
    private int dni;
    private String cargo;

    public Personal() {
    }

    public Personal(int id_profe, String nombre, String apellido, int dni, String cargo) {
        this.id_profe = id_profe;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.cargo = cargo;
    }

    public Personal(String nombre, String apellido, int dni, String cargo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.cargo = cargo;
    }

    public int getId_profe() {
        return id_profe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
