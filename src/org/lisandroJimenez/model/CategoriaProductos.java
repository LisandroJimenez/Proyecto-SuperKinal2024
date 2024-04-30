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
public class CategoriaProductos {
    int categoriaProductoId;
    String nombreCategoriaProducto;
    String descripcionCategoriaProducto;

    public CategoriaProductos() {
    }

    public CategoriaProductos(int categoriaProductoId, String nombreCategoriaProducto, String descripcionCategoriaProducto) {
        this.categoriaProductoId = categoriaProductoId;
        this.nombreCategoriaProducto = nombreCategoriaProducto;
        this.descripcionCategoriaProducto = descripcionCategoriaProducto;
    }

    public int getCategoriaProductoId() {
        return categoriaProductoId;
    }

    public void setCategoriaProductoId(int categoriaProductoId) {
        this.categoriaProductoId = categoriaProductoId;
    }

    public String getNombreCategoriaProducto() {
        return nombreCategoriaProducto;
    }

    public void setNombreCategoriaProducto(String nombreCategoriaProducto) {
        this.nombreCategoriaProducto = nombreCategoriaProducto;
    }

    public String getDescripcionCategoriaProducto() {
        return descripcionCategoriaProducto;
    }

    public void setDescripcionCategoriaProducto(String descripcionCategoriaProducto) {
        this.descripcionCategoriaProducto = descripcionCategoriaProducto;
    }

    
}
