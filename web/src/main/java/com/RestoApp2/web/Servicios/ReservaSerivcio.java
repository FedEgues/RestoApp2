
package com.RestoApp2.web.Servicios;

import com.RestoApp2.web.Repositorios.ReservaRepositorio;
import java.io.Serializable;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservaSerivcio implements Serializable {
    
    
    @Autowired
    private ReservaRepositorio reservaR;
    
    @Transactional
    public void crearReserva(){
        
    }
}
