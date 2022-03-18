package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Mesa;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.MesaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mesa")
public class MesaControlador {

    @Autowired
    private MesaServicio mesaServi;

    @GetMapping("/crearMesa")
    public String crearMesa(ModelMap modelo) {
        return "mesaCrear";
    }

    @PostMapping("/guardarMesa")
    public String guardarMesa(ModelMap modelo, @RequestParam Integer capacidad, @RequestParam String idResto) {
        try {
            mesaServi.crearMesa(capacidad, idResto);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "crearMesa";
        }
        modelo.put("exito", "La mesa fue ingresada con éxito.");
        return "mesaCrear";

    }
    
    @GetMapping("/listarMesaResto/{idResto}")
    public String listarMesaResto(ModelMap modelo ,@PathVariable("idResto") String idResto){
        List<Mesa> mesas = mesaServi.listarMesaResto(idResto);
        modelo.put("mesas", mesas);
        return "mesaListar";
    }
    
    @GetMapping("/listarMesasInactivas")
    public String listaMesasInactivas(ModelMap modelo) {        
        List<Mesa> mesas = mesaServi.buscarMesasInactivas();
        modelo.put("mesas", mesas);
        return "mesaListarInactivas";
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable("id") String id, ModelMap model){
        try{
            mesaServi.bajaMesa(id);
        }catch(ErrorServicio e){
            model.put("error", e.getMessage());
             return "mesaListar";
        }
        model.put("exito", "Mesa dada de baja correctamente");
        return "mesaListar";
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable("id") String id, ModelMap model){
        try{
            mesaServi.altaMesa(id);
        }catch(ErrorServicio e){
            model.put("error", e.getMessage());
             return "mesaListarInactivas";
        }
        model.put("exito", "Mesa dada de alta correctamente");
        return "mesaListarInactivas";
    }
}
