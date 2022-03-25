/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Entidades.Carrito;
import com.RestoApp2.web.Entidades.Plato;

import com.RestoApp2.web.Repositorios.CarritoRepositorio;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Federico
 */
@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepositorio cR;
    @Autowired
    private UsuarioServicio uS;
    @Autowired
    private RestoServicio rS;
    @Autowired
    private PlatoServicio pS;

    public Carrito crearCarrito(String idResto, String idUsuario) throws ErrorServicio {
        /*Cuando el usuario entra a ver un restaurante automaticamente se crea un carrito
         este solo tiene el idResto y idUsuario, , previendo que es un usuario User, y no vamos
         a poder acceder luego de fácil al idResto
        El carrito id lo vamos a tener que ir pasando en los parametros en los botones del html
        por eso preveo solo pasar este parametro y ya dejar seteado idResto e idUsuario 
        para mejor acceso.*/
        Carrito carrito = new Carrito();
        carrito.setListaDePlatos(null);
        try {
            carrito.setUsuario(uS.buscarUsuarioPorId(idUsuario));
        } catch (ErrorServicio ex) {
            throw new ErrorServicio("El usuario no se encontró o debe estar logueado en la plataforma");
        }
        carrito.setResto(rS.buscarResto(idResto));
        cR.save(carrito);
        return carrito;
    }

    public Carrito agregarPlato(String idCarrito, String idPlato, Integer cantidad) throws ErrorServicio {
        /*Cuando el usuario agrega platos a su reserva*/
        Optional<Carrito> respuesta = cR.findById(idPlato);
        LinkedHashMap<String, Integer> platos = new LinkedHashMap();
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            platos.put(idPlato, cantidad);
            carrito.setListaDePlatos(platos);
            cR.save(carrito);
            return carrito;
        } else {
            throw new ErrorServicio("No se encontró el carrito.");
        }
    }

    public Carrito modificarCantidadPlato(String idCarrito,String idPlato,Integer nuevaCantidad)throws ErrorServicio {
     /*Este método lo pense por si el usuario quiere modificar el número de platos*/
      Optional<Carrito> respuesta = cR.findById(idPlato);
        LinkedHashMap<String, Integer> platos = new LinkedHashMap();
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            LinkedHashMap<String, Integer> lista = carrito.getListaDePlatos();
            for(Map.Entry<String,Integer> entry : lista.entrySet()){
                if (idPlato.equals(entry.getKey())) {
                    entry.setValue(nuevaCantidad);
                }
            }
            carrito.setListaDePlatos(platos);
            cR.save(carrito);
            return carrito;
        } else {
            throw new ErrorServicio("No se encontró el carrito.");
        }
    }

    public ArrayList<Plato> listaDePlatosCarrito(String idCarrito) throws ErrorServicio {
        /*Como en thymeleaf solo sabemos utilizar un foreach para una lista y no para una LinkedHashMap
        a la hora de mostrar el carrito voy a pasar dos parametros en el model map, la primer lista sera de
        los platos, que se utilizara este método.
         */
        Optional<Carrito> respuesta = cR.findById(idCarrito);
        ArrayList<Plato> platos = new ArrayList();
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            LinkedHashMap<String, Integer> lista = carrito.getListaDePlatos();
            for (String idPlato : lista.keySet()) {
                platos.add(pS.buscarPorId(idPlato));
            }
            return platos;
        } else {
            throw new ErrorServicio("No se encontró el carrito.");
        }
    }
    public ArrayList<Integer> cantidadDePlatos(String idCarrito) throws ErrorServicio{
        /*me va a arrojar la cantidad de platos, esta lista siempre tiene que ir a la par
        con la que tira el metodo listaDePlatosCarrito
        */
        Optional<Carrito> respuesta = cR.findById(idCarrito);
        ArrayList<Integer> cantidades = new ArrayList();
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            LinkedHashMap<String, Integer> lista = carrito.getListaDePlatos();
            for (Integer cantidad : lista.values()) {
                cantidades.add(cantidad);
            }
        return cantidades;
        }else{
            throw new ErrorServicio("No se encontró el carrito.");
        }
    }
    
    public void eliminarCarrito(String idCarrito) throws ErrorServicio {
        /*Esto se aplica solo cuando el usuario entra a mirar un restaurante y no le convence
         y decide salir del resto para mirar otro, asi no se crean y quedan carritos vacios.
        Esto lo pondria una vez que el usuario vuelve a la pagina principal sin hacer reserva*/
        Optional<Carrito> respuesta = cR.findById(idCarrito);
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            cR.deleteById(idCarrito);
        } else {
            throw new ErrorServicio("El carrito a eliminar no fue encontrado");
        }

    }

}
