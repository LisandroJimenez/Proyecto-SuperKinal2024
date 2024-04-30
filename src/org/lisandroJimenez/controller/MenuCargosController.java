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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class MenuCargosController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    /**
     * Initializes the controller class.
     */
    @FXML
    TableView tblCargos;
    @FXML
    Button btnBack, btnAgregar, btnEliminar, btnEditar, btnBuscar;
    @FXML
    TableColumn colCargoId, colNombreCargo, colDescripcionCargo;
    @FXML
    TextField tfBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLista();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnBack) {
            stage.MenuPrincipalView();

        } else if (event.getSource() == btnEliminar) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
                int carId = ((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getCargoId();
                eliminarCargo(carId);
                cargarLista();
            }
        }else if(event.getSource() == btnAgregar){
            stage.FormCargosView(1);
        }else if(event.getSource() == btnEditar){
            CargoDTO.getCargoDTO().setCargo((Cargos)tblCargos.getSelectionModel().getSelectedItem());
            stage.FormCargosView(2);
        }else if(event.getSource() == btnBuscar){
            tblCargos.getItems().clear();
            if (tfBuscar.getText().equals("")){
                cargarLista();
            }else{
                tblCargos.getItems().add(buscarCargo());
                colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("cargoId"));
                colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
                colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("DescripcionCargo"));
            }
        }
    }

    public void cargarLista() {
        tblCargos.setItems(listarCargos());
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("descripcionCargo"));
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

    public void eliminarCargo(int carId) {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCargo(?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, carId);
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
    
    public Cargos buscarCargo(){
        Cargos cargo = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargo = (new Cargos(cargoId, nombreCargo, descripcionCargo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        return cargo;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
