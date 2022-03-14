/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Repositorios.ReservaRepositorio;
import java.io.Serializable;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Federico
 */
@Service
public class ReservaSerivcio implements Serializable {
    
    
    @Autowired
    private ReservaRepositorio reservaR;
    
    @Transactional
    public void crearReserva(){
        
    }
}
