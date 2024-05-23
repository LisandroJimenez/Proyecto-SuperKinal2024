/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.lisandroJimenez.system.Main;

/**
 *
 * @author Usuario
 */
public class MenuPrincipalController implements Initializable {
    private Main stage;
    @FXML
    MenuItem btnMenuClientes, btnTicketSoporte, btnCargos, btnCategoriaProductos, btnDistribuidores, btnPromociones, btnProductos, btnCompras, btnEmpleados;

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnMenuClientes){
            stage.MenuClientesView();
        }else if(event.getSource() == btnTicketSoporte){
            stage.MenuTicketSoporteView();
        }else if(event.getSource() == btnCargos){
            stage.MenuCargosView();
        }else if(event.getSource() == btnCategoriaProductos){
            stage.MenuCategoriaProductosView();
        }else if(event.getSource() == btnDistribuidores){
            stage.MenuDistribuidoresView();
        }else if(event.getSource() == btnPromociones){
            stage.MenuPromocionesView();
        }else if(event.getSource() == btnProductos){
            stage.MenuProductosView();
        }else if(event.getSource() == btnCompras){
            stage.MenuComprasView();
        }else if(event.getSource() == btnEmpleados){
            stage.MenuEmpleadosView(2);
        }
    }
    
}
