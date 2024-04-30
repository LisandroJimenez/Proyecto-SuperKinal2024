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
import org.lisandroJimenez.model.CategoriaProductos;
import org.lisandroJimenez.system.Main;

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
        // TODO
    }    
    @FXML
    public void handleButtonAction(ActionEvent event){
        
    }
    public void cargarLista() {
        tblCategoriaProductos.setItems(listarCategoriasProducto());
        colCategoriaId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer>("categoriaProductoId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("nombreCategoriaProducto"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("descripcionCategoriaProducto"));
    }
    
    public ObservableList<CategoriaProductos> listarCategoriasProducto() {
        ArrayList<CategoriaProductos> categoriaProductos = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int categoriaProductoId = resultSet.getInt("categoriaProductoId");
                String nombreCategoria= resultSet.getString("nombreCategoriaProducto");
                String descripcionCategoria = resultSet.getString("descripcionCategoriaProdcuto");
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
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
 