package org.esfe.repositorios;

import org.esfe.modelos.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProyectoRepositoy extends JpaRepository<Proyecto, Integer> {
}
