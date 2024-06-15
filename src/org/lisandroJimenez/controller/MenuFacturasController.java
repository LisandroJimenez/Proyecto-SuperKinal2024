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
import org.lisandroJimenez.model.Facturas;
import org.lisandroJimenez.model.Productos;
import org.lisandroJimenez.report.GenerarReporte;
import org.lisandroJimenez.system.Main;
import org.lisandroJimenez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MenuFacturasController implements Initializable {

    @FXML
    Button btnRegresar, btnGuardar, btnVaciar, btnVerFactura, btnFactura;
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
            if (cmbFacturaId.getSelectionModel().getSelectedItem() == null) {
                agregarFactura();
                cmbFacturaId.setItems(listarFacturaIds());
                cargarDatos();
                SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
            } else {
                agregarDetalleFactura();
                cmbFacturaId.setItems(listarFacturaIds());
                cargarDatos();
                SuperKinalAlert.getInstance().mostrarAlertasInfo(401);
            }

        } else if (event.getSource() == btnVaciar) {
            vaciarCampos();
        } else if (event.getSource() == btnVerFactura) {
            GenerarReporte.getInstance().generarFactura(((Facturas) tblFacturas.getSelectionModel().getSelectedItem()).getFacturaId());
        } else if (event.getSource() == btnFactura) {
            cargarDatos();
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
        Object selectedItem = tblFacturas.getSelectionModel().getSelectedItem();

        if (selectedItem instanceof DetalleFacturas) {
            int facturaTbl = ((DetalleFacturas) selectedItem).getFacturaId();

            ObservableList<Facturas> facturasList = cmbFacturaId.getItems();
            for (int i = 0; i < facturasList.size(); i++) {
                Facturas facturaCmb = facturasList.get(i);
                if (facturaCmb.getFacturaId() == facturaTbl) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    public void cargarDatos() {
        Facturas facturaSeleccionada = (Facturas) cmbFacturaId.getSelectionModel().getSelectedItem();

        // Verificar si hay una factura seleccionada
        if (facturaSeleccionada != null) {
            // Obtener el ID de la factura seleccionada
            int facturaIdSeleccionada = facturaSeleccionada.getFacturaId();

            // Cargar los detalles de la factura seleccionada en la tabla
            tblFacturas.setItems(listarFactura(facturaIdSeleccionada));
            colFacturaId.setCellValueFactory(new PropertyValueFactory<>("facturaId"));
            colProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
            colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
            colEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
            colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("precioVentaUnitario"));
        tfTotal.setText(Double.toString(facturaSeleccionada.getTotal()));
        dpFecha.setValue(facturaSeleccionada.getFecha().toLocalDate());
        tfHora.setText(facturaSeleccionada.getHora().toString());
        }
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

    public ObservableList<DetalleFacturas> listarFactura(int facturaIdSeleccionada) {
        ArrayList<DetalleFacturas> factura = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDetalleFactura(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, facturaIdSeleccionada);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                String producto = resultSet.getString("Producto");
                String cliente = resultSet.getString("Cliente");
                String empleado = resultSet.getString("Empleado");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                Double precio = resultSet.getDouble("precioVentaUnitario");
                Double total = resultSet.getDouble("total");
                factura.add(new DetalleFacturas(producto, facturaId, fecha, hora, cliente, empleado, total, precio));
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

    public ObservableList<Facturas> listarFacturaIds() {
        ArrayList<Facturas> facturas = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarFactura()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int facturaId = resultSet.getInt("facturaId");
                int cliente = resultSet.getInt("clienteId");
                int empleado = resultSet.getInt("empleadoId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                Double total = resultSet.getDouble("total");
                facturas.add(new Facturas(facturaId, fecha, hora, cliente, empleado, total));
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
        ObservableList<Facturas> observableFacturas = FXCollections.observableList(facturas);
        return observableFacturas;
    }

    public void agregarDetalleFactura() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleFactura(?, ?)";
            statement = conexion.prepareStatement(sql);
            Facturas facturaSeleccionada = (Facturas) cmbFacturaId.getSelectionModel().getSelectedItem();
            Productos productoSeleccionado = (Productos) cmbProductos.getSelectionModel().getSelectedItem();
            if (facturaSeleccionada != null && productoSeleccionado != null) {
                Integer facturaId = facturaSeleccionada.getFacturaId();
                Integer productoId = productoSeleccionado.getProductoId();
                statement.setInt(1, facturaId);
                statement.setInt(2, productoId);
                statement.execute();
                cmbFacturaId.setItems(listarFacturaIds());
                cmbFacturaId.getSelectionModel().select(facturaSeleccionada);
            }

            cargarDatos();
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

                // Después de agregar la factura, actualizar lista de facturas disponibles
                cmbFacturaId.setItems(listarFacturaIds());
                // Mantener la selección actual si es relevante
                cmbFacturaId.getSelectionModel().selectLast();
                // Cargar los datos actualizados en la tabla
                cargarDatos();
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

    public void vaciarCampos() {
        tfHora.clear();
        tfTotal.clear();
        dpFecha.setValue(null);
        cmbFacturaId.getSelectionModel().clearSelection();
        cmbProductos.getSelectionModel().clearSelection();
        cmbClientes.getSelectionModel().clearSelection();
        cmbEmpleados.getSelectionModel().clearSelection();
        tblFacturas.setItems(FXCollections.observableArrayList());
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
