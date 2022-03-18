package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Plato;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.PlatoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/plato")
public class PlatoControlador {

    @Autowired
    private PlatoServicio platoServi;

    @GetMapping("/crearPlato")
    public String crearPlato() {
        return "platoCrear";
    }

    @PostMapping("/guardarPlato")
    public String guardarPlato(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre,
            @RequestParam Double precio, @RequestParam String descri,
            @RequestParam Integer categoria, @RequestParam String idResto) {
        try {
            platoServi.nuevoPlato(archivo, nombre, descri, precio, categoria, idResto);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("precio", precio);
            modelo.put("descri", descri);
            return "platoCrear";
        }
        modelo.put("exito", "Plato creado con exito");
        return "platoCrear";
    }

    @GetMapping("/listarPlato")
    public String listaPlato(ModelMap modelo) {        
        List<Plato> platos = platoServi.buscarPlatosActivos();
        modelo.put("platos", platos);
        return "platoListar.html";
    }
    
    @GetMapping("/listarPlatoInactivos")
    public String listaPlatoInactivos(ModelMap modelo) {        
        List<Plato> platos = platoServi.buscarPlatosInactivos();
        modelo.put("platos", platos);
        return "platoListarInactivos.html";
    }

    @GetMapping("/modPlato/{id}")
    public String modPlato(@PathVariable("id") String id, ModelMap model) {
        try {
            Plato plato = platoServi.buscarPorId(id);
            model.put("plato", plato);
            model.put("id", id);
            model.put("nombre1", plato.getNombre());
            model.put("precio1", plato.getPrecio());
            model.put("descri1", plato.getDescri());
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
        }
        return "platoModificar";
    }

    @PostMapping("/modificarPlato")
    public String modifiPlato(ModelMap model, MultipartFile archivo, @RequestParam String idPlato, 
                            @RequestParam String nombre, @RequestParam Double precio, 
                            @RequestParam String descri) throws ErrorServicio {
        try {
            platoServi.modificarPlato(archivo, idPlato, nombre, precio, descri);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            model.put("precio", precio);
            model.put("descri", descri);
            return "platoModificar";
            //en el form poner th:value="${nombre.variable}" para conservar los datos l
        }
        model.put("exito", "El plato fue modificado con éxito");
        return "redirect:/";
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable("id") String id, ModelMap model){
        try{
            platoServi.bajaPlato(id);
        }catch(ErrorServicio e){
            model.put("error", e.getMessage());
             return "redirect:/plato/listarPlato";
        }
        model.put("exito", "Plato dado de baja correctamente");
        return "redirect:/";
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable("id") String id, ModelMap model){
        try{
            platoServi.altaPlato(id);
        }catch(ErrorServicio e){
            model.put("error", e.getMessage());
             return "redirect:/plato/listarPlatoInactivos";
        }
        model.put("exito", "Plato dado de baja correctamente");
        return "redirect:/plato/listarPlato";
    }
}
