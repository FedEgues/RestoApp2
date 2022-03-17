package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Plato;
import com.RestoApp2.web.Enums.Categoria;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.PlatoServicio;
import javax.websocket.server.PathParam;
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
        modelo.put("platos", platoServi.listaPlato());
        return "platoListar";
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

//    @PostMapping("/modificarPlato")
//    public String modificarPlato(){
//
//    }
}
