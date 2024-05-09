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
import org.lisandroJimenez.dto.DistribuidorDTO;
import org.lisandroJimenez.model.Distribuidores;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FormDistribuidoresController implements Initializable {

    private Main stage;
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    Button btnGuardar, btnCancelar;
    @FXML
    TextField tfDistribuidorId, tfNombre, tfDireccion, tfNit, tfTelefono, tfWeb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null) {
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCancelar) {
            DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
            stage.MenuDistribuidoresView();
        } else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                if (!tfNombre.getText().equals("") && !tfDireccion.getText().equals("") && !tfNit.getText().equals("") && !tfTelefono.getText().equals("")) {
                    AgregarDistribuidor();
                    stage.MenuDistribuidoresView();
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                    tfNombre.requestFocus();
                    return;
                }
            } else if (op == 2) {
                if (!tfNombre.getText().equals("") && !tfDireccion.getText().equals("") && !tfNit.getText().equals("") && !tfTelefono.getText().equals("")) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        EditarDistribuidor();
                        stage.MenuDistribuidoresView();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                }

            }
        }
    }

    public void AgregarDistribuidor() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidor(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfDireccion.getText());
            statement.setString(3, tfNit.getText());
            statement.setString(4, tfTelefono.getText());
            if(tfWeb.getText().isEmpty()){
                statement.setString(5, null);
            }else{
                statement.setString(5, tfWeb.getText());
                
            }
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

    public void EditarDistribuidor() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidor(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfDireccion.getText());
            statement.setString(4, tfNit.getText());
            statement.setString(5, tfTelefono.getText());
            statement.setString(6, tfWeb.getText());
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

    public void cargarDatos(Distribuidores distribuidor) {
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNombre.setText(distribuidor.getNombreDistribuidor());
        tfDireccion.setText(distribuidor.getDireccionDistribuidor());
        tfNit.setText(distribuidor.getNitDistribuidor());
        tfTelefono.setText(distribuidor.getTelefonoDistribuidor());
        tfWeb.setText(distribuidor.getWeb());
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
