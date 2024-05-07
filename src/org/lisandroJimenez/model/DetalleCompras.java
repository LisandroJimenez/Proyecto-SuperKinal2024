/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.model;

/**
 *
 * @author Usuario
 */
public class DetalleCompras {
    private int detalleCompraId;
    private int cantidadCompra;
    private int productoId;
    private int compraId;
    private String producto;

    public DetalleCompras() {
    }

    public DetalleCompras(int detalleCompraId, int cantidadCompra, int productoId, int compraId) {
        this.detalleCompraId = detalleCompraId;
        this.cantidadCompra = cantidadCompra;
        this.productoId = productoId;
        this.compraId = compraId;
    }

    public DetalleCompras(int detalleCompraId, int cantidadCompra, int compraId, String producto) {
        this.detalleCompraId = detalleCompraId;
        this.cantidadCompra = cantidadCompra;
        this.compraId = compraId;
        this.producto = producto;
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

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "DetalleCompras{" + "detalleCompraId=" + detalleCompraId + ", cantidadCompra=" + cantidadCompra + ", productoId=" + productoId + ", compraId=" + compraId + ", producto=" + producto + '}';
    }
    
    
}
