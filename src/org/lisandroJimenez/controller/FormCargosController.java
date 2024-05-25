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
import org.lisandroJimenez.dto.CargoDTO;
import org.lisandroJimenez.model.Cargos;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FormCargosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Main stage;
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    Button btnCancelar, btnGuardar;
    @FXML
    TextField tfCargoId, tfNombreCargo;
    @FXML
    TextArea taDescripcionCargo;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (CargoDTO.getCargoDTO().getCargo() != null) {
            cargarDatos(CargoDTO.getCargoDTO().getCargo());
        }
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCancelar) {
            CargoDTO.getCargoDTO().setCargo(null);
            stage.MenuCargosView();
        } else if (event.getSource() == btnGuardar) {
            if (op == 1) {
                if (!tfNombreCargo.getText().equals("") && !taDescripcionCargo.getText().equals("")) {
                    AgregarCargo();
                    stage.MenuCargosView();
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                    tfNombreCargo.requestFocus();
                    return;
                }
            } else if (op == 2) {
                if (!tfCargoId.getText().isEmpty() &&!tfNombreCargo.getText().equals("") && !taDescripcionCargo.getText().equals("")) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        EditarCargo();
                        stage.MenuCargosView();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                }

            }
        }
    }
    public void AgregarCargo() {

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargo(?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCargo.getText());
            statement.setString(2, taDescripcionCargo.getText());
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
    
    public void EditarCargo() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargo(?,?,?)";
            statement = conexion.prepareStatement(sql);
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombreCargo.getText());
            statement.setString(3, taDescripcionCargo.getText());
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

    public void cargarDatos(Cargos cargo) {
        tfCargoId.setText(Integer.toString(cargo.getCargoId()));
        tfNombreCargo.setText(cargo.getNombreCargo());
        taDescripcionCargo.setText(cargo.getDescripcionCargo());
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
