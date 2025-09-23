package ar.edu.iua.iwr.integration.cli1.model;

import ar.edu.iua.iwr.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cli1_products")
@PrimaryKeyJoinColumn(name = "id_product") //asi se llama la clave principal y foranea
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCli1 extends Product {
	//Product es super clase
	// la clave principal se hereda, se crea una clave principal y es una foranea que tambien apunta a la pk principal
	
	//capaz otros sistemas tienen otro tipo de codigo, es una clave secundaria
	@Column(nullable = false, unique = true)
	private String codCli1;
}
