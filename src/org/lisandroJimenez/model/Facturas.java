/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Usuario
 */
public class Facturas {

    private int facturaId;
    private Date fecha;
    private Time hora;
    private int clienteId;
    private int empleadoId;
    private Double total;//
    private String cliente;
    private String empleado;
    private String factura;

    public Facturas(Date fecha, Time hora, String cliente, String empleado, Double total) {
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
    }
    public Facturas(Date fecha, Time hora, String cliente, String empleado) {
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    public Facturas(int facturaId, Date fecha, Time hora, int clienteId, int empleadoId, Double total) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.total = total;
    }

    
    public Facturas(String factura, Date fecha, Time hora, String cliente, String empleado, Double total) {
        this.factura = factura;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
    }

    public Facturas(Date fecha, Time hora, int clienteId, int empleadoId, Double total, String cliente, String empleado) {
        this.fecha = fecha;
        this.hora = hora;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.total = total;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    public Facturas(String factura) {
        this.factura = factura;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    @Override
    public String toString() {
        return String.valueOf(facturaId);
    }

}
