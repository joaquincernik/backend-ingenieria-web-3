package ar.edu.iua.iwr.model.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.iwr.model.Product;

@Repository
//<cual es el tipo de entidad, primary key?
public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByProduct(String product);
	
	//no esta en ninguna parte la implementacion de estos metodos, la genera JPA al momento de runtime:
	//spring lee el nombre del metodo y parse siguiendo palabras clave: findBy, and, not
	//y despues lo traduce
	Optional<Product> findByProductAndIdNot(String product,long id);
}
