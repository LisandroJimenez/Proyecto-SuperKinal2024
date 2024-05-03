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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.dto.DistribuidorDTO;

import org.lisandroJimenez.model.Cliente;
import org.lisandroJimenez.model.Distribuidores;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuDistribuidoresController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    /**
     * Initializes the controller class.
     */
    @FXML
    TableView tblDistribuidores;
    @FXML
    Button btnBack, btnAgregar, btnEliminar, btnEditar, btnBuscar;
    @FXML
    TableColumn colDistribuidorId, colNombreDistribuidor, colDireccionDistribuidor, colNitDistribuidor, colTelefonoDistribuidor, colWeb;
    @FXML
    TextField tfBuscar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnBack) {
            stage.MenuPrincipalView();

        } else if (event.getSource() == btnEliminar) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
                int disId = ((Distribuidores) tblDistribuidores.getSelectionModel().getSelectedItem()).getDistribuidorId();
                eliminarDistribuidor(disId);
                cargarLista();
            }
        }else if(event.getSource() == btnAgregar){
            stage.FormDistribuidoresView(1);
        }else if(event.getSource() == btnEditar){
            DistribuidorDTO.getDistribuidorDTO().setDistribuidor((Distribuidores)tblDistribuidores.getSelectionModel().getSelectedItem());
            stage.FormDistribuidoresView(2);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         cargarLista();
    }    
    
    public void cargarLista(){
        tblDistribuidores.setItems(listarDistribuidor());
        colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("distribuidorId"));
        colNombreDistribuidor.setCellValueFactory(new PropertyValueFactory<Cliente, String> ("nombreDistribuidor")); 
        colDireccionDistribuidor.setCellValueFactory(new PropertyValueFactory<Cliente, String> ("direccionDistribuidor")); 
        colNitDistribuidor.setCellValueFactory(new PropertyValueFactory<Cliente, String> ("nitDistribuidor")); 
        colTelefonoDistribuidor.setCellValueFactory(new PropertyValueFactory<Cliente, String> ("telefonoDistribuidor")); 
        colWeb.setCellValueFactory(new PropertyValueFactory<Cliente, String> ("web"));
    }
    
    public ObservableList<Distribuidores>listarDistribuidor(){
        ArrayList<Distribuidores> distribuidores = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidor()";
            statement = conexion.prepareStatement(sql); 
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web"); 
                
                distribuidores.add(new Distribuidores(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
            }
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
        return FXCollections.observableList(distribuidores);
    }
    
    public void eliminarDistribuidor (int DisId){
        try{
        conexion = Conexion.getInstance().obtenerConexion();
        String sql = "call sp_eliminarDistribuidor(?)";
        PreparedStatement statement = conexion.prepareStatement(sql);
        statement.setInt(1, DisId);
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
    
     public Distribuidores buscarDistribuidor(){
        Distribuidores distribuidores = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarDistribuidor(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web"); 
                
                distribuidores = (new Distribuidores(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
            }
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
        
        return distribuidores;
    }
    
    
    
    
    
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    
    
    
    
}
