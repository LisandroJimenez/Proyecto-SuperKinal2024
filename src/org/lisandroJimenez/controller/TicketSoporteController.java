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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.model.Cliente;
import org.lisandroJimenez.model.TicketSoporte;
import org.lisandroJimenez.system.Main;

/**
 *
 * @author informatica
 */
public class TicketSoporteController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TableColumn colTicketId, colDescripcion, colEstatus, colCliente, colFactura;
    @FXML
    TableView tblTicketSoporte;
    @FXML
    TextField textId;
    @FXML
    TextArea textDescripcion;
    @FXML
    Button btnBack, btnGuardar, btnVaciarForm;
    @FXML
    ComboBox cmbEstatus, cmbClientes, cmbFactura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCmbEstatus();
        cmbClientes.setItems(listarClientes());
        cargarDatos();
    }

    public void cargarCmbEstatus() {
        cmbEstatus.getItems().add("En Proceso");
        cmbEstatus.getItems().add("Finalizado");
    }

    public void cargarDatos() {
        tblTicketSoporte.setItems(listarTickets());
        colTicketId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcionTicket"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("Status"));
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("cliente"));
        colFactura.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("facturaId"));
        tblTicketSoporte.getSortOrder().add(colTicketId);

    }
    
    public void cargarDatosEditar(){
        TicketSoporte ts = (TicketSoporte)tblTicketSoporte.getSelectionModel().getSelectedItem();
        if(ts != null){
            textId.setText(Integer.toString(ts.getTicketSoporteId()));
            textDescripcion.setText(ts.getDescripcionTicket());
            cmbEstatus.getSelectionModel().select(0);
            cmbClientes.getSelectionModel().select(obtenerIndexCliente());
        }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        for(int i = 0; i<= cmbClientes.getItems().size(); i++){
            String clienteCmb = cmbClientes.getItems().get(i).toString();
            String clienteTbl = ((TicketSoporte)tblTicketSoporte.getSelectionModel().getSelectedItem()).getCliente();
            if(clienteCmb.equals(clienteTbl)){
                index = i;
                break;
            }
        }
        return index;
    }

    public ObservableList<TicketSoporte> listarTickets() {
        ArrayList<TicketSoporte> tickets = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarTicketSoporte()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ticketSoporteId = resultSet.getInt("ticketSoporteId");
                String descripcion = resultSet.getString("descripcionTicket");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                int facturaId = resultSet.getInt("facturaId");

                tickets.add(new TicketSoporte(ticketSoporteId, descripcion, estatus, cliente, facturaId));
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
        return FXCollections.observableList(tickets);
    }

    public void agregarTicket() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarTicketSoporte(?,?,?)";

            statement = conexion.prepareStatement(sql);
            statement.setString(1, textDescripcion.getText());
            statement.setInt(2, ((Cliente) cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3, 1);
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
    
    public void editarTicket(){
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarTicketSoporte(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(textId.getText()));
            statement.setString(2, textDescripcion.getText());
            statement.setString(3, cmbEstatus.getSelectionModel().getSelectedItem().toString());
            statement.setInt(4, ((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, 1);
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

    public ObservableList<Cliente> listarClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCliente()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");

                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
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
        return FXCollections.observableList(clientes);
    }
    
    public void vaciarCampos(){
        textId.clear();
        textDescripcion.clear();
        cmbEstatus.getSelectionModel().clearSelection();
        cmbClientes.getSelectionModel().clearSelection();
    }
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnBack) {
            stage.MenuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(textId.getText().equals("")){
                agregarTicket();
                cargarDatos();
            }else{
                editarTicket();
                cargarDatos();
            }
        }else if(event.getSource()== btnVaciarForm){
            vaciarCampos();
        }
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
