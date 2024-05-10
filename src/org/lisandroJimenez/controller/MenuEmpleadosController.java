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
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.model.Cargos;
import org.lisandroJimenez.model.Empleados;
import org.lisandroJimenez.system.Main;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuEmpleadosController implements Initializable {

    @FXML
    TextField tfHoraEntrada, tfHoraSalida, tfEmpleadoId, tfSueldo;
    @FXML
    ComboBox cmbEncargado, cmbEmpleado, cmbCargo;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colEmpleado, colSueldo, colHoraEntrada, colHoraSalida, colCargo, colEncargado;
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEncargado.setItems(listarEmpleado());
        cmbEmpleado.setItems(listarEmpleado());
        cmbCargo.setItems(listarCargos());
        cargarDatos();
    }    
    
    public void cargarDatosEditar(){
        Empleados E = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
        if(E != null){
            tfHoraEntrada.setText(E.getHoraEntrada().toString());
            tfHoraSalida.setText(E.getHoraSalida().toString());
            tfEmpleadoId.setText(Integer.toString(E.getEmpleadoId()));
            tfSueldo.setText(Double.toString(E.getSueldo()));
            cmbEncargado.getSelectionModel().select(obtenerIndexEncargado());
            cmbEmpleado.getSelectionModel().select(obtenerIndexEmpleado());
            cmbCargo.getSelectionModel().select(obtenerIndexCargo());
        }
    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(listarEmpleado());
        colEmpleado.setCellValueFactory(new  PropertyValueFactory<Empleados, String>("Empleado"));
        colSueldo.setCellValueFactory(new  PropertyValueFactory<Empleados, Double>("sueldo"));
        colHoraEntrada.setCellValueFactory(new  PropertyValueFactory<Empleados, Time>("HoraEntrada"));
        colHoraSalida.setCellValueFactory(new  PropertyValueFactory<Empleados, Time>("HoraSalida"));
        colCargo.setCellValueFactory(new  PropertyValueFactory<Empleados, String>("NombreCargo"));
        colEncargado.setCellValueFactory(new  PropertyValueFactory<Empleados, String>("Encargado"));
        
    }
    
    public int obtenerIndexEmpleado(){
        int index = 0;
        Empleados selectEmpleado = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
        if (selectEmpleado != null) {
            ObservableList<Empleados> empleados = cmbEncargado.getItems();
            for (int i = 0; i < empleados.size(); i++) {
                Empleados empleado = empleados.get(i);
                if (empleado.getEmpleado().equals(selectEmpleado.getEmpleado())) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    public int obtenerIndexEncargado() {
        int index = 0;
        Empleados selectEmpleado = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
        if (selectEmpleado != null) {
            ObservableList<Empleados> empleados = cmbEncargado.getItems();
            for (int i = 0; i < empleados.size(); i++) {
                Empleados empleado = empleados.get(i);
                if (empleado.getEmpleado().equals(selectEmpleado.getEncargado())) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    
    public int obtenerIndexCargo() {
        int index = 0;
        Empleados selectCargo = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
        if (selectCargo != null) {
            ObservableList<Empleados> empleados = cmbEncargado.getItems();
            for (int i = 0; i < empleados.size(); i++) {
                Empleados empleado = empleados.get(i);
                if (empleado.getCargo().equals(selectCargo.getCargo())) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    
    public ObservableList<Empleados> listarEmpleado() {
        ArrayList<Empleados> empleados = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarEmpleado()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int empleadoId = resultSet.getInt("empleadoId");
                String empleado = resultSet.getString("Empleado");
                Double sueldo = resultSet.getDouble("sueldo");
                Time horaE = resultSet.getTime("horaEntrada");
                Time horaS = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("NombreCargo");
                String encargado = resultSet.getString("Encargado");
                empleados.add(new Empleados(empleadoId, empleado, sueldo, horaE, horaS, cargo, encargado));
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
        return FXCollections.observableList(empleados);
    }
    
   

    
    public ObservableList<Cargos> listarCargos() {
        ArrayList<Cargos> cargos = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargo()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                cargos.add(new Cargos(cargoId, nombreCargo, descripcionCargo));
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
        return FXCollections.observableList(cargos);
    }
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
