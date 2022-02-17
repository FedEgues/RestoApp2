/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Federico
 */
@Entity
public class Foto implements Serializable {
    
    
       
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")    
    private String id;
    private String mime;
   
    
    
    @Lob //identifica que el tipo de dato es pesado
    @Basic (fetch = FetchType.LAZY) //carga el contenido solo cuando lo pedimos
    private byte [] contenido;    

    public Foto() {
    }

    public Foto(String id, String mime, byte[] contenido) {
        this.id = id;
        this.mime = mime;
        this.contenido = contenido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

}
