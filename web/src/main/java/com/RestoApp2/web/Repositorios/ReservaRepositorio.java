
package com.RestoApp2.web.Repositorios;

import com.RestoApp2.web.Entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, String> {
    
}
