/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lisandroJimenez.dao.Conexion;
import org.lisandroJimenez.dto.ProductoDTO;
import org.lisandroJimenez.model.Cliente;
import org.lisandroJimenez.model.DetalleFacturas;
import org.lisandroJimenez.model.Empleados;
import org.lisandroJimenez.model.Productos;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuFacturasController implements Initializable {

    @FXML
    Button btnRegresar, btnGuardar, btnVaciar;
    @FXML
    TextField tfHora, tfTotal;
    @FXML
    ComboBox cmbFacturaId, cmbProductos, cmbClientes, cmbEmpleados;
    @FXML
    TableView tblFacturas;
    @FXML
    TableColumn colFacturaId, colProducto, colCliente, colEmpleado, colFecha, colHora, colTotal;
    @FXML
    DatePicker dpFecha;

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbFacturaId.setItems(listarFacturaIds());
        cmbClientes.setItems(listarClientes());
        cmbEmpleados.setItems(listarEmpleado());
        cmbProductos.setItems(listarProducto());
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.MenuPrincipalView();
        } else if (event.getSource() == btnGuardar) {
            Productos producto = (Productos) cmbProductos.getSelectionModel().getSelectedItem();
            if (producto.getCantidadStock() <= 0) {
                SuperKinalAlert.getInstance().mostrarAlertasInfo(406);
            } else {
                if (cmbFacturaId.getSelectionModel().getSelectedItem() == null) {
                    agregarFactura();
                    cargarDatos();
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
                } else if (cmbClientes.getSelectionModel().getSelectedItem() == null && cmbEmpleados.getSelectionModel().getSelectedItem() == null) {
                    agregarDetalleFactura();
                    cargarDatos();
                    SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
                }

            }

        }
    }

    public int obtenerIndexEmpleado() {
        int index = 0;
        for (int i = 0; i < cmbEmpleados.getItems().size(); i++) {
            String empleadoCmb = cmbEmpleados.getItems().get(i).toString();
            String facturasTbl = ((DetalleFacturas) tblFacturas.getSelectionModel().getSelectedItem()).getEmpleado();
            if (empleadoCmb.equals(facturasTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexCliente() {
        int index = 0;
        for (int i = 0; i < cmbClientes.getItems().size(); i++) {
            String clienteCmb = cmbClientes.getItems().get(i).toString();
            String facturasTbl = ((DetalleFacturas) tblFacturas.getSelectionModel().getSelectedItem()).getCliente();
            if (clienteCmb.equals(facturasTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int obtenerIndexProducto() {
        int index = 0;
        for (int i = 0; i < cmbProductos.getItems().size(); i++) {
            String productoCmb = cmbProductos.getItems().get(i).toString();
            String facturasTbl = ((DetalleFacturas) tblFacturas.getSelectionModel().getSelectedItem()).getProducto();
            if (productoCmb.equals(facturasTbl)) {
                index = i;
                break;
            }
        }

        return index;
    }

    public int obtenerIndexFactura() {
        int index = 0;
        for (int i = 0; i < cmbFacturaId.getItems().size(); i++) {
            String facturaCmb = cmbFacturaId.getItems().get(i).toString();
            int facturasTbl = ((DetalleFacturas) tblFacturas.getSelectionModel().getSelectedItem()).getFacturaId();
            if (facturaCmb.equals(facturasTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void cargarDatos() {
        tblFacturas.setItems(listarFactura());
        colFacturaId.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Integer>("facturaId"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, String>("Producto"));
        colCliente.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, String>("Cliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, String>("Empleado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Date>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Time>("hora"));
        colTotal.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Double>("total"));
    }

    public void cargarDatosEditar() {
        DetalleFacturas DF = (DetalleFacturas) tblFacturas.getSelectionModel().getSelectedItem();
        if (DF != null) {
            cmbFacturaId.getSelectionModel().select(obtenerIndexFactura());
            cmbEmpleados.getSelectionModel().select(obtenerIndexEmpleado());
            cmbClientes.getSelectionModel().select(obtenerIndexCliente());
            cmbProductos.getSelectionModel().select(obtenerIndexProducto());
            dpFecha.setValue(DF.getFecha().toLocalDate());
            tfHora.setText(DF.getHora().toString());
            tfTotal.setText(Double.toString(DF.getTotal()));
        }
    }

    public ObservableList<Productos> listarProducto() {
        ArrayList<Productos> productos = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int productoId = resultSet.getInt("productoId");
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcionProducto");
                int cantidad = resultSet.getInt("cantidadStock");
                Double precioU = resultSet.getDouble("precioVentaUnitario");
                Double precioM = resultSet.getDouble("precioVentaMayor");
                Double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagen = resultSet.getBlob("imagenProducto");
                String distribuidorId = resultSet.getString("distribuidor");
                String categoriaProductoId = resultSet.getString("categoriaProducto");

                productos.add(new Productos(productoId, nombre, descripcion, cantidad, precioU, precioM, precioCompra, imagen, distribuidorId, categoriaProductoId));
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
        return FXCollections.observableList(productos);
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

    public ObservableList<Empleados> listarEmpleado() {
        ArrayList<Empleados> empleados = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarEmpleado()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int empleadoId = resultSet.getInt("empleadoId");
                String nombre = resultSet.getString("nombreEmpleado");
                String apellido = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("Sueldo");
                Time horaE = resultSet.getTime("horaEntrada");
                Time horaS = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("Cargo");
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

    public ObservableList<DetalleFacturas> listarFactura() {
        ArrayList<DetalleFacturas> factura = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDetalleFactura()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                String producto = resultSet.getString("Producto");
                String cliente = resultSet.getString("Cliente");
                String empleado = resultSet.getString("Empleado");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                Double total = resultSet.getDouble("total");
                factura.add(new DetalleFacturas(producto, facturaId, fecha, hora, cliente, empleado, total));
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
        return FXCollections.observableList(factura);
    }

    public ObservableList<Integer> listarFacturaIds() {
        HashSet<Integer> facturaIds = new HashSet<>(); 

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDetalleFactura()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                facturaIds.add(facturaId); 
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
        return FXCollections.observableList(new ArrayList<>(facturaIds));
    }

    public void agregarDetalleFactura() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleFactura(?,?)";
            statement = conexion.prepareStatement(sql);
            DetalleFacturas facturaSeleccionada = (DetalleFacturas) cmbFacturaId.getSelectionModel().getSelectedItem();
            Productos productoSeleccionado = (Productos) cmbProductos.getSelectionModel().getSelectedItem();

            if (facturaSeleccionada != null && productoSeleccionado != null) {
                statement.setInt(1, facturaSeleccionada.getFacturaId());
                statement.setInt(2, productoSeleccionado.getProductoId());
                statement.execute();
            }
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

    public void agregarFactura() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarFactura(?,?,?)";
            statement = conexion.prepareStatement(sql);
            Cliente clienteSeleccionado = (Cliente) cmbClientes.getSelectionModel().getSelectedItem();
            Empleados empleadoSeleccionado = (Empleados) cmbEmpleados.getSelectionModel().getSelectedItem();
            Productos productoSeleccionado = (Productos) cmbProductos.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado != null && empleadoSeleccionado != null && productoSeleccionado != null) {
                statement.setInt(1, clienteSeleccionado.getClienteId());
                statement.setInt(2, empleadoSeleccionado.getEmpleadoId());
                statement.setInt(3, productoSeleccionado.getProductoId());
                statement.execute();
            }
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

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
