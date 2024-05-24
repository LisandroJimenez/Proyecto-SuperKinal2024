/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author informatica
 */
public class SuperKinalAlert {

    private static SuperKinalAlert instance;

    private SuperKinalAlert() {

    }

    public static SuperKinalAlert getInstance() {
        if (instance == null) {
            instance = new SuperKinalAlert();
        }
        return instance;
    }

    public void mostrarAlertasInfo(int code) {
        if (code == 400) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos Pendientes");
            alert.setContentText("Algunos campos necesarios para el registro estan pendientes");
            alert.showAndWait();

        } else if (code == 401) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion de registro");
            alert.setHeaderText("Confirmacion de registro");
            alert.setContentText("El registro se ha creado con exito!!!!!");
            alert.showAndWait();
        } else if (code == 404) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al Eliminar");
            alert.setHeaderText("Error al Eliminar");
            alert.setContentText("El registro no se puede eliminar porque es foranea");
            alert.showAndWait();
        } else if (code == 402) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario Incorrecto");
            alert.setHeaderText("Usuario Incorrecto");
            alert.setContentText("Verifique el Usuario");
            alert.showAndWait();
        } else if (code == 403) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contraseña Incorrecta");
            alert.setHeaderText("Contraseña Incorrecta");
            alert.setContentText("Verifique la contraseña");
            alert.showAndWait();
        }else if (code == 406) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Producto Agotado");
            alert.setHeaderText("Producto Agotado");
            alert.setContentText("Lo sentimos, ya no hay producto en el Stock");
            alert.showAndWait();
        }
    }

    public Optional<ButtonType> mostrarAlertaConfirmacion(int code) {
        Optional<ButtonType> action = null;
        if (code == 405) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion de registro");
            alert.setHeaderText("Eliminacion de registro");
            alert.setContentText("¿Desea confirmar la eliminacion de registro?");
            action = alert.showAndWait();
        } else if (code == 106) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edicion de registro");
            alert.setHeaderText("Edicion de registro");
            alert.setContentText("¿Desea confirmar la Edicion de registro?");
            action = alert.showAndWait();

        }
        return action;
    }

    public void alertaSaludo(String usuario) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Bienvenido!!!!!");
        alert.setHeaderText("Bienvenido");
        alert.setContentText("Bienvenido a Super Kinal " + usuario);
        alert.showAndWait();

    }
}
