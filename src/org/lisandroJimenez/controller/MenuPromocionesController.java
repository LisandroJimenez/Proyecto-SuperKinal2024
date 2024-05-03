/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.model.Promociones;
import org.lisandroJimenez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuPromocionesController implements Initializable {
    
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TableView tblPromociones;
    @FXML
    TableColumn colPromocionId, colPrecio, colDescripcion, colFechaInicio, colFechaFinalizacion, colProducto;
    @FXML
    TextField tfPromocionId, tfPrecio;
    @FXML
    TextArea taDescripcion;
    @FXML
    DatePicker dpFechaInicio, dpFechaFinalizacion;
    @FXML
    ComboBox cmbProducto;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    
    public void cargarDatosEditar(){
        Promociones pr = (Promociones)tblPromociones.getSelectionModel().getSelectedItem();
        if(pr != null){
            tfPromocionId.setText(Integer.toString(pr.getPromocionId()));
            taDescripcion.setText(pr.getDescripcionPromocion());
            cmbProducto.getSelectionModel().select(0);
            cmbProducto.getSelectionModel().select(obtenerIndexPromocion());
        }
    }
    public void cargarDatos() {
        tblPromociones.setItems(listarPromocion());
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promociones, Integer>("promocionId"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Promociones, String>("precio"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Promociones, String>("descripcion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Promociones, String>("fechaInicio"));
        colFechaFinalizacion.setCellValueFactory(new PropertyValueFactory<Promociones, String>("fechaFinalizacion"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Promociones, String>("producto"));
        tblPromociones.getSortOrder().add(colPromocionId);

    }
    public int obtenerIndexPromocion(){
        int index = 0;
        for(int i = 0; i<= cmbProducto.getItems().size(); i++){
            String productoCmb = cmbProducto.getItems().get(i).toString();
            String PromocionesTbl = ((Promociones)tblPromociones.getSelectionModel().getSelectedItem()).getProducto();
            if(productoCmb.equals(PromocionesTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    public ObservableList<Promociones> listarPromocion() {
        ArrayList<Promociones> promociones = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarPromocion()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int PromocionId = resultSet.getInt("promocionId");
                Double precio = resultSet.getDouble("precioPromocion");
                String descripcion = resultSet.getString("descripcionPromocion");
                Date fechaInicio = resultSet.getDate("fechaInicio");
                Date fechaFinalizacion = resultSet.getDate("fechaFinalizacion");
                String producto = resultSet.getString("producto");

                promociones.add(new Promociones(PromocionId, precio, descripcion, fechaInicio,fechaFinalizacion, producto));
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
        return FXCollections.observableList(promociones);
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
