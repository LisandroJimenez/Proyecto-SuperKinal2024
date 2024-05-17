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
import org.lisandroJimenez.dto.CategoriaProductoDTO;
import org.lisandroJimenez.model.CategoriaProductos;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuCategoriaProductosController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     * 
     */
    @FXML
    TableView tblCategoriaProductos;
    @FXML
    Button btnBack, btnAgregar, btnEliminar, btnEditar, btnBuscar;
    @FXML
    TableColumn colCategoriaId, colNombreCategoria, colDescripcionCategoria;
    @FXML
    TextField tfBuscar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();
    }    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnBack) {
            stage.MenuPrincipalView();

        } else if (event.getSource() == btnEliminar) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
                int catId = ((CategoriaProductos) tblCategoriaProductos.getSelectionModel().getSelectedItem()).getCategoriaProductosId();
                eliminarCategoriaProducto(catId);
                cargarLista();
            }
        }else if(event.getSource() == btnAgregar){
            stage.FormCategoriaProductosView(1);
        }else if(event.getSource() == btnEditar){
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto((CategoriaProductos)tblCategoriaProductos.getSelectionModel().getSelectedItem());
            stage.FormCategoriaProductosView(2);
        }else if(event.getSource() == btnBuscar){
            tblCategoriaProductos.getItems().clear();
            if (tfBuscar.getText().equals("")){
                cargarLista();
            }else{
                tblCategoriaProductos.getItems().add(buscarCategoriaProducto());
                colCategoriaId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer>("categoriaProductosId"));
                colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("nombreCategoria"));
                colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("descripcionCategoria"));
            }
        }
    }
    public void cargarLista() {
        tblCategoriaProductos.setItems(listarCategoriasProducto());
        colCategoriaId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer>("categoriaProductosId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("nombreCategoria"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("descripcionCategoria"));
    }
    
    public ObservableList<CategoriaProductos> listarCategoriasProducto() {
        ArrayList<CategoriaProductos> categoriaProductos = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int categoriaProductoId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria= resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                categoriaProductos.add(new CategoriaProductos(categoriaProductoId, nombreCategoria, descripcionCategoria));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        }
        return FXCollections.observableList(categoriaProductos);
    }
    
    
    public void eliminarCategoriaProducto(int catId) {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCategoriaProducto(?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, catId);
            statement.executeUpdate();
        } catch (SQLException e) {
            SuperKinalAlert.getInstance().mostrarAlertasInfo(404);
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public CategoriaProductos buscarCategoriaProducto(){
        CategoriaProductos categoriaProductos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCategoriaProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProductos = (new CategoriaProductos(categoriaProductosId, nombreCategoria, descripcionCategoria));
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
        
        return categoriaProductos;
    }
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
 