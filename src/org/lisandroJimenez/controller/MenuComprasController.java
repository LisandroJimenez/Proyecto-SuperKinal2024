/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.model.Compras;
import org.lisandroJimenez.model.DetalleCompras;
import org.lisandroJimenez.model.Productos;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuComprasController implements Initializable {

    @FXML
    ComboBox cmbProductos;
    @FXML
    Button btnGuardar, btnCancelar, btnVaciar;
    @FXML
    TextField tfTotal, tfCantidad, tfCompraId;
    @FXML
    DatePicker dpFecha;
    @FXML
    TableView tblCompras;
    @FXML
    TableColumn colCompraId, colProducto, colFecha, colCantidad, colTotal;
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbProductos.setItems(listarProducto());
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCancelar) {
            stage.MenuPrincipalView();
        } else if (event.getSource() == btnVaciar) {
            vaciarCampos();
        } else if (event.getSource() == btnGuardar) {
            if (tfCompraId.getText().equals("")) {
                agregarCompra();
                cargarDatos();
                SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
            }else{     
                if (dpFecha.getValue() != null) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarCompra();
                        cargarDatos();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                }
            }
        }
    }

    public void cargarDatos() {
        tblCompras.setItems(listarCompra());
        colCompraId.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Integer>("compraId"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompras, String>("Producto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Date>("fechaCompra"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Integer>("cantidadCompra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Double>("totalCompra"));
        tblCompras.getSortOrder().add(colCompraId);
    }

    public void cargarDatosEditar() {
        DetalleCompras DC = (DetalleCompras) tblCompras.getSelectionModel().getSelectedItem();
        if (DC != null) {
            tfCompraId.setText(Integer.toString(DC.getCompraId()));
            tfTotal.setText(Double.toString(DC.getTotalCompra()));
            tfCantidad.setText(Integer.toString(DC.getCantidadCompra()));
            cmbProductos.getSelectionModel().select(obtenerIndexProducto());
            dpFecha.setValue(DC.getFechaCompra().toLocalDate());
        }
    }

    public void agregarDetalleCompra() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleCompra(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCantidad.getText()));
            statement.setInt(2, ((Productos) cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(3, Integer.parseInt(tfCompraId.getText()));
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

    public void agregarCompra() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCompra(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ((Productos) cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(2, Integer.parseInt(tfCantidad.getText()));
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
    
    public void editarCompra(){
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setDate(2,Date.valueOf(dpFecha.getValue()));
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<DetalleCompras> listarCompra() {
        ArrayList<DetalleCompras> compra = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDetalleCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int cantidadCompra = resultSet.getInt("cantidadCompra");
                int compraId = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                Double total = resultSet.getDouble("totalCompra");
                String producto = resultSet.getString("nombreProducto");

                compra.add(new DetalleCompras(cantidadCompra, compraId, fecha, total, producto));
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
        return FXCollections.observableList(compra);
    }

    public int obtenerIndexProducto() {
        int index = 0;
        DetalleCompras selectedDetalleCompra = (DetalleCompras) tblCompras.getSelectionModel().getSelectedItem();
        if (selectedDetalleCompra != null) {
            ObservableList<Productos> productos = cmbProductos.getItems();
            for (int i = 0; i < productos.size(); i++) {
                Productos producto = productos.get(i);
                if (producto.getNombreProducto().equals(selectedDetalleCompra.getProducto())) {
                    index = i;
                    break;
                }
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

    public void vaciarCampos() {
        tfCompraId.clear();
        tfTotal.clear();
        tfCantidad.clear();
        cmbProductos.getSelectionModel().clearSelection();
        dpFecha.getEditor().clear();
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
