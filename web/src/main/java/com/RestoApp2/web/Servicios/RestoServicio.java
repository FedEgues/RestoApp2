/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Entidades.Resto;
import com.RestoApp2.web.Entidades.Zona;
import com.RestoApp2.web.Repositorios.RestoRepositorio;
import com.RestoApp2.web.Repositorios.ZonaRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Federico
 */
@Service
public class RestoServicio {
    
    @Autowired
    private ZonaRepositorio zR;
    @Autowired
    private RestoRepositorio rR;
    @Autowired
    private FotoRepositorio fR;
   
    
    @Transactional
    public void registroResto(String nombre,String idZona,MultipartFile foto,Boolean abierto)throws ErrorServicio{
        validacion(nombre,idZona);
        
        Resto resto = new Resto();
        
        
        resto.setNombre(nombre);
        
        Optional<Zona> respuesta =zR.findById(idZona);
        if(respuesta.isPresent()){
            Zona zona = respuesta.get();
            resto.setZona(zona);
        }
        Foto foto = fR.
        /*Dejo aca voy a necesitar para seguir foto, plato,mesa*/
    }
    
    
    private void validacion(String nombre,String idZona)throws ErrorServicio{
        
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío");
        }
        /*No estoy seguro de esto, si la zona no esta presente tendria que tirar ese error(pero como con el html
        si o si vamos a traer una id de alguna zona si no va a ser posible visualizarla en el desplegable
        ,esto puede servir solamente en el caso que queramos guardar un resto y no haber creado las zonas antes supongo
        y no nos permita poner zona null*/
        Optional<Zona> respuesta =zR.findById(idZona);
        if (!respuesta.isPresent()) {
            throw new ErrorServicio("La zona seleccionada no se encontró");
        }
    }
}
