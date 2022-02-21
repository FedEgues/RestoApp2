/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Entidades.Zona;
import com.RestoApp2.web.Repositorios.ZonaRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Federico
 */
@Service
public class ZonaSerivicio {

    @Autowired
    private ZonaRepositorio zR;

    @Autowired
    public void registroZona(String nombre) throws ErrorServicio {

        Zona zona = new Zona();
        zona.setNombre(nombre);
        zR.save(zona);
    }

    @Autowired
    public void actualizarZona(String id, String nombre) throws ErrorServicio {

        validacion(nombre);
        Optional<Zona> respuesta = zR.findById(id);

        if (respuesta.isPresent()) {

        }
    }

    @Autowired
    public void darBajaZona(String id) throws ErrorServicio {

        Optional<Zona> respuesta = zR.findById(id);
        if (respuesta.isPresent()) {
            Zona zona = respuesta.get();
            zona.setAlta(false);
            zR.save(zona);
        }
    }

    
    private void validacion(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la zona no puede estar vacío");
        }
    }
}
