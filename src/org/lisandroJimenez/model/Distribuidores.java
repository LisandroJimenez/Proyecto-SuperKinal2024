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
public class Distribuidores {
    int distribuidorId;
    String nombreDistribuidor;
    String direccionDistribuidor;
    String nitDistribuidor;
    String telefonoDistribuidor;
    String web;

    public Distribuidores() {
    }

    public Distribuidores(int distribuidorId, String nombreDistribuidor, String direccionDistribuidor, String nitDistribuidor, String telefonoDistribuidor, String web) {
        this.distribuidorId = distribuidorId;
        this.nombreDistribuidor = nombreDistribuidor;
        this.direccionDistribuidor = direccionDistribuidor;
        this.nitDistribuidor = nitDistribuidor;
        this.telefonoDistribuidor = telefonoDistribuidor;
        this.web = web;
    }

    public Distribuidores(int distribuidorId, String nombreDistribuidor, String direccionDistribuidor, String nitDistribuidor, String telefonoDistribuidor) {
        this.distribuidorId = distribuidorId;
        this.nombreDistribuidor = nombreDistribuidor;
        this.direccionDistribuidor = direccionDistribuidor;
        this.nitDistribuidor = nitDistribuidor;
        this.telefonoDistribuidor = telefonoDistribuidor;
    }
    
    

    public int getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    public String getNombreDistribuidor() {
        return nombreDistribuidor;
    }

    public void setNombreDistribuidor(String nombreDistribuidor) {
        this.nombreDistribuidor = nombreDistribuidor;
    }

    public String getDireccionDistribuidor() {
        return direccionDistribuidor;
    }

    public void setDireccionDistribuidor(String direccionDistribuidor) {
        this.direccionDistribuidor = direccionDistribuidor;
    }

    public String getNitDistribuidor() {
        return nitDistribuidor;
    }

    public void setNitDistribuidor(String nitDistribuidor) {
        this.nitDistribuidor = nitDistribuidor;
    }

    public String getTelefonoDistribuidor() {
        return telefonoDistribuidor;
    }

    public void setTelefonoDistribuidor(String telefonoDistribuidor) {
        this.telefonoDistribuidor = telefonoDistribuidor;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
    
    
}
