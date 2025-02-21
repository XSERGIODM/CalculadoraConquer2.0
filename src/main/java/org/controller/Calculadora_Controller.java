package org.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.exception.Validacion_Exception;
import org.exception.Validador_Exception;
import org.model.Item_Model;
import org.util.Path_Util;

import java.util.Optional;

public class Calculadora_Controller {

    /* @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;*/

    @FXML
    private Button btnCalcular;

    @FXML
    private Button btnEiminarItemTabla;

    @FXML
    private Button btnLimpiarItem;

    @FXML
    private Button btnLimpiarTabla;

    @FXML
    private Button btnSumarTabla;

    @FXML
    private TableColumn<?, ?> colNivel;

    @FXML
    private TableColumn<?, ?> colPrecio;

    @FXML
    private TableColumn<?, ?> colProgreso;

    @FXML
    private TableColumn<?, ?> colSubTotal;

    @FXML
    private Label lbPrecioTotalItem;

    @FXML
    private Label lbPrecioTotalTabla;

    @FXML
    private TableView<Item_Model> tablaSumaItem;

    @FXML
    private TextField tfNivel;

    @FXML
    private TextField tfPrecio;

    @FXML
    private TextField tfProgreso;

    private ObservableList<Item_Model> listaItems;

    private Item_Model itemModel;

    @FXML
    void AccionEliminarItem() {
        Item_Model item = tablaSumaItem.getSelectionModel().getSelectedItem();
        if (item != null) {
            listaItems.remove(item);
            tablaSumaItem.setItems(listaItems);
            tablaSumaItem.refresh();
            lbPrecioTotalTabla.setText(String.valueOf(calcularPrecioTotalTabla()));
            if (listaItems.isEmpty()) {
                btnLimpiarTabla.setDisable(true);
            }
        }
        btnEiminarItemTabla.setDisable(true);
    }

    @FXML
    void accionAgregarItem() {
        if (itemModel != null) {
            listaItems.add(itemModel);
            tablaSumaItem.setItems(listaItems);
            lbPrecioTotalTabla.setText(String.valueOf(calcularPrecioTotalTabla()));
            btnLimpiarTabla.setDisable(false);
        }
    }

    @FXML
    void accionCalcular() {
        try {
            // Validar los campos antes de procesarlos

            Validador_Exception.verificarCampoLleno(tfNivel, "Nivel");
            Validador_Exception.verificarEntero(tfNivel, "Nivel");
            Validador_Exception.verificarNoNegativo(tfNivel, "Nivel");
            Validador_Exception.verificarNumeroMayorCero(tfNivel, "Nivel");

            Validador_Exception.verificarCampoLleno(tfProgreso, "Progreso");
            Validador_Exception.verificarEntero(tfProgreso, "Progreso");
            Validador_Exception.verificarNoNegativo(tfProgreso, "Progreso");
            Validador_Exception.verificarNumeroProgreso(tfProgreso, "Progreso");


            Validador_Exception.verificarCampoLleno(tfPrecio, "Precio");
            Validador_Exception.verificarNumero(tfPrecio, "Precio");
            Validador_Exception.verificarNoNegativo(tfPrecio, "Precio");
            Validador_Exception.verificarNumeroMayorCero(tfPrecio, "Precio");
            //--------------------------------------------

            calcular();

        } catch (Validacion_Exception ex) {
            Validador_Exception.mostrarAlerta(ex.getMessage());
        }
    }

    @FXML
    void accionLimpiarItem() {
        itemModel = null;
        tfNivel.clear();
        tfProgreso.clear();
        tfPrecio.clear();
        lbPrecioTotalItem.setText("0");
        btnLimpiarItem.setDisable(true);
        btnSumarTabla.setDisable(true);
        tfNivel.setStyle(null);
        tfProgreso.setStyle(null);
        tfPrecio.setStyle(null);
        tfNivel.requestFocus();
    }

    @FXML
    void accionLimpiarTabla() {
        listaItems.clear();
        tablaSumaItem.setItems(listaItems);
        lbPrecioTotalTabla.setText("0");
        btnLimpiarTabla.setDisable(true);
    }

