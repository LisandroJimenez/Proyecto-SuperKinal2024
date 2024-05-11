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
public class DetalleFacturas extends Facturas {
    private int detalleFacturaId;

    public DetalleFacturas(int facturaId, Date fecha, Time hora, int clienteId, int empleadoId, Double total) {
        super(facturaId, fecha, hora, clienteId, empleadoId, total);
    }

    public DetalleFacturas(int facturaId, Date fecha, Time hora, Double total, String cliente, String empleado) {
        super(facturaId, fecha, hora, total, cliente, empleado);
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }
    
    
}
