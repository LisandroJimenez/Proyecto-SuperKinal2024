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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.model.Usuario;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.PasswordUtils;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class LoginController implements Initializable {

    private int op = 0;
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TextField tfUser;
    @FXML
    PasswordField tfPassword;
    @FXML
    Button btnIniciar, btnRegistrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnIniciar) {
            if (op == 0) {
                Usuario usuario = buscarUsuario();
                if (usuario != null) {
                    if (PasswordUtils.getInstance().checkPassword(tfPassword.getText(), usuario.getContrasenia())) {
                        if (usuario.getNivelAccesoId() == 1) {
                            btnRegistrar.setDisable(false);
                            btnIniciar.setText("Ir al menu");
                            op = 1;
                        } else if (usuario.getNivelAccesoId() == 2) {
                            stage.MenuPrincipalView();
                            SuperKinalAlert.getInstance().alertaSaludo(usuario.getUsuario());
                        }
                    } else {
                        SuperKinalAlert.getInstance().mostrarAlertasInfo(403);
                    }

                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(402);
                }

            } else {
                Usuario usuario = buscarUsuario();
                stage.MenuPrincipalView();
                SuperKinalAlert.getInstance().alertaSaludo(usuario.getUsuario());
            }

        } else if (event.getSource() == btnRegistrar) {
            stage.FormUsuarioView();
        }
    }

    public Usuario buscarUsuario() {
        Usuario usuarios = null;
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarUsuario(?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfUser.getText());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int usuarioId = resultSet.getInt("usuarioId");
                String usuario = resultSet.getString("usuario");
                String contrasenia = resultSet.getString("contrasenia");
                int nivelAccesoId = resultSet.getInt("nivelAccesoId");
                int empleadoId = resultSet.getInt("empleadoId");

                usuarios = new Usuario(usuarioId, usuario, contrasenia, nivelAccesoId, empleadoId);
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
        return usuarios;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
