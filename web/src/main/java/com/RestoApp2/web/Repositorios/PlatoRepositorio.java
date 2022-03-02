
package com.RestoApp2.web.Repositorios;

import com.RestoApp2.web.Entidades.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatoRepositorio extends JpaRepository<Plato, String>{
    @Query("SELECT p FROM Plato p WHERE p.id = :id")
    public Plato buscarPlatoId(@Param("id") String id);
}
