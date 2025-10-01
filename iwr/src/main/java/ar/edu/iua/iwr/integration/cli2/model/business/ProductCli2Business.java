package ar.edu.iua.iwr.integration.cli2.model.business;

import java.io.IOException;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.iua.iwr.integration.cli2.model.ProductCli2;
import ar.edu.iua.iwr.integration.cli2.model.ProductCli2JsonDeserializer;
import ar.edu.iua.iwr.integration.cli2.model.ProductCli2SlimView;
import ar.edu.iua.iwr.integration.cli2.model.persistence.ProductCli2Repository;
import ar.edu.iua.iwr.model.business.BusinessException;
import ar.edu.iua.iwr.model.business.FoundException;
import ar.edu.iua.iwr.model.business.ICategoryBusiness;
import ar.edu.iua.iwr.model.business.IProductBusiness;
import ar.edu.iua.iwr.model.business.NotFoundException;
import ar.edu.iua.iwr.util.JsonUtiles;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ProductCli2Business implements IProductCli2Business {

	@Autowired(required = false)
	private ProductCli2Repository productDAO;

	@Autowired
	private IProductBusiness productBaseBusiness;

	@Override
	public ProductCli2 add(ProductCli2 product) throws FoundException, BusinessException {

		try {
			productBaseBusiness.load(product.getId());
			throw FoundException.builder().message("Se encontró el Producto id=" + product.getId()).build();
		} catch (NotFoundException e) {
		}



		try {
			return productDAO.save(product);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}
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
	public List<ProductCli2> listByPrice(Double start, Double end) throws BusinessException {
		try {
			return productDAO.findByPrecioBetweenOrderByPrecioAsc(start, end);
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
	//---------post deserializador
		@Autowired(required = false)
		private ICategoryBusiness categoryBusiness;

		@Override
		public ProductCli2 addExternal(String json) throws FoundException, BusinessException {
			
			//transformamos el json que puede venir con atributos difernetes y creamos
			ObjectMapper mapper = JsonUtiles.getObjectMapper(ProductCli2.class,
					new ProductCli2JsonDeserializer(ProductCli2.class, categoryBusiness),null);
			try {
				ProductCli2 product = null;
				product = mapper.readValue(json, ProductCli2.class);
				return add(product);
			} catch (IOException e) {
				//log.error(e.getMessage(), e);
				throw BusinessException.builder().message(e.getMessage()).build();
			} 


		}

		


}