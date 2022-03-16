
package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Plato;
import com.RestoApp2.web.Enums.Categoria;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.PlatoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/plato")
public class PlatoControlador {
    
    @Autowired
    private PlatoServicio platoServi;
    
    @GetMapping("/editarPlato")
    public String editarPlato(ModelMap mod,@RequestParam(required = false) String id, 
                                           @RequestParam(required = false) String accion){
        if (accion == null) {
            accion = "Crear";
        }else{
            accion = "Actualizar";
        }
        
        Plato plato = new Plato();
        if (id!=null && !id.isEmpty()) {
            try{
                plato = platoServi.buscarPorId(id);
            }catch(ErrorServicio e){
                mod.addAttribute("error", e.getMessage());
            }
        }
        
        //pongo en modelo de la vista
        mod.put("editar", plato);
        mod.put("accion", accion);
        
        return "platoEditar.html";
    }
    
    @PostMapping("/actualizarPlato")
    public String actualizarPlato(ModelMap model,MultipartFile archivo, @RequestParam(required = false) String id, 
                            @RequestParam String nombre, @RequestParam String descri, 
                            @RequestParam Double precio, @RequestParam Integer categoria, String idResto){
        
       
        try{
            if (id == null) {
                platoServi.nuevoPlato(archivo, nombre, descri, precio, categoria, idResto);
            }else{
                platoServi.modificarPlato(archivo, id, nombre, precio, descri);
            }
            return "platoEditar";
        }catch(ErrorServicio e){
            //para mostrar los datos precargados
            Plato plato = new Plato();
            plato.setId(id);
            plato.setNombre(nombre);
            plato.setPrecio(precio);
            plato.setDescri(descri);
            
            model.put("accion", "Actulizar");
            model.put("error", e.getMessage());
            model.put("editar", plato);

            return "platoEditar.html";
        }
    }
    
    @GetMapping("/listarlato")
    public String listaPlato(ModelMap modelo){
        modelo.put("platos", platoServi.listaPlato());
        return "platoListar";
    }
    
    
}
