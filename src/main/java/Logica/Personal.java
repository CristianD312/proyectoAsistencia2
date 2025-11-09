package Logica;

public class Personal {
    private int id_personal;
    private String nombre;
    private String apellido;
    private int CUIL;
    private String cargo;

    public Personal() {
    }

    public Personal(int id_profe, String nombre, String apellido, int CUIL, String cargo) {
        this.id_personal = id_profe;
        this.nombre = nombre;
        this.apellido = apellido;
        this.CUIL = CUIL;
        this.cargo = cargo;
    }

    public Personal(String nombre, String apellido, int CUIL, String cargo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.CUIL = CUIL;
        this.cargo = cargo;
    }

    public int getId_personal() {
        return id_personal;
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

    public int getCUIL() {
        return CUIL;
    }

    public void setCUIL(int CUIL) {
        this.CUIL = CUIL;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
