/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.model;

/**
 *
 * @author informatica
 */
public class TicketSoporte {
    private int ticketSoporteId;
    private String descripcionTicket;
    private String Status;
    private String cliente;
    private int clienteId;
    private int facturaId;

    public TicketSoporte() {
    }

    public TicketSoporte(int ticketSoporteId, String descripcionTicket, String Status, String cliente, int facturaId) {
        this.ticketSoporteId = ticketSoporteId;
        this.descripcionTicket = descripcionTicket;
        this.Status = Status;
        this.cliente = cliente;
        this.facturaId = facturaId;
    }

    public TicketSoporte(int ticketSoporteId, String descripcionTicket, String Status, int clienteId, int facturaId) {
        this.ticketSoporteId = ticketSoporteId;
        this.descripcionTicket = descripcionTicket;
        this.Status = Status;
        this.clienteId = clienteId;
        this.facturaId = facturaId;
    }

    public int getTicketSoporteId() {
        return ticketSoporteId;
    }

    public void setTicketSoporteId(int ticketSoporteId) {
        this.ticketSoporteId = ticketSoporteId;
    }

    public String getDescripcionTicket() {
        return descripcionTicket;
    }

    public void setDescripcionTicket(String descripcionTicket) {
        this.descripcionTicket = descripcionTicket;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    @Override
    public String toString() {
        return "TicketSoporte{" + "ticketSoporteId=" + ticketSoporteId + ", descripcionTicket=" + descripcionTicket + ", Status=" + Status + ", cliente=" + cliente + ", clienteId=" + clienteId + ", facturaId=" + facturaId + '}';
    }
    
    
    
}
