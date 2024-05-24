package org.lisandroJimenez.model;


import java.sql.Date;
import java.sql.Time;
import org.lisandroJimenez.model.Facturas;

public class DetalleFacturas extends Facturas {
    private int detalleFacturaId;
    private String producto;
    private int productoId;
    private int facturaId;

    public DetalleFacturas(String producto, int facturaId, Date fecha, Time hora, String cliente, String empleado, Double total) {
        super(fecha, hora, cliente, empleado, total);
        this.producto = producto;
        this.facturaId = facturaId;
    }

    public DetalleFacturas(Date fecha, Time hora, int clienteId, int empleadoId, Double total, String cliente, String empleado) {
        super(fecha, hora, clienteId, empleadoId, total, cliente, empleado);
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    

    
    @Override
    public String toString() {
        return "" + facturaId; 
    }
}