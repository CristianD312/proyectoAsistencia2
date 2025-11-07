module com.cristiand.proyectoasistencia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.cristiand.proyectoasistencia to javafx.fxml;
    exports com.cristiand.proyectoasistencia;
}