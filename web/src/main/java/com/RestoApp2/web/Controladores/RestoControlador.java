
package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Resto;
import com.RestoApp2.web.Entidades.Usuario;
import com.RestoApp2.web.Repositorios.RestoRepositorio;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.PlatoServicio;
import com.RestoApp2.web.Servicios.RestoServicio;
import com.RestoApp2.web.Servicios.ZonaServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author Federico
 */
@PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN')")
@Controller
@RequestMapping("/resto")
public class RestoControlador {
  
    @Autowired
    private RestoServicio rS;
    @Autowired
    private ZonaServicio zS;
    @Autowired
    private RestoRepositorio rR;
    
    @Autowired
    private PlatoServicio platoServi;
                  
    @GetMapping("/modificarResto")
    public String crearResto(ModelMap model,HttpSession session){
        
        Usuario usuario = (Usuario)session.getAttribute("usuariosession");
        Resto resto =  rR.getById(usuario.getId());
        
        
        model.put("resto",resto); 
        model.put("zonas",zS.listarZonas());
        
        return "restoModificar.html";
    }
    
    @PostMapping("/restoModificado")
    public String crearResto(HttpSession session,ModelMap model,MultipartFile archivo,@RequestParam String idUsuario,@RequestParam String nombre,@RequestParam String idZona){
        Usuario usuario = (Usuario)session.getAttribute("usuariosession");
        Resto resto =  rR.getById(usuario.getId());
      
        try{
            rS.modificarResto(idUsuario,nombre, idZona, archivo, Boolean.TRUE);
        }catch(ErrorServicio e){
            model.put("error",e.getMessage());
            
           
            model.put("zonas",zS.listarZonas());
             model.put("resto",resto);  
            return "restoModificar.html";
        }
        model.put("exito","El restaurant modificado con éxito.");
        
        return "index.html";
        
    }
    
    @GetMapping("/verPlatos/{id}")
    public String verPlatos(@PathVariable("id") String id, ModelMap model){        
        model.put("platos", platoServi.listaPlatoResto(id));
        return "resto1";
    }
    
    
}
