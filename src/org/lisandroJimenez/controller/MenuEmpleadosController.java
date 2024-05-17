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
import java.time.LocalTime;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.model.Cargos;
import org.lisandroJimenez.model.Empleados;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuEmpleadosController implements Initializable {

    @FXML
    Button btnGuardar, btnCancelar, btnVaciarForm;
    @FXML
    TextField tfHoraEntrada, tfHoraSalida, tfEmpleadoId, tfSueldo, tfNombre, tfApellido;
    @FXML
    ComboBox cmbEncargado, cmbCargo;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colNombre,colApellido, colSueldo, colHoraEntrada, colHoraSalida, colCargo, colEncargado;
    
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEncargado.setItems(listarEmpleado());
        cmbCargo.setItems(listarCargos());
        cargarDatos();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCancelar) {
            stage.MenuPrincipalView();
        } else if (event.getSource() == btnGuardar) {
            if (tfEmpleadoId.getText().equals("")) {
                agregarEmpleado();
                cargarDatos();
                SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
            } else {
                if (!tfHoraEntrada.getText().isEmpty() && !tfHoraSalida.getText().isEmpty() && !tfSueldo.getText().isEmpty() && cmbEncargado.getSelectionModel().getSelectedItem() != null && cmbCargo.getSelectionModel().getSelectedItem() != null) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarEmpleado();
                        cargarDatos();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(400);
                }
            }
        } else if (event.getSource() == btnVaciarForm) {
            vaciarCampos();
        }
    }

    public void cargarDatosEditar() {
        Empleados E = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
        if (E != null) {
            tfNombre.setText(E.getNombre());
            tfApellido.setText(E.getApellido());
            tfHoraEntrada.setText(E.getHoraEntrada().toString());
            tfHoraSalida.setText(E.getHoraSalida().toString());
            tfEmpleadoId.setText(Integer.toString(E.getEmpleadoId()));
            tfSueldo.setText(Double.toString(E.getSueldo()));
            cmbEncargado.getSelectionModel().select(obtenerIndexEncargado());
            cmbCargo.getSelectionModel().select(obtenerIndexCargo());
        }
    }

    public void cargarDatos() {
        tblEmpleados.setItems(listarEmpleado());
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellido"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleados, Time>("HoraEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleados, Time>("HoraSalida"));
        colCargo.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreCargo"));
        colEncargado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("Encargado"));

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
                String nombre = resultSet.getString("nombreEmpleado");
                String apellido = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                Time horaE = resultSet.getTime("horaEntrada");
                Time horaS = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("nombreCargo");
                String encargado = resultSet.getString("Encargado");
                empleados.add(new Empleados(empleadoId, nombre, apellido, sueldo, horaE, horaS, cargo, encargado));
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

    public void agregarEmpleado() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarEmpleado(?,?,?,?,?,?,?)";

            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setString(3, tfSueldo.getText());
            LocalTime horaEntrada = LocalTime.parse(tfHoraEntrada.getText());
            Time horaEntradaSQL = Time.valueOf(horaEntrada);
            statement.setTime(4, horaEntradaSQL);
            LocalTime horaSalida = LocalTime.parse(tfHoraSalida.getText());
            Time horaSalidaSQL = Time.valueOf(horaSalida);
            statement.setTime(5, horaSalidaSQL);
            statement.setInt(6, ((Cargos) cmbCargo.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(7, ((Empleados) cmbEncargado.getSelectionModel().getSelectedItem()).getEncargadoId());       
            statement.execute();
        } catch (SQLException e) {
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
    public int obtenerIndexEncargado() {
        int index = 0;
        for (int i = 0; i < cmbEncargado.getItems().size(); i++) {
            String encargadoCmb = cmbEncargado.getItems().get(i).toString();
            String encargadoTbl = ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getEncargado();  
            if (encargadoCmb.equals(encargadoTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void editarEmpleado() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarEmpleado(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfApellido.getText());
            statement.setString(4, tfSueldo.getText());
            LocalTime horaEntrada = LocalTime.parse(tfHoraEntrada.getText());
            Time horaEntradaSQL = Time.valueOf(horaEntrada);
            statement.setTime(5, horaEntradaSQL);
            LocalTime horaSalida = LocalTime.parse(tfHoraSalida.getText());
            Time horaSalidaSQL = Time.valueOf(horaSalida);
            statement.setTime(6, horaSalidaSQL);
            statement.setInt(7, ((Cargos) cmbCargo.getSelectionModel().getSelectedItem()).getCargoId());
            statement.setInt(8, ((Empleados) cmbEncargado.getSelectionModel().getSelectedItem()).getEncargadoId());

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

    public void vaciarCampos() {
        tfNombre.clear();
        tfApellido.clear();
        tfHoraEntrada.clear();
        tfHoraSalida.clear();
        tfEmpleadoId.clear();
        tfSueldo.clear();
        cmbEncargado.getSelectionModel().clearSelection();
        cmbCargo.getSelectionModel().clearSelection();
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
