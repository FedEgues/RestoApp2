/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Entidades.Zona;
import com.RestoApp2.web.Repositorios.ZonaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Federico
 */
@Service
public class ZonaServicio {

    @Autowired
    private ZonaRepositorio zR;

    @Transactional
    public void registroZona(String nombre) throws ErrorServicio {

        Zona zona = new Zona();
        zona.setNombre(nombre);
        zona.setAlta(true);
        zR.save(zona);
    }

    @Transactional
    public void actualizarZona(String id, String nombre) throws ErrorServicio {

        validacion(nombre);
        Optional<Zona> respuesta = zR.findById(id);

        if (respuesta.isPresent()) {
         Zona zona =respuesta.get();
         zona.setNombre(nombre);
         
         zR.save(zona);
        }
    }

    @Transactional
    public void darBajaZona(String id) throws ErrorServicio {

        Optional<Zona> respuesta = zR.findById(id);
        if (respuesta.isPresent()) {
            Zona zona = respuesta.get();
            zona.setAlta(false);
            zR.save(zona);
        }
    }
    
     public Zona buscarPorId(String id) throws ErrorServicio{
        Optional<Zona> rta = zR.findById(id);
        if (rta.isPresent()) {
            Zona zona = rta.get();
            return zona;
        }else{
            throw new ErrorServicio("Zona no encontrada");
        }
    }
    public List<Zona> listarZonas(){
        
       return zR.findAll();
    }

    
    private void validacion(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la zona no puede estar vacío");
        }
    }
}
