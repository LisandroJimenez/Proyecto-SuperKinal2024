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
    private String Empleado;
    private Double sueldo;
    private Time HoraEntrada;
    private Time HoraSalida;
    private int cargoId;
    private int encargadoId;
    private String cargo;
    private String encargado;

    public Empleados(int empleadoId, String Empleado, Double sueldo, Time HoraEntrada, Time HoraSalida, int cargoId, int encargadoId) {
        this.empleadoId = empleadoId;
        this.Empleado = Empleado;
        this.sueldo = sueldo;
        this.HoraEntrada = HoraEntrada;
        this.HoraSalida = HoraSalida;
        this.cargoId = cargoId;
        this.encargadoId = encargadoId;
    }

    public Empleados(int empleadoId, String Empleado, Double sueldo, Time HoraEntrada, Time HoraSalida, String cargo, String encargado) {
        this.empleadoId = empleadoId;
        this.Empleado = Empleado;
        this.sueldo = sueldo;
        this.HoraEntrada = HoraEntrada;
        this.HoraSalida = HoraSalida;
        this.cargo = cargo;
        this.encargado = encargado;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(String Empleado) {
        this.Empleado = Empleado;
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
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public int getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(int encargadoId) {
        this.encargadoId = encargadoId;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    @Override
    public String toString() {
        return "Id: " + empleadoId + " | " + Empleado;
    }

    
    
    
    
}
