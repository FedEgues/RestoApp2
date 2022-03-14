/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.UsuarioServicio;
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
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio uS;

    @GetMapping("/usuarioUser")
    public String usuarioUser() {
        return "registroUsuario.html";
    }

    @PostMapping("/registroUsuario")
    public String registroUsuario(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2) {

        try {
            uS.registroUsuarioUsuario(nombre, apellido, email, clave1, clave2);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            return "registroUsuario.html";
        }
        modelo.put("exito", "Su usuario fue registrado con éxito");
        return "registroUsuario.html";

    }

    @GetMapping("/usuarioSeller")
    public String usuarioResto() {
        return "registroUsuarioResto.html";
    }

    @PostMapping("/registroUsuarioResto")
    public String registroUsuarioResto(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2) {

        try {
            uS.registroRestoUsuario(nombre, apellido, email, clave1, clave2);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            return "registroUsuarioResto.html";
        }
        modelo.put("exito", "Su usuario fue registrado con éxito");
        return "registroUsuarioResto.html";

    }

}
