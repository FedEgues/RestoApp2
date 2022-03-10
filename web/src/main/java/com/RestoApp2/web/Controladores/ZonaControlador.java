/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Zona;
import com.RestoApp2.web.Repositorios.ZonaRepositorio;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.ZonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Federico
 */
@Controller
@RequestMapping("/zona")
public class ZonaControlador {

    @Autowired
    private ZonaServicio zS;
    @Autowired
    private ZonaRepositorio zR;

    @GetMapping("/crearZona")
    public String creaZona() {

        return "zonaCrear.html";
    }

    @PostMapping("/persistirZona")
    public String Zona(ModelMap modelo, @RequestParam String nombre) {

        try {
            zS.registroZona(nombre);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
        return "zonaCrear";
        }
        modelo.put("exito","La zona fue ingresada con éxito.");
        return "zonaCrear";
    }

    @PostMapping("/actualizarZona")
    public String acutalizarZona(ModelMap modelo, @RequestParam(required = false) String idZona, @RequestParam String nombre) {

        try {
            if (idZona == null || idZona.isEmpty()) {
                zS.registroZona(nombre);
            } else {
                zS.actualizarZona(idZona, nombre);
            }
            return "zonaEditar.html";
        } catch (ErrorServicio e) {
            Zona zona = new Zona();
            zona.setNombre(nombre);
            modelo.put("editar", zona);
            modelo.put("Accion", "Actualizar");
            modelo.put("error", e.getMessage());
            modelo.put("exito", "Zona actualizada con éxito");
            return "zonaEditar.html";
        }
    }
}
