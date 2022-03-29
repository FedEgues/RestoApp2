
package com.RestoApp2.web.Repositorios;

import com.RestoApp2.web.Entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Federico
 */
@Repository
public interface CarritoRepositorio extends JpaRepository<Carrito,String> {
 
}
