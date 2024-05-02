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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.dto.CategoriaProductoDTO;
import org.lisandroJimenez.model.CategoriaProductos;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 *
 * @author informatica
 */
public class FormCategoriaProductosController implements Initializable {

    private Main stage;
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    Button btnCancelar, btnGuardar;
    @FXML
    TextField tfCategoriaId, tfNombreCategoria;
    @FXML
    TextArea taDescripcionCategoria;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto() != null) {
            cargarDatos(CategoriaProductoDTO.getCategoriaProductoDTO().getCategoriaProducto());
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCancelar) {
            CategoriaProductoDTO.getCategoriaProductoDTO().setCategoriaProducto(null);
            stage.MenuCategoriaProductosView();
        } else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                if (!tfNombreCategoria.getText().equals("") && !taDescripcionCategoria.getText().equals("")) {
                    AgregarCategoriaProducto();
                    stage.MenuCategoriaProductosView();
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                    tfNombreCategoria.requestFocus();
                    return;
                }
            } else if (op == 2) {
                if (!tfNombreCategoria.getText().equals("") && !taDescripcionCategoria.getText().equals("")) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        EditarCategoriaProducto();
                        stage.MenuCategoriaProductosView();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                }
            }
        }
    }

    

    public void AgregarCategoriaProducto() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProducto(?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCategoria.getText());
            statement.setString(2, taDescripcionCategoria.getText());
            statement.executeUpdate();
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

    public void EditarCategoriaProducto() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCategoriaProducto(?,?,?)";
            statement = conexion.prepareStatement(sql);
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaId.getText()));
            statement.setString(2, tfNombreCategoria.getText());
            statement.setString(3, taDescripcionCategoria.getText());
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

    public void cargarDatos(CategoriaProductos categoriaProductos) {
        tfCategoriaId.setText(Integer.toString(categoriaProductos.getCategoriaProductosId()));
        tfNombreCategoria.setText(categoriaProductos.getNombreCategoria());
        taDescripcionCategoria.setText(categoriaProductos.getDescripcionCategoria());
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

}
