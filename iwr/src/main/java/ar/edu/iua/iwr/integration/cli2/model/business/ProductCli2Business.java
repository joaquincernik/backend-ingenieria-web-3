package ar.edu.iua.iwr.integration.cli2.model.business;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.iwr.integration.cli2.model.ProductCli2;
import ar.edu.iua.iwr.integration.cli2.model.ProductCli2SlimView;
import ar.edu.iua.iwr.integration.cli2.model.persistence.ProductCli2Repository;
import ar.edu.iua.iwr.model.business.BusinessException;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProductCli2Business implements IProductCli2Business {

	@Autowired(required = false)
	private ProductCli2Repository productDAO;

	@Override
	public List<ProductCli2> listExpired(Date date) throws BusinessException {
		try {
			return productDAO.findByExpirationDateBeforeOrderByExpirationDateDesc(date);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}
	
	@Override
	public List<ProductCli2SlimView> listSlim() throws BusinessException {
		try {
			//la salida va a ser por consola nomas
			return productDAO.findByOrderByPrecioDesc();
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}


}