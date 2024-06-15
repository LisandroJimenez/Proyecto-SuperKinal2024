package org.lisandroJimenez.model;

import java.sql.Date;
import java.sql.Time;
import org.lisandroJimenez.model.Facturas;

public class DetalleFacturas extends Facturas {

    private int detalleFacturaId;
    private String producto;
    private int productoId;
    private int facturaId;
    private Double precioVentaUnitario;


    public DetalleFacturas(String producto, int facturaId, Date fecha, Time hora, String cliente, String empleado,Double total,  Double precioVentaUnitario) {
        super(fecha, hora, cliente, empleado, total);
        this.facturaId = facturaId;
        this.producto = producto;
        this.precioVentaUnitario = precioVentaUnitario;
    }

    public double getPrecioVentaUnitario() {
        return precioVentaUnitario;
    }

    public void setPrecioVentaUnitario(double precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
    }

    
    
    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
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



    @Override
    public String toString() {
        return String.valueOf(facturaId);
    }
}
