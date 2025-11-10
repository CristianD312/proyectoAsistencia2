package Logica;

public class Materia {
    private int idMateria;
    private String nombreMateria;
    private int cantidadModulos;
    private int grupo;
    private String orientacion;

    public Materia() {
    }

    public Materia(int idMateria, String nombreMateria, int cantidadModulos, int grupo, String orientacion) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.cantidadModulos = cantidadModulos;
        this.grupo = grupo;
        this.orientacion = orientacion;
    }

    public Materia(String nombreMateria, int cantidadModulos, int grupo, String orientacion) {
        this.nombreMateria = nombreMateria;
        this.cantidadModulos = cantidadModulos;
        this.grupo = grupo;
        this.orientacion = orientacion;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getCantidadModulos() {
        return cantidadModulos;
    }

    public void setCantidadModulos(int cantidadModulos) {
        this.cantidadModulos = cantidadModulos;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "idMateria=" + idMateria +
                ", nombreMateria='" + nombreMateria + '\'' +
                ", cantidadModulos=" + cantidadModulos +
                ", grupo=" + grupo +
                ", orientacion='" + orientacion + '\'' +
                '}';
    }

}
