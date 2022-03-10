package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Entidades.Mesa;
import com.RestoApp2.web.Entidades.Resto;
import com.RestoApp2.web.Repositorios.MesaRepositorio;
import com.RestoApp2.web.Repositorios.RestoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MesaServicio {

    @Autowired
    private RestoRepositorio rR;
    @Autowired
    private MesaRepositorio mR;

    @Transactional
    public void crearMesa(Integer capacidad, Boolean disponible, String idResto) throws ErrorServicio {

        Mesa mesa = new Mesa();
        mesa.setCapacidad(capacidad);
        mesa.setDisponible(true);
        Optional<Resto> respuesta = rR.findById(idResto);
        if (respuesta.isPresent()) {
            Resto resto = respuesta.get();
            mesa.setResto(resto);
        } else {
            throw new ErrorServicio("No se encontro el restaurante al cuál quiere asociar su mesa");
        }
        mR.save(mesa);
    }
    
    @Transactional
    public void modificarMesa(String idMesa, Integer capacidad)throws ErrorServicio{
        
        Optional<Mesa> respuesta = mR.findById(idMesa);
        if (respuesta.isPresent()) {
            Mesa mesa = respuesta.get();
            mesa.setCapacidad(capacidad);
            mR.save(mesa);
        }else{
            throw new ErrorServicio("No se encontro la mesa a modificar");
        }
    }
    @Transactional
    public void darBajaMesa(String idMesa)throws ErrorServicio{
        
        Optional<Mesa> respuesta = mR.findById(idMesa);
        if(respuesta.isPresent()){
            Mesa mesa = respuesta.get();
            mesa.setDisponible(false);
            mR.save(mesa);
        }else{
            throw new ErrorServicio("No se encontro la mesa a modificar");
        }
        
    }
    
}
