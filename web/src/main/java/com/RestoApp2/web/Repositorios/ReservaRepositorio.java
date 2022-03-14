/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RestoApp2.web.Repositorios;

import com.RestoApp2.web.Entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Federico
 */
@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, String> {
    
}
