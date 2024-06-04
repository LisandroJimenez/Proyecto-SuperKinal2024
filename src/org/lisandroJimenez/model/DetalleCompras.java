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
    private String producto;
    private int compraId;
    
    public DetalleCompras() {
    }

    public DetalleCompras(int compraId, int cantidadCompra,Date fechaCompra, Double totalCompra, String producto) {
        super(fechaCompra, totalCompra);
        this.compraId = compraId;
        this.cantidadCompra = cantidadCompra;
        this.producto = producto;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
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


    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }



    @Override
    public String toString() {
        return String.valueOf(compraId);
    }

    
  
}
