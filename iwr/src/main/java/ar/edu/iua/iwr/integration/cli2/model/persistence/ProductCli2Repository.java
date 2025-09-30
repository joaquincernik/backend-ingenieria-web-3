package ar.edu.iua.iwr.integration.cli2.model.persistence;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.iwr.integration.cli2.model.ProductCli2;
import ar.edu.iua.iwr.integration.cli2.model.ProductCli2SlimView;

@Repository
public interface ProductCli2Repository extends JpaRepository<ProductCli2, Long> {
	//todos los productos que hayan expirado desdpues de una fecha
	//orden by el que se venda mas rapido
	public List<ProductCli2> findByExpirationDateBeforeOrderByExpirationDateDesc(Date expirationDate);
	
	//pryectar es decidir que atributos se seleccionan como parte de la consulta sql
	//cuando hago select * elegimos todo
	public List<ProductCli2SlimView> findByOrderByPrecioDesc();
}