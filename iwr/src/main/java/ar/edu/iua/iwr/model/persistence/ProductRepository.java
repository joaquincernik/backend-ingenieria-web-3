package ar.edu.iua.iwr.model.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.iua.iwr.model.Product;
import jakarta.transaction.Transactional;

@Repository
//<cual es el tipo de entidad, primary key?
public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByProduct(String product);
	
	//no esta en ninguna parte la implementacion de estos metodos, la genera JPA al momento de runtime:
	//spring lee el nombre del metodo y parse siguiendo palabras clave: findBy, and, not
	//y despues lo traduce
	Optional<Product> findByProductAndIdNot(String product,long id);
	
	//en vez de trabajar por conuslta por nombre escribimos nuestra propia consulta
	@Query(value = "SELECT count(*) FROM products where id_category=?", nativeQuery = true) //constula personalizada
	public Integer countProductsByCategory(long idCategory);
	
	@Transactional
    @Modifying //es la que indica que los datos se van a modificar
    @Query(value = "UPDATE products SET stock=? WHERE id=?", nativeQuery = true) //establece el valor de stock para un producto  en particular
	public int setStock(boolean stock, long idProduct);

}
