/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Controladores;

import com.RestoApp2.web.Servicios.ErrorServicio;
import com.RestoApp2.web.Servicios.MesaServicio;
import com.RestoApp2.web.Servicios.RestoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Federico
 */
@Controller
@RequestMapping("/mesa")
public class MesaControlador {
    
    @Autowired
    private MesaServicio mS;
    @Autowired
    private RestoServicio rS;
    
    @GetMapping("/crearMesa")
    public String crearMesa(ModelMap modelo){
//        try{ esto lo comento hasta que veamos la mejor forma de crear la mesa y pasar el id del resto
//        modelo.put("idResto",rS.buscarResto(idResto));
//        }catch(ErrorServicio e){
//         modelo.put("error",e.getMessage());
//         
//                }
        return "mesaCrear.html";
    }
    
    @PostMapping("/persistirMesa")
    public String persistirMesa(ModelMap modelo,@RequestParam String idResto,@RequestParam Integer capacidad){
        
        try{
        mS.crearMesa(capacidad, Boolean.TRUE, idResto);
        }catch(ErrorServicio e){
               modelo.put("error",e.getMessage());
              return "crearMesa.html";
                }
        modelo.put("exito","La mesa fue ingresada con éxito.");
        return "mesaCrear.html";
        
    }
}
