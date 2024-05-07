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
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.lisandroJimenez.system.Main;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuComprasController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    
}
