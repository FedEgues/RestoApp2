
package com.RestoApp2.web.Repositorios;

import com.RestoApp2.web.Entidades.Plato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatoRepositorio extends JpaRepository<Plato, String>{
    @Query("SELECT p FROM Plato p WHERE p.id = :id")
    public List<Plato> buscarPlatoId(@Param("id") String id);
    
    @Query("SELECT c FROM Plato c WHERE c.alta = true")
    public List<Plato> buscarPlatosActivos();
    
    @Query("SELECT c FROM Plato c WHERE c.alta = false")
    public List<Plato> buscarPlatosInactivos();
    
    @Query("SELECT c FROM Plato c WHERE c.resto.id = :idResto and c.alta = true")
    public List<Plato> buscarPlatoResto(@Param("idResto") String idResto);
}
