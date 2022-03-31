
package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Carrito;
import com.RestoApp2.web.Entidades.Plato;
import com.RestoApp2.web.Servicios.CarritoServicio;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.PlatoServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
@GetMapping("/agregar/{idPlato}/car/{idCarrito}/res/{idResto}")
public String agregarPlatoCarrito(ModelMap modelo,@PathVariable("idResto")String idResto,@PathVariable("idPlato")String idPlato,@PathVariable("idCarrito")String idCarrito){
  
    try {
        Carrito carrito = carritoServicio.buscarCarrito(idCarrito);
        carritoServicio.agregarPlato(idCarrito,idPlato,1);
        List<Plato> platos = platoServi.listaPlatoResto(idResto);
        
        modelo.put("platos", platos);
        modelo.put("idResto",carrito.getResto().getId());
        modelo.put("carrito",carrito);
        modelo.put("exito","Se cargo el plato con éxito.");
        
        return "menu";
    } catch (ErrorServicio ex) {
        
        
        List<Plato> platos = platoServi.listaPlatoResto(idResto);
        modelo.put("error",ex.getMessage());
        modelo.put("platos", platos);
        modelo.put("idResto",idResto);
      return "menu";  
    }
    
}
    
}
