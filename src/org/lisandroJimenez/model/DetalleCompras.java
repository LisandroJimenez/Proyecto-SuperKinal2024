/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.model;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class DetalleCompras extends Compras{
    private int detalleCompraId;
    private int cantidadCompra;
    private int productoId;
    private String producto;
    private String compra;
    
    public DetalleCompras() {
    }

    public DetalleCompras(int detalleCompraId, int cantidadCompra, int productoId, String producto, String compra, int compraId, Date fechaCompra, Double totalCompra) {
        super(compraId, fechaCompra, totalCompra);
        this.detalleCompraId = detalleCompraId;
        this.cantidadCompra = cantidadCompra;
        this.productoId = productoId;
        this.producto = producto;
        this.compra = compra;
    }

    public int getDetalleCompraId() {
        return detalleCompraId;
    }

    public void setDetalleCompraId(int detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    

    
  
}
