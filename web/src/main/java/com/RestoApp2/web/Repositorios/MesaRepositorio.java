
package com.RestoApp2.web.Repositorios;

import com.RestoApp2.web.Entidades.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepositorio extends JpaRepository<Mesa, String>{
    
}