    @FXML
    void accionSeccionarItem() {
        itemModel = tablaSumaItem.getSelectionModel().getSelectedItem();
        if (itemModel != null) {
            tfNivel.setText(String.valueOf(itemModel.getNivel()));
            tfProgreso.setText(String.valueOf(itemModel.getProgreso()));
            tfPrecio.setText(String.valueOf(itemModel.getPrecio()));
            lbPrecioTotalItem.setText(String.valueOf(itemModel.getPrecioTotal()));
            btnLimpiarItem.setDisable(true);
            btnSumarTabla.setDisable(true);
            btnEiminarItemTabla.setDisable(false);
        } else {
            btnEiminarItemTabla.setDisable(true);
        }
    }

    @FXML
    void accionLink() {

        // Crear la alerta de confirmación
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText("Se abrirá una página web");
        alerta.setContentText(
                "¿Deseas continuar?\n\n" +
                        "También puedes apoyarme con CP's en el juego. Aparezco como:\n" +
                        "- PequenioGigante\n" +
                        "- DamaG\n" +
                        "- MariaMuerte"
        );

        // Mostrar la alerta y esperar respuesta
        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Intentar abrir el enlace
            try {
                Validador_Exception.abrirEnlace(Path_Util.URL_DONACIONES_Y_REDES); // Reemplaza con tu URL
            } catch (Validacion_Exception e) {
                // Mostrar alerta de error
                Validador_Exception.mostrarAlerta(e.getMessage());
            }
        }
    }

    private void calcular() {
        itemModel = new Item_Model(
                Integer.parseInt(tfNivel.getText()),
                Integer.parseInt(tfProgreso.getText()),
                Double.parseDouble(tfPrecio.getText())
        );
        itemModel.calcularPrecioTotal();
        lbPrecioTotalItem.setText(String.valueOf(itemModel.getPrecioTotal()));

        //----------------------------------
        btnSumarTabla.setDisable(false);
        btnLimpiarItem.setDisable(false);
        tfNivel.setStyle("-fx-border-color: green;");
        tfProgreso.setStyle("-fx-border-color: green;");
        tfPrecio.setStyle("-fx-border-color: green;");
    }

    private double calcularPrecioTotalTabla() {
        double total = 0;
        for (Item_Model item : listaItems) {
            total += item.getPrecioTotal();
        }
        return total;
    }

    @FXML
    void initialize() {
        //-----------Inicializar los botones por defecto----------------
        assert btnCalcular != null : "fx:id=\"btnCalcular\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert btnEiminarItemTabla != null : "fx:id=\"btnEiminarItemTabla\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert btnLimpiarItem != null : "fx:id=\"btnLimpiarItem\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert btnLimpiarTabla != null : "fx:id=\"btnLimpiarTabla\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert btnSumarTabla != null : "fx:id=\"btnSumarTabla\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert colNivel != null : "fx:id=\"colNivel\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert colPrecio != null : "fx:id=\"colPrecio\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert colProgreso != null : "fx:id=\"colProgreso\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert colSubTotal != null : "fx:id=\"colSubTotal\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert lbPrecioTotalItem != null : "fx:id=\"lbPrecioTotalItem\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert lbPrecioTotalTabla != null : "fx:id=\"lbPrecioTotalTabla\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert tablaSumaItem != null : "fx:id=\"tablaSumaItem\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert tfNivel != null : "fx:id=\"tfNivel\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert tfPrecio != null : "fx:id=\"tfPrecio\" was not injected: check your FXML file 'Calculadora.fxml'.";
        assert tfProgreso != null : "fx:id=\"tfProgreso\" was not injected: check your FXML file 'Calculadora.fxml'.";

        //---------Inicializar los botones----------------
        this.btnSumarTabla.setDisable(true);
        this.btnLimpiarItem.setDisable(true);
        this.btnLimpiarTabla.setDisable(true);

        //---------Inicializar la tabla----------------
        this.listaItems = FXCollections.observableArrayList();
        this.colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        this.colProgreso.setCellValueFactory(new PropertyValueFactory<>("progreso"));
        this.colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        this.colSubTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));

    }

}
