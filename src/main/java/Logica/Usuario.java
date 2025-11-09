package Logica;

public class Usuario {
    private int id;
    private String nombreUsuario;
    private String password;
    private boolean admin;

    public Usuario() {
    }

    public Usuario(int id, String nombreUsuario, String password, boolean admin) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.admin = admin;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {return nombreUsuario;}

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    @Override
    public String toString() {
        return nombreUsuario; // Muestra solo el nombre de usuario
    }

}
