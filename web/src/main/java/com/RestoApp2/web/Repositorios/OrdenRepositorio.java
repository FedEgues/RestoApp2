
package com.RestoApp2.web.Repositorios;

import com.RestoApp2.web.Entidades.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdenRepositorio extends JpaRepository<Orden,String>{

    
}
