/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.controller;

import java.net.URL;
import java.sql.Blob;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.model.CategoriaProductos;
import org.lisandroJimenez.model.Distribuidores;
import org.lisandroJimenez.model.Productos;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

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
    TableColumn colProductoId, colNombre, colDescripcion, colStock, colPrecio, colPrecioU, colPrecioM,colDistribuidor, colCategoria;
    @FXML
    TextField tfProductoId, tfNombre, tfStock, tfPrecio, tfPrecioU, tfPrecioM;
    @FXML
    TextArea taDescripcion;
    @FXML
    ComboBox cmbDistribuidor, cmbCategoria;
    @FXML
    Button btnBack, btnGuardar, btnVaciarForm;
    @FXML
    ImageView ivProducto;
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnBack) {
            stage.MenuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfNombre.getText().equals("")){      
                agregarProducto();
                cargarDatos();
                SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
            }else{     
                if (!tfNombre.getText().equals("") && !tfStock.getText().equals("") && !tfPrecio.getText().equals("") && !tfPrecioU.getText().equals("") && !tfPrecioM.getText().equals("")) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarProducto();
                        cargarDatos();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                }
            }
        }else if(event.getSource()== btnVaciarForm){
            vaciarCampos();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbDistribuidor.setItems(listarDistribuidor());
        cmbCategoria.setItems(listarCategoriasProducto());
        cargarDatos();
    }   
    public void cargarDatosEditar(){
        Productos p = (Productos)tblProductos.getSelectionModel().getSelectedItem();
        if(p != null){
            tfProductoId.setText(Integer.toString(p.getProductoId()));
            tfNombre.setText(p.getNombreProducto());
            tfStock.setText(Integer.toString(p.getCantidadStock()));
            tfPrecio.setText(Double.toString(p.getPrecioCompra()));
            tfPrecioU.setText(Double.toString(p.getPrecioVentaUnitario()));
            tfPrecioM.setText(Double.toString(p.getPrecioVentaMayor()));
            taDescripcion.setText(p.getDescripcionProducto());
            cmbDistribuidor.getSelectionModel().select(obtenerIndexDistribuidor());
            cmbCategoria.getSelectionModel().select(obtenerIndexCategoria());
        }
    }
    public int obtenerIndexDistribuidor(){
        int index = 0;
        for(int i = 0; i< cmbDistribuidor.getItems().size(); i++){
            String distribuidorCmb = cmbDistribuidor.getItems().get(i).toString();
            String distribuidorTbl = ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDistribuidor();
            if(distribuidorCmb.equals(distribuidorTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public int obtenerIndexCategoria(){
        int index = 0;
        for(int i = 0; i< cmbCategoria.getItems().size(); i++){
            String categoriaCmb = cmbCategoria.getItems().get(i).toString();
            String categoriaTbl = ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCategoriaProductos();
            if(categoriaCmb.equals(categoriaTbl)){
                index = i;
                break;
            }
        }
        return index;
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
    
    public void agregarProducto() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProducto(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, taDescripcion.getText());
            statement.setInt(3, Integer.parseInt(tfStock.getText()));
            statement.setDouble(4, Double.parseDouble(tfPrecio.getText()));
            statement.setDouble(5, Double.parseDouble(tfPrecioU.getText()));
            statement.setDouble(6, Double.parseDouble(tfPrecioM.getText()));
            statement.setNull(7, 0);
            statement.setInt(8, ((Distribuidores)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9, ((CategoriaProductos)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId()); 
            statement.execute();

        } catch (SQLException e) {
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
    
    public void editarProducto(){
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarProducto(?,?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, taDescripcion.getText());
            statement.setInt(4, Integer.parseInt(tfStock.getText()));
            statement.setDouble(5, Double.parseDouble(tfPrecio.getText()));
            statement.setDouble(6, Double.parseDouble(tfPrecioU.getText()));
            statement.setDouble(7, Double.parseDouble(tfPrecioM.getText()));
            statement.setNull(8, 0);
            statement.setInt(9, ((Distribuidores)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(10, ((CategoriaProductos)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId()); 
            statement.execute();

        } catch (SQLException e) {
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
    
        public void vaciarCampos(){
        tfProductoId.clear();
        tfNombre.clear();
        tfStock.clear();
        tfPrecio.clear();
        tfPrecioU.clear();
        tfPrecioM.clear();
        taDescripcion.clear();
        cmbDistribuidor.getSelectionModel().clearSelection();
        cmbCategoria.getSelectionModel().clearSelection();
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
    
    public ObservableList<Distribuidores> listarDistribuidor() {
        ArrayList<Distribuidores> distribuidores = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidor()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");

                distribuidores.add(new Distribuidores(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
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
        return FXCollections.observableList(distribuidores);
    }
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
