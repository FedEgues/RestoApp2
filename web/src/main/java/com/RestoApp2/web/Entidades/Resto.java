/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Federico
 */
@Entity
public class Resto implements Serializable{
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private Zona zona;
    @OneToMany
    private Plato plato;
    @OneToOne
    private Foto foto;
    @OneToMany
    private Mesa mesa;
    @OneToMany
    private Reserva reserva;
    private Boolean abierto;

    public Resto() {
    }

    public Resto(String id, String nombre, Zona zona, Plato plato, Foto foto, Mesa mesa, Reserva reserva, Boolean abierto) {
        this.id = id;
        this.nombre = nombre;
        this.zona = zona;
        this.plato = plato;
        this.foto = foto;
        this.mesa = mesa;
        this.reserva = reserva;
        this.abierto = abierto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Boolean getAbierto() {
        return abierto;
    }

    public void setAbierto(Boolean abierto) {
        this.abierto = abierto;
    }
    
    
    
}
