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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.model.Cliente;
import org.lisandroJimenez.system.Main;

/**
 *
 * @author Usuario
 */
public class EliminarClientesController implements Initializable{
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TextField textId;
    @FXML
    Button btnBack, btnEliminar;
    @FXML
    TableView tblOpcion;
    @FXML
    TableColumn colClienteId, colNombre;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLista();
    }  
    
    public void eliminarCliente(int cliId){
        try{
        conexion = Conexion.getInstance().obtenerConexion();
        String sql = "call sp_eliminarCliente(?)";
        PreparedStatement statement = conexion.prepareStatement(sql);
        statement.setInt(1, cliId);
        statement.executeUpdate();
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }finally{
        try{
            if(statement != null){
                statement.close();
            }
            if(conexion != null){
                conexion.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    }
    
    
    public void cargarLista(){
        tblOpcion.setItems(Opciones());
        colClienteId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("clienteId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String> ("nombre")); 
    }
    
    public ObservableList<Cliente>Opciones(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_elegirCliente()";
            statement = conexion.prepareStatement(sql); 
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");  
                clientes.add(new Cliente(clienteId, nombre));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                resultSet.close();
            }
            if(conexion != null){
                resultSet.close();
            }
            }catch(SQLException e){
                System.out.println(e.getMessage());

            }
        }
        return FXCollections.observableList(clientes);
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnBack){
            stage.MenuClientesView();
        }else if(event.getSource() == btnEliminar){
        }
    }
}
