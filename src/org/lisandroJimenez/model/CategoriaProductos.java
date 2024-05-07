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
    private int categoriaProductosId;
    private String nombreCategoria;
    private String descripcionCategoria;

    public CategoriaProductos() {
    }

    public CategoriaProductos(int categoriaProductosId, String nombreCategoria, String descripcionCategoria) {
        this.categoriaProductosId = categoriaProductosId;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public int getCategoriaProductosId() {
        return categoriaProductosId;
    }

    public void setCategoriaProductosId(int categoriaProductosId) {
        this.categoriaProductosId = categoriaProductosId;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    @Override
    public String toString() {
        return "Id: " + categoriaProductosId + " | " + nombreCategoria;
    }

    
}
