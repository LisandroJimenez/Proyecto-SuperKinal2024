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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.dto.ProductoDTO;
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
public class FormProductosController implements Initializable {

    private Main stage;
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private List<File> files = null;
    @FXML
    Button btnGuardar, btnCancelar;
    @FXML
    TextField tfProductoId, tfNombre, tfStock, tfPrecio, tfPrecioU, tfPrecioM;
    @FXML
    TextArea taDescripcion;
    @FXML
    ComboBox cmbDistribuidor, cmbCategoria;
    @FXML
    ImageView imgCargar;
    private File imageFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbDistribuidor.setItems(listarDistribuidor());
        cmbCategoria.setItems(listarCategoriasProducto());
        if (ProductoDTO.getProductoDTO().getProducto() != null) {
            cargarDatos(ProductoDTO.getProductoDTO().getProducto());
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCancelar) {
            ProductoDTO.getProductoDTO().setProducto(null);
            stage.MenuProductosView();
        } else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                if (!tfNombre.getText().equals("") && !tfStock.getText().equals("") && !tfPrecio.getText().equals("") && !tfPrecioU.getText().equals("") && !tfPrecioM.getText().equals("")) {
                    agregarProducto();
                    stage.MenuProductosView();
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                    tfNombre.requestFocus();
                    return;
                }
            } else if (op == 2) {
                if (!tfNombre.getText().equals("") && !tfStock.getText().equals("") && !tfPrecio.getText().equals("") && !tfPrecioU.getText().equals("") && !tfPrecioM.getText().equals("")) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarProducto();
                        stage.MenuProductosView();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                }
            }
        }
    }

    @FXML
    public void handleOnDrag(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void handleOnDrop(DragEvent event) {
        try {
            files = event.getDragboard().getFiles();
            imageFile = files.get(0);
            FileInputStream file = new FileInputStream(imageFile);

            Image image = new Image(file);
            imgCargar.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos(Productos producto) {
        tfProductoId.setText(Integer.toString(producto.getProductoId()));
        tfNombre.setText(producto.getNombreProducto());
        tfStock.setText(Integer.toString(producto.getCantidadStock()));
        tfPrecio.setText(Double.toString(producto.getPrecioCompra()));
        tfPrecioU.setText(Double.toString(producto.getPrecioVentaUnitario()));
        tfPrecioM.setText(Double.toString(producto.getPrecioVentaMayor()));
        taDescripcion.setText(producto.getDescripcionProducto());
        imgCargar.setImage(mostrarImagen(producto.getImagenProducto()));
        cmbDistribuidor.getSelectionModel().select(obtenerIndexDistribuidor(producto.getDistribuidorId()));
        cmbCategoria.getSelectionModel().select(obtenerIndexCategoria(producto.getCategoriaProductosId()));
        cmbCategoria.getSelectionModel().select(1);
    }

    public Image mostrarImagen(Blob blob) {
        Image imagen = null;
        try {
            InputStream file = blob.getBinaryStream();
            imagen = new Image(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagen;
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
            InputStream img = new FileInputStream(imageFile);
            if (imgCargar.getImage() == null) {
                statement.setBinaryStream(7, null);
            } else {
                statement.setBinaryStream(7, img);
            }
            statement.setInt(8, ((Distribuidores) cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9, ((CategoriaProductos) cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();

        } catch (Exception e) {
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

    public void editarProducto() {
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
            if (imgCargar.getImage() == null) {
                statement.setBinaryStream(8, null);
            } else {
                InputStream img = new FileInputStream(imageFile);
                statement.setBinaryStream(8, img);
            }
            statement.setInt(9, ((Distribuidores) cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(10, ((CategoriaProductos) cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
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

    public ObservableList<CategoriaProductos> listarCategoriasProducto() {
        ArrayList<CategoriaProductos> categoriaProductos = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int categoriaProductoId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
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

    public int obtenerIndexDistribuidor(int id) {
        int index = 0;
        for (int i = 0; i < cmbDistribuidor.getItems().size(); i++) {
            int disCmb = ((Distribuidores) cmbDistribuidor.getItems().get(i)).getDistribuidorId();
            if (disCmb == id) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexCategoria(int id) {
        int index = 0;
        for (int i = 0; i < cmbCategoria.getItems().size(); i++) {
            int catCmb = ((CategoriaProductos) cmbCategoria.getItems().get(i)).getCategoriaProductosId();
            if (catCmb == id) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
