/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.dto;

import org.lisandroJimenez.model.Cargos;

/**
 *
 * @author Usuario
 */
public class CargoDTO {
    private static CargoDTO instance;
    private Cargos cargo;
    
    private CargoDTO(){
    
    }
    
    public static CargoDTO getCargoDTO(){
        if (instance == null){
            instance = new CargoDTO();
        }
        return instance;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    
}
