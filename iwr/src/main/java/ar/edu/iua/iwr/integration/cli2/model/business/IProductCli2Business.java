package ar.edu.iua.iwr.integration.cli2.model.business;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import ar.edu.iua.iwr.integration.cli2.model.ProductCli2;
import ar.edu.iua.iwr.integration.cli2.model.ProductCli2SlimView;
import ar.edu.iua.iwr.model.business.BusinessException;
import ar.edu.iua.iwr.model.business.FoundException;


public interface IProductCli2Business {
	public List<ProductCli2> listExpired(Date date) throws BusinessException;
	public List<ProductCli2SlimView> listSlim() throws BusinessException;
	//va a ser nuestro add, recibo un json
	
	public List<ProductCli2> listByPrice(Double start, Double end) throws BusinessException;

	
	public ProductCli2 add(ProductCli2 product) throws FoundException, BusinessException;
	public ProductCli2 addExternal(String json) throws FoundException, BusinessException;
}
