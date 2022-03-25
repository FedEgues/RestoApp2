/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Entidades;

import java.io.Serializable;
import java.util.LinkedHashMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Federico
 */

@Entity
public class Carrito implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private LinkedHashMap<String,Integer> listaDePlatos;
    
    @ManyToOne
    private Resto resto;
    @ManyToOne
    private Usuario usuario;

    public String getId() {
        return id;
    }

  

    public LinkedHashMap<String, Integer> getListaDePlatos() {
        return listaDePlatos;
    }

    public void setListaDePlatos(LinkedHashMap<String, Integer> listaDePlatos) {
        this.listaDePlatos = listaDePlatos;
    }

    public Resto getResto() {
        return resto;
    }

    public void setResto(Resto resto) {
        this.resto = resto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
