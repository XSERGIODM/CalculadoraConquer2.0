package org.exception;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.model.Item_Model;

import java.awt.*;
import java.net.URI;
import java.util.Objects;

public class Validador_Exception {

    // Verificar que el campo no esté vacío
    public static void verificarCampoLleno(TextField campo, String nombreCampo) throws Validacion_Exception {
        if (campo == null || campo.getText().trim().isEmpty()) {
            colocarEstiloFocus(campo);
            throw new Validacion_Exception("El campo '" + nombreCampo + "' no puede estar vacío.");
        }
    }

    // Verificar que sea un número
    public static void verificarNumero(TextField campo, String nombreCampo) throws Validacion_Exception {
        try {
            Double.parseDouble(campo.getText());
        } catch (NumberFormatException e) {
            colocarEstiloFocus(campo);
            throw new Validacion_Exception("El campo '" + nombreCampo + "' debe ser un número.");
        }
    }

    // Verificar que sea un número entero
    public static void verificarEntero(TextField campo, String nombreCampo) throws Validacion_Exception {
        try {
            Integer.parseInt(campo.getText());
        } catch (NumberFormatException e) {
            colocarEstiloFocus(campo);
            throw new Validacion_Exception("El campo '" + nombreCampo + "' debe ser un número entero.");
        }
    }

    // Verificar que no sea negativo (entero o decimal)
    public static void verificarNoNegativo(TextField campo, String nombreCampo) throws Validacion_Exception {
        try {
            double numero = Double.parseDouble(campo.getText());
            if (numero < 0) {
                colocarEstiloFocus(campo);
                throw new Validacion_Exception("El campo '" + nombreCampo + "' no puede ser un número negativo.");
            }
        } catch (NumberFormatException e) {
            colocarEstiloFocus(campo);
            throw new Validacion_Exception("El campo '" + nombreCampo + "' debe ser un número válido.");
        }
    }

    public static void verificarNumeroProgreso(TextField campo, String nombreCampo) throws Validacion_Exception {
        // verificamos que sea modulo de 10
        try {
            int numero = Integer.parseInt(campo.getText());
            if (numero % 10 != 0) {
                colocarEstiloFocus(campo);
                throw new Validacion_Exception("El campo '" + nombreCampo + "' debe ser un número válido.");
            }
        } catch (NumberFormatException e) {
            colocarEstiloFocus(campo);
            throw new Validacion_Exception("El campo '" + nombreCampo + "' debe ser un número válido.");
        }
    }

    public static void verificarNumeroMayorCero(TextField campo, String nombreCampo) throws Validacion_Exception {
        // verificamos que sea mayor a 0
        try {
            double numero = Double.parseDouble(campo.getText());
            if (numero <= 0) {
                colocarEstiloFocus(campo);
                throw new Validacion_Exception("El campo '" + nombreCampo + "' debe ser un número mayor a 0.");
            }
        } catch (NumberFormatException e) {
            colocarEstiloFocus(campo);
            throw new Validacion_Exception("El campo '" + nombreCampo + "' debe ser un número válido.");
        }
    }

    public static void verificarSeleccion(TableView<Item_Model> tabla, Item_Model item, String nombreCampo) throws Validacion_Exception {
        if (Objects.isNull(item)) {
            tabla.requestFocus();
            tabla.setStyle("-fx-border-color: red;");
            throw new Validacion_Exception("Debe seleccionar un '" + nombreCampo + "'.");
        }
    }

    public static void abrirEnlace(String url) throws Validacion_Exception {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                throw new Validacion_Exception("No se puede abrir el navegador en este sistema.");
            }
        } catch (Exception e) {
            throw new Validacion_Exception("Error al intentar abrir el enlace: " + e.getMessage());
        }
    }

    // Mostrar alerta de JavaFX
    public static void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error de Validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private static void colocarEstiloFocus(TextField campo) {
        campo.requestFocus();
        campo.setStyle("-fx-border-color: red;");
    }
}
