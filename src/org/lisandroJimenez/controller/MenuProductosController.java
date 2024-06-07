/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.dto.ProductoDTO;
import org.lisandroJimenez.model.CategoriaProductos;
import org.lisandroJimenez.model.Distribuidores;
import org.lisandroJimenez.model.Productos;
import org.lisandroJimenez.report.GenerarReporte;
import org.lisandroJimenez.system.Main;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuProductosController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TableView tblProductos;
    @FXML
    TableColumn colProductoId, colNombre, colDescripcion, colStock, colPrecio, colPrecioU, colPrecioM, colDistribuidor, colCategoria;
    @FXML
    Button btnVerProductos, btnBack, btnAgregar, btnEditar;
    @FXML
    ImageView imgMostrar;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnBack) {
            stage.MenuPrincipalView();
        } else if (event.getSource() == btnAgregar) {
            stage.FormProductosView(1);
        } else if (event.getSource() == btnEditar) {
            ProductoDTO.getProductoDTO().setProducto((Productos) tblProductos.getSelectionModel().getSelectedItem());
            stage.FormProductosView(2);
        }else if (event.getSource() == btnVerProductos){
            GenerarReporte.getInstance().generarProducto();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public ObservableList<Productos> listarProducto() {
        ArrayList<Productos> productos = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int productoId = resultSet.getInt("productoId");
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcionProducto");
                int cantidad = resultSet.getInt("cantidadStock");
                Double precioU = resultSet.getDouble("precioVentaUnitario");
                Double precioM = resultSet.getDouble("precioVentaMayor");
                Double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagen = resultSet.getBlob("imagenProducto");
                String distribuidorId = resultSet.getString("distribuidor");
                String categoriaProductoId = resultSet.getString("categoriaProducto");

                productos.add(new Productos(productoId, nombre, descripcion, cantidad, precioU, precioM, precioCompra, imagen, distribuidorId, categoriaProductoId));
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
        return FXCollections.observableList(productos);
    }

    public void mostrarImagen() {
        Productos p = (Productos) tblProductos.getSelectionModel().getSelectedItem();
        if (p != null) {
            Blob img = p.getImagenProducto();
            if (img != null) {
                try {
                    InputStream inputStream = img.getBinaryStream();
                    Image image = new Image(inputStream);
                    imgMostrar.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imgMostrar.setImage(null);
            }
        }else{
            imgMostrar.setImage(null);
        }
    }

    public void cargarDatos() {
        tblProductos.setItems(listarProducto());
        colProductoId.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("productoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Productos, String>("nombreProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("DescripcionProducto"));
        colStock.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidadStock"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioCompra"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaUnitario"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaMayor"));
        colDistribuidor.setCellValueFactory(new PropertyValueFactory<Productos, String>("distribuidor"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Productos, String>("categoriaProductos"));
        tblProductos.getSortOrder().add(colProductoId);

    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
