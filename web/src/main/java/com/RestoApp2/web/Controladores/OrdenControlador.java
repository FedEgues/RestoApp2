
package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Servicios.CarritoServicio;
import com.RestoApp2.web.Servicios.OrdenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SELLER', 'ROLE_USER')")
@Controller
@RequestMapping("/orden")
public class OrdenControlador {
    
    @Autowired
    private CarritoServicio carritoServi;
    
    @Autowired
    private OrdenServicio ordenServi;
    
    
    
}
