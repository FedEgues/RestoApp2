
package com.RestoApp2.web.Controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER', 'ROLE_USER')")
@Controller
@RequestMapping("/carrito")
public class CarritoControlador {
    
//    @GetMapping("/crearCarrito")
    
}
