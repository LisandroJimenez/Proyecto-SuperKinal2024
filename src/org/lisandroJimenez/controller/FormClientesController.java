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
import javafx.scene.control.TextField;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.dto.ClienteDTO;
import org.lisandroJimenez.model.Cliente;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 *
 * @author informatica
 */
public class FormClientesController implements Initializable {

    private Main stage;
    private int op;
    @FXML
    Button btnGuardar, btnCancelar;
    @FXML
    TextField tfClienteId, tfNombre, tfApellido, tfTelefono, tfDireccion, tfNit;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ClienteDTO.getClienteDTO().getCliente() != null) {
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }
    }
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCancelar) {
            ClienteDTO.getClienteDTO().setCliente(null);
            stage.MenuClientesView();
        } else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                if (!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfDireccion.getText().equals("")) {
                    AgregarClientes();
                    stage.MenuClientesView();
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                    tfNombre.requestFocus();
                    return;
                }
            } else if (op == 2) {
                if (!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfDireccion.getText().equals("")) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        EditarClientes();
                        stage.MenuClientesView();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                }

            }
        }
    }

    public void AgregarClientes() {

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCliente(?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfTelefono.getText());
            statement.setString(4, tfDireccion.getText());
            statement.setString(5, tfNit.getText());
            statement.executeUpdate();
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

    }

    public void EditarClientes() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCliente(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfClienteId.getText()));
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfTelefono.getText());
            statement.setString(4, tfDireccion.getText());
            statement.setString(5, tfNit.getText());
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

    public void cargarDatos(Cliente cliente) {
        tfClienteId.setText(Integer.toString(cliente.getClienteId()));
        tfNombre.setText(cliente.getNombre());
        tfApellido.setText(cliente.getApellido());
        tfTelefono.setText(cliente.getTelefono());
        tfDireccion.setText(cliente.getDireccion());
        tfNit.setText(cliente.getNit());
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
