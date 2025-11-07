package com.cristiand.proyectoasistencia;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class GestorVentanas {

    public void crearVentana(String ventanaFmxl,String nombreVentana){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ventanaFmxl));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage(); // Nueva ventana
            stage.setTitle(nombreVentana);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Método genérico
    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Métodos específicos
    public void mostrarError(String mensaje) {
        mostrarMensaje(Alert.AlertType.ERROR, "Error", mensaje);
    }

    public void mostrarExito(String mensaje) {
        mostrarMensaje(Alert.AlertType.INFORMATION, "Éxito", mensaje);
    }

    public void mostrarAdvertencia(String mensaje) {
        mostrarMensaje(Alert.AlertType.WARNING, "Advertencia", mensaje);
    }

    public void mostrarInfo(String mensaje) {
        mostrarMensaje(Alert.AlertType.INFORMATION, "Información", mensaje);
    }
}

