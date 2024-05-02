/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lisandroJimenez.dto;

import org.lisandroJimenez.model.CategoriaProductos;

/**
 *
 * @author Usuario
 */
public class CategoriaProductoDTO {
    private static CategoriaProductoDTO instance;
    private CategoriaProductos categoriaProducto;
    
    private CategoriaProductoDTO(){
    
    }
    
    public static CategoriaProductoDTO getCategoriaProductoDTO(){
        if (instance == null){
            instance = new CategoriaProductoDTO();
        }
        return instance;
    }

    public CategoriaProductos getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProductos categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
}
