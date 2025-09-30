package ar.edu.iua.iwr.integration.cli2.model;

//definir todos los productos que queremos representar
public interface ProductCli2SlimView {
	
	Long getId();
	String getProduct();
	Double getPrecio();
	
	//atributo complejo 
	Category getCategory();
	//categoria solo devuelva nombre y no id
	interface Category{
		String getCategory();
	}
	
}
