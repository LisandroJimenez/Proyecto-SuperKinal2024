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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.dto.ClienteDTO;
import org.lisandroJimenez.model.Cliente;
import org.lisandroJimenez.system.Main;

/**
 *
 * @author informatica
 */
public class FormClientesController implements Initializable{
    private Main stage;
    private int op;
    @FXML
    Button btnGuardar, btnCancelar;
    @FXML
    TextField textId, textNombre, textApellido, textTelefono, textDireccion, textNit;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(ClienteDTO.getClienteDTO().getCliente() != null){
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }
    }
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnCancelar){
            ClienteDTO.getClienteDTO().setCliente(null);
            stage.MenuClientesView();
        }else if (event.getSource() == btnGuardar){
            if (op == 1){
                AgregarClientes();
                stage.MenuClientesView();
            }else if(op == 2){
                EditarClientes();
                ClienteDTO.getClienteDTO().setCliente(null);
                stage.MenuClientesView();
            }
        }
    }
    
    public void AgregarClientes(){
     
    try{
        conexion = Conexion.getInstance().obtenerConexion();
        String sql = "call sp_agregarCliente(?, ?, ?, ?, ?)";
        PreparedStatement statement = conexion.prepareStatement(sql);
        statement.setString(1, textNombre.getText());
        statement.setString(2, textApellido.getText());
        statement.setString(3, textTelefono.getText());
        statement.setString(4, textDireccion.getText());
        statement.setString(5, textNit.getText()); 
        statement.executeUpdate();
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }finally{
        try{
            if(resultSet != null){
                resultSet.close();
            }
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
    
    
    public void EditarClientes(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCliente(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql); 
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(textId.getText()));
            statement.setString(2, textNombre.getText());
            statement.setString(3, textApellido.getText());
            statement.setString(4, textTelefono.getText());
            statement.setString(5, textDireccion.getText());
            statement.setString(6, textNit.getText()); 
            statement.execute();
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
    
    public void cargarDatos(Cliente cliente){
        textId.setText(Integer.toString(cliente.getClienteId()));
        textNombre.setText(cliente.getNombre());
        textApellido.setText(cliente.getApellido());
        textTelefono.setText(cliente.getTelefono());
        textDireccion.setText(cliente.getDireccion());
        textNit.setText(cliente.getNit());
    }
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
