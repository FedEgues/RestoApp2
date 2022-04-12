package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Carrito;
import com.RestoApp2.web.Entidades.Orden;
import com.RestoApp2.web.Entidades.Usuario;
import com.RestoApp2.web.Servicios.CarritoServicio;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.OrdenServicio;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN','ROLE_USER')")
@Controller
@RequestMapping("/reserva")
public class ReservaControlador {

    @Autowired
    private CarritoServicio carritoServicio;
    @Autowired
    private OrdenServicio ordenServicio;
   

    @GetMapping("/crearReserva/{carritoId}")
    public String crearReserva(HttpSession session, ModelMap model, @PathVariable("carritoId") String carritoId) {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        try {

            Carrito carrito = carritoServicio.buscarCarrito(carritoId);
            if (!login.getId().equals(carrito.getUsuario().getId())) {
                /*Para verificar que el carrito es del mismo del usuario que esta en la pagina*/
                return "index.html";
            }
                String idResto = carrito.getResto().getId();
                String nombreResto = carrito.getResto().getNombre();
                String idCarrito = carrito.getId();
                ArrayList<String> IdOrdenes = carrito.getIdOrden();
                ArrayList<Orden> ordenes = new ArrayList();
                Double total=0.0;
                for (String IdOrdene : IdOrdenes) {
                    Orden orden = ordenServicio.buscarOrden(IdOrdene);
                    ordenes.add(orden);
                    total = total+orden.getPlato().getPrecio();
                }
                model.put("total",total);
                model.put("listaOrdenes", ordenes);
                model.put("idResto", idResto);
                model.put("nombreResto",nombreResto);
                model.put("idCarrito", idCarrito); //no me mostraba el carrito xq faltaba esta linea.
           
                return "reservaNuevo.html";
 
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            
            return "menu.html";
        }
         
    }
}