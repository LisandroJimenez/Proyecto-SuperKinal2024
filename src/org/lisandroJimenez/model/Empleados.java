/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.model;

import java.sql.Time;

/**
 *
 * @author informatica
 */
public class Empleados {
    private int empleadoId;
    private String nombre;
    private String apellido;
    private Double sueldo;
    private Time HoraEntrada;
    private Time HoraSalida;
    private int nombreCargoId;
    private int encargadoId;
    private String nombreCargo;
    private String encargado;


    public Empleados(int empleadoId, String nombre, String apellido, Double sueldo, Time HoraEntrada, Time HoraSalida, int nombreCargoId, int encargadoId) {
        this.empleadoId = empleadoId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sueldo = sueldo;
        this.HoraEntrada = HoraEntrada;
        this.HoraSalida = HoraSalida;
        this.nombreCargoId = nombreCargoId;
        this.encargadoId = encargadoId;
    }

    public Empleados(int empleadoId, String nombre, String apellido, Double sueldo, Time HoraEntrada, Time HoraSalida, String nombreCargo, String encargado) {
        this.empleadoId = empleadoId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sueldo = sueldo;
        this.HoraEntrada = HoraEntrada;
        this.HoraSalida = HoraSalida;
        this.nombreCargo = nombreCargo;
        this.encargado = encargado;
    }

    
    

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public Time getHoraEntrada() {
        return HoraEntrada;
    }

    public void setHoraEntrada(Time HoraEntrada) {
        this.HoraEntrada = HoraEntrada;
    }

    public Time getHoraSalida() {
        return HoraSalida;
    }

    public void setHoraSalida(Time HoraSalida) {
        this.HoraSalida = HoraSalida;
    }

    public int getCargoId() {
        return nombreCargoId;
    }

    public void setCargoId(int nombreCargoId) {
        this.nombreCargoId = nombreCargoId;
    }

    public int getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(int encargadoId) {
        this.encargadoId = encargadoId;
    }

    public String getCargo() {
        return nombreCargo;
    }

    public void setCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    

    

    @Override
    public String toString() {
        return "Id: " + empleadoId + " | " + nombre + " " + apellido;
    }

    
    
    
    
}
