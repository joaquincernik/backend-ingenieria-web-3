package ar.edu.iua.iwr.integration.cli2.model.business;


import java.util.Date;
import java.util.List;

import ar.edu.iua.iwr.integration.cli2.model.ProductCli2;
import ar.edu.iua.iwr.model.business.BusinessException;


public interface IProductCli2Business {
	public List<ProductCli2> listExpired(Date date) throws BusinessException;
}
