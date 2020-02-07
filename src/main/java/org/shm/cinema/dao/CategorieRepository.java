package org.shm.cinema.dao;

import org.shm.cinema.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource // spring data
@CrossOrigin("*")
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
