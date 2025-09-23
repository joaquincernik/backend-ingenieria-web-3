package ar.edu.iua.iwr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//cuando empieza con arroba son anotaciones en runtime se transforam ene codigo
//le tengo que decir a jpa que tiene que ser persistente
//lo obligaorio es el entity y definir clave primaria
@Entity
@Table(name = "products")


//para que herede a la cli 1
@Inheritance(strategy = InheritanceType.JOINED)
//single table -> solo agrega una columna, se me llena de nulls no sirrve
// table por clase -> una tabla para productos, ora para prod cli
//joined -> es la que va, crea una fk, las une y crea objetos 

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//cada nombre de los productos unicos, es una clave secundria y crea un indice
	@Column(length = 100, unique = true)
	private String product;
	

	@Column(columnDefinition = "tinyint default 0")
	private boolean stock = false;
	private double precio;
	
	@ManyToOne
	//desde el punto de vista de productos es un uno a muchos
	@JoinColumn(name="id_category", nullable = true) //da mas detalles, clave foranea, 
	private Category category;
}
