/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Entidades.Zona;
import com.RestoApp2.web.Repositorios.ZonaRepositorio;
import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.RestoServicio;
import com.RestoApp2.web.Servicios.ZonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author Federico
 */
@Controller
@RequestMapping("/resto")
public class RestoControlador {
  
    @Autowired
    private RestoServicio rS;
    @Autowired
    private ZonaServicio zS;
    
                  
    @GetMapping("/crearResto")
    public String crearResto(ModelMap model){
        model.put("zonas",zS.listarZonas());
     
        return "restoCrear.html";
    }
    
    @PostMapping("/persistirResto")
    public String crearResto(ModelMap model,MultipartFile archivo,@RequestParam String idUsuario,@RequestParam String nombre,@RequestParam String idZona){
        
        try{
            rS.registroResto(idUsuario,nombre, idZona, archivo, Boolean.TRUE);
        }catch(ErrorServicio e){
            model.put("Error",e.getMessage());
            model.put("nombre",nombre);
            model.put("idZona",idZona);
            model.put("zonas",zS.listarZonas());
            return "restoCrear.html";
        }
        model.put("exito","El restaurant fue creado con éxito.");
        model.put("zonas",zS.listarZonas());
        return "restoInicio.html";
        
    }
    
    
}
