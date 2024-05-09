/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.lisandroJimenez.model.DetalleCompras;
import org.lisandroJimenez.system.Main;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuComprasController implements Initializable {
    @FXML
    Button btnGuardar, btnCancelar;
    @FXML
    TextField tfTotal, tfCantidad;
    @FXML
    DatePicker fpFecha;
    @FXML
    TableView tblCompras;
    @FXML
    TableColumn colCompraId, colProducto, colFecha, colCantidad, colTotal;
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.MenuPrincipalView();
        }
    }
    
    public void cargarDatosEditar(){
        DetalleCompras ts = (DetalleCompras)tblCompras.getSelectionModel().getSelectedItem();
        if(ts != null){
            
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
