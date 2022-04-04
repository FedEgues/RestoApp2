package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Entidades.Carrito;
import com.RestoApp2.web.Entidades.Orden;
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
    @Autowired
    private OrdenServicio oS;
    
    
    @Transactional
    public Carrito crearCarrito(String idResto, String idUsuario) throws ErrorServicio {
        /*Cuando el usuario entra a ver un restaurante automaticamente se crea un carrito
         este solo tiene el idResto y idUsuario, , previendo que es un usuario User, y no vamos
         a poder acceder luego de fácil forma al idResto
        El carrito id lo vamos a tener que ir pasando en los parametros en los botones del html
        por eso preveo solo pasar este parametro y ya dejar seteado idResto e idUsuario 
        para mejor acceso.*/
        Carrito carrito = new Carrito();
        ArrayList<String> ordenes = new ArrayList();
        carrito.setIdOrden(ordenes);
        
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
    public Carrito agregarPlato(String idCarrito,String idOrden) throws ErrorServicio {
        /*Cuando el usuario agrega platos a su reserva*/
        Optional<Carrito> respuesta = cR.findById(idCarrito);
        System.out.println("Hasta aca llego bien carrito0");
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            System.out.println("Hasta aca llego bien carrito1");
            Orden orden = oS.buscarOrden(idOrden);
            System.out.println("Hasta aca llego bien carrito2");
            ArrayList<String> ordenes = carrito.getIdOrden();
            System.out.println("Hasta aca llego bien carrito3");
            ordenes.add(orden.getId());
            System.out.println("Hasta aca llego bien carrito4");
            carrito.setIdOrden(ordenes);
            System.out.println("Hasta aca llego bien carrito5");
            System.out.println("id carrito"+carrito.getId()+"idResto"+carrito.getResto().getId()+"idUsuario"+carrito.getUsuario().getId());
            cR.save(carrito);
            System.out.println("Hasta aca llego bien carrito6");
            System.out.println("Hasta aca llego bien carrito7");
            return carrito;
        } else {
            throw new ErrorServicio("No se encontró el carrito.");
        }
    }
    
//    @Transactional
//    public Carrito modificarCantidadPlato(String idCarrito, String idPlato, Integer nuevaCantidad) throws ErrorServicio {
//        /*Este método lo pense por si el usuario quiere modificar el número de platos*/
//        Optional<Carrito> respuesta = cR.findById(idCarrito);
//        
//        if (respuesta.isPresent()) {
//            Carrito carrito = respuesta.get();
//            ArrayList<Orden> listaDeOrdenes = carrito.getOrdenes();
//            for (Orden Ordenes : listaDeOrdenes) {
//                if (Ordenes.getId().equals(idPlato)) {
//                    Ordenes.setCantidad(nuevaCantidad);
//                }
//            }
//            
//            carrito.setOrdenes(listaDeOrdenes);
//            cR.save(carrito);
//            return carrito;
//        } else {
//            throw new ErrorServicio("No se encontró el carrito.");
//        }
//    }

    /*ESTOS METODOS NO SE UTILIZAN MAS YA QUE APRENDI A RECORRER EN EL HTML LA LINKEDHASHMAP CON EL FOR EACH
//    public ArrayList<Plato> listaDePlatosCarrito(String idCarrito) throws ErrorServicio {
//        /*Como en thymeleaf solo sabemos utilizar un foreach para una lista y no para una LinkedHashMap
//        a la hora de mostrar el carrito voy a pasar dos parametros en el model map, la primer lista sera de
//        los platos, que se utilizara este método.
//         */
//        Optional<Carrito> respuesta = cR.findById(idCarrito);
//        ArrayList<Plato> platos = new ArrayList();
//        if (respuesta.isPresent()) {
//            Carrito carrito = respuesta.get();
//            LinkedHashMap<String, Integer> lista = carrito.getListaDePlatos();
//            for (String idPlato : lista.keySet()) {
//                platos.add(pS.buscarPorId(idPlato));
//            }
//            return platos;
//        } else {
//            throw new ErrorServicio("No se encontró el carrito.");
//        }
//    }
//    public ArrayList<Integer> cantidadDePlatos(String idCarrito) throws ErrorServicio{
//        /*me va a arrojar la cantidad de platos, esta lista siempre tiene que ir a la par
//        con la que tira el metodo listaDePlatosCarrito
//        */
//        Optional<Carrito> respuesta = cR.findById(idCarrito);
//        ArrayList<Integer> cantidades = new ArrayList();
//        if (respuesta.isPresent()) {
//            Carrito carrito = respuesta.get();
//            LinkedHashMap<String, Integer> lista = carrito.getListaDePlatos();
//            for (Integer cantidad : lista.values()) {
//                cantidades.add(cantidad);
//            }
//        return cantidades;
//        }else{
//            throw new ErrorServicio("No se encontró el carrito.");
//        }
//    }
    @Transactional
    public void eliminarCarritos(String idUsuario) throws ErrorServicio {
        /*Borra todos los carritos esto pasa cuando salis de algun restaurante y decidis buscar en otro.
        Borra solo el carrito del usuario logueado*/
        try {
            Carrito carrito = cR.buscarCarritoUsuario(idUsuario);
            ArrayList<String> ordenes = carrito.getIdOrden();
            for (String ordene : ordenes) {
                oS.borrarOrden(ordene);
            }
            cR.deleteById(carrito.getId());
            
        } catch (Exception e) {
            throw new ErrorServicio("no se encontro un carrito asociado a su usuario.");
        }
        
    }
    
    public Carrito buscarCarritoIdUsuario(String idUsuario) throws ErrorServicio {
        try {
            Carrito carrito = cR.buscarCarritoUsuario(idUsuario);
            return carrito;
        } catch (Exception e) {
            throw new ErrorServicio("No se encontró un carrito asociado a su usuario.");
        }
        
    }
    
    public Carrito buscarCarrito(String id) throws ErrorServicio {
        
        Optional<Carrito> respuesta = cR.findById(id);
        if (respuesta.isPresent()) {
            Carrito carrito = respuesta.get();
            return carrito;
        } else {
            throw new ErrorServicio("No se encontró el carrito");
        }
        
    }
    
}
