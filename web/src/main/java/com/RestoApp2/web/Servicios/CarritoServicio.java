package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Entidades.Carrito;
import com.RestoApp2.web.Entidades.Plato;

import com.RestoApp2.web.Repositorios.CarritoRepositorio;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;

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
    
    @Transactional
    public Carrito crearCarrito(String idResto, String idUsuario) throws ErrorServicio {
        /*Cuando el usuario entra a ver un restaurante automaticamente se crea un carrito
         este solo tiene el idResto y idUsuario, , previendo que es un usuario User, y no vamos
         a poder acceder luego de fácil forma al idResto
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
    @Transactional
    public Carrito agregarPlato(String idCarrito, String idPlato, Integer cantidad) throws ErrorServicio {
        /*Cuando el usuario agrega platos a su reserva*/
        Optional<Carrito> respuesta = cR.findById(idCarrito);
        LinkedHashMap<String, Integer> platos = new LinkedHashMap();
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            if(carrito.getListaDePlatos()== null){
            platos.put(idPlato, cantidad);
            carrito.setListaDePlatos(platos);}
            if(carrito.getListaDePlatos()!=null){
              LinkedHashMap platos1 = carrito.getListaDePlatos();
              platos1.put(idPlato,cantidad);
            }
            cR.save(carrito);
            return carrito;
        } else {
            throw new ErrorServicio("No se encontró el carrito.");
        }
    }
   @Transactional
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
    @Transactional
    public void eliminarCarritos() {
        /*Borra todos los carritos esto pasa cuando salis de algun restaurante y decidis buscar en otro.
        Como medida extra borra cualquier otro carrito ante cualquier error de la BD*/
       
        cR.deleteAll();
        

    }
    public Carrito buscarCarrito(String id)throws ErrorServicio{
        
        Optional<Carrito> respuesta = cR.findById(id);
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            return carrito;
        }else{
            throw new ErrorServicio("No se encontró el carrito");
        }
        
    }

}
