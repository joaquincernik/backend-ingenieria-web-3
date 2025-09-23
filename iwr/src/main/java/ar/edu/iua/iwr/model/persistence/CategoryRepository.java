package ar.edu.iua.iwr.model.persistence;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//para que sea repositorio tengo que extender
import org.springframework.stereotype.Repository;

import ar.edu.iua.iwr.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findOneByCategory(String category);
}
