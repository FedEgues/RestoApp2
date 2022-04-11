package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Carrito;
import com.RestoApp2.web.Entidades.Orden;
import com.RestoApp2.web.Entidades.Plato;
import com.RestoApp2.web.Servicios.CarritoServicio;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.OrdenServicio;
import com.RestoApp2.web.Servicios.PlatoServicio;
import com.RestoApp2.web.Servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER', 'ROLE_USER')")
@Controller
@RequestMapping("/carrito")
public class CarritoControlador {

    @Autowired
    CarritoServicio carritoServicio;
    @Autowired
    PlatoServicio platoServi;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    OrdenServicio ordenServicio;

    @GetMapping("/agregar/{idPlato}/{idCarrito}")
    public String agregarPlatoCarrito(ModelMap modelo, @PathVariable("idPlato") String idPlato, @PathVariable("idCarrito") String idCarrito) {
        System.out.println("HASTA ACA LLEGO BIEN?");
        try {            
            Carrito carrito = carritoServicio.buscarCarrito(idCarrito);         
            Orden orden = ordenServicio.crearOrden(idPlato, 1);           
            carrito = carritoServicio.agregarPlato(idCarrito, orden.getId());
          
            List<Plato> platos = platoServi.listaPlatoResto(carrito.getResto().getId());
            
            modelo.put("platos", platos);
            modelo.put("idResto", carrito.getResto().getId());
            modelo.put("carritoId", carrito.getId());
            modelo.put("exito", "Se cargo el plato con éxito.");

            return "menu";
        } catch (ErrorServicio ex) {
            Carrito carrito;
            try {
                carrito = carritoServicio.buscarCarrito(idCarrito);
                List<Plato> platos = platoServi.listaPlatoResto(carrito.getResto().getId());
                modelo.put("error", ex.getMessage());
                modelo.put("platos", platos);
                modelo.put("idResto", carrito.getResto().getId());
                modelo.put("carritoId", carrito.getId());
                return "menu";
            } catch (ErrorServicio ex1) {
                modelo.put("error", ex1.getMessage());
                return "index";
            }
        }
    }

    @GetMapping("/verCarrito/{id}")
    public String listaCarrito(@PathVariable("id") String id, ModelMap modelo) {
        try {
            Carrito carrito = carritoServicio.buscarCarritoIdUsuario(id);
            String idResto = carrito.getResto().getId();
            String idCarrito = carrito.getId();
            ArrayList<String> IdOrdenes = carrito.getIdOrden();
            ArrayList<Orden> ordenes = new ArrayList();
            for (String IdOrdene : IdOrdenes) {
                Orden orden = ordenServicio.buscarOrden(IdOrdene);
                ordenes.add(orden);
            }
            modelo.put("listaOrdenes", ordenes);
            modelo.put("idResto", idResto);
            modelo.put("idCarrito", idCarrito); //no me mostraba el carrito xq faltaba esta linea.
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
        }
        return "carrito";
    }
    
    @GetMapping("/eliminar/{idOrden}")
    public String bajaPlato(@PathVariable ("idOrden") String idOrden, ModelMap model){
        try{
            ordenServicio.borrarOrden(idOrden);
        }catch(ErrorServicio e){
            model.put("error", e.getMessage());
            
        }
        model.put("exito", "Orden eliminada con exito");
        return "menu";
    }
    
}
