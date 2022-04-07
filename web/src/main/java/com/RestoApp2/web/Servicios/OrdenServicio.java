
package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Entidades.Orden;
import com.RestoApp2.web.Repositorios.CarritoRepositorio;
import com.RestoApp2.web.Repositorios.OrdenRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrdenServicio {

    @Autowired
    private CarritoRepositorio carritoRepo;
    @Autowired
    private PlatoServicio platoServicio;
    @Autowired
    private OrdenRepositorio ordenRepositorio;

    @Transactional
    public Orden crearOrden(String idPlato, Integer cantidad) throws ErrorServicio {

        
        Orden orden = new Orden();
        orden.setPlato(platoServicio.buscarPorId(idPlato));
        orden.setCantidad(cantidad);
        ordenRepositorio.save(orden);
       
        return orden;
    }

    public Orden buscarOrden(String idOrden) throws ErrorServicio {

        Optional<Orden> respuesta = ordenRepositorio.findById(idOrden);
        if (respuesta.isPresent()) {
            Orden orden = respuesta.get();
            return orden;
        } else {
            throw new ErrorServicio("No se encontro la orden.");
        }

    }
    public void borrarOrden(String id)throws ErrorServicio{
        
       try{
           Orden orden = ordenRepositorio.getById(id);
           ordenRepositorio.delete(orden);
       }catch(Exception e){
        throw new ErrorServicio("No se encontro la orden buscada.");
      }
    }
    
    @Transactional
    public void bajaOrden(String idOrden) throws ErrorServicio {
        Optional<Orden> rta = ordenRepositorio.findById(idOrden);
        if (rta.isPresent()) {
            Orden orden = rta.get();
            orden.setCantidad(0);
            ordenRepositorio.save(orden);
        }else{
            throw new ErrorServicio("Orden NO ENCOMTRADA");
        }
        
    }
}
