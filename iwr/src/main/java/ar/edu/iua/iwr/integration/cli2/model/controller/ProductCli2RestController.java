package ar.edu.iua.iwr.integration.cli2.model.controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ar.edu.iua.iwr.controllers.BaseRestController;
import ar.edu.iua.iwr.controllers.Constants;
import ar.edu.iua.iwr.integration.cli1.model.ProductCli1;
import ar.edu.iua.iwr.integration.cli2.model.ProductCli2;
import ar.edu.iua.iwr.integration.cli2.model.ProductCli2SlimV1JsonSerializer;
import ar.edu.iua.iwr.integration.cli2.model.business.IProductCli2Business;
import ar.edu.iua.iwr.model.business.BusinessException;
import ar.edu.iua.iwr.model.business.FoundException;
import ar.edu.iua.iwr.util.IStandartResponseBusiness;
import ar.edu.iua.iwr.util.JsonUtiles;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.URL_INTEGRATION_CLI2 + "/products")
@Profile("cli2")


@Slf4j
public class ProductCli2RestController extends BaseRestController {

	@Autowired
	private IProductCli2Business productBusiness;

	@Autowired
	private IStandartResponseBusiness response;
	// http://localhost:8080/api/v1/integration/cli2/products/list-expired?since=2025-09-15 18:00:00

	@GetMapping(value = "/list-expired", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listExpired(
			@RequestParam(name = "since", required = false, defaultValue = "1970-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date since,
			@RequestParam(name = "slim", required = false, defaultValue = "v0") String slimVersion) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(since);
			if (c.get(Calendar.YEAR) == 1970) {
				since = new Date();
			}
			
			
			//-------serializador en base a un conficional--------------
			StdSerializer<ProductCli2> ser = null; //creamos una variable y la igualamos a null
			if (slimVersion.equalsIgnoreCase("v1")) { 
				ser = new ProductCli2SlimV1JsonSerializer(ProductCli2.class, false);
			}else {
				//hacer lo que estabamos haciendo antes de agregar el serializador
				return new ResponseEntity<>(productBusiness.listExpired(since), HttpStatus.OK);
			}
			
			String result = JsonUtiles.getObjectMapper(ProductCli2.class, ser, null)
					.writeValueAsString(productBusiness.listExpired(since));

			
			//---------------------------------
			log.debug(since.toString());
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (BusinessException  | JsonProcessingException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/api/v1/integration/cli2/products/list-by-price?start-price=10&end-price=20

		@GetMapping(value = "/list-by-price", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> listByPrice(
				@RequestParam(name = "start-price", required = false, defaultValue = "0.0") Double start,
				@RequestParam(name = "end-price", required = false, defaultValue = "500000000") Double end) {
			try {
				
			return new ResponseEntity<>(productBusiness.listByPrice(start, end), HttpStatus.OK);
				
			} catch (BusinessException  e) {
				return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	// nuevo api B2B, OTRO sistema nos envie anosotros un json, un producto de otro lugar con otras codificaciones
		@PostMapping(value = "/b2b")
		public ResponseEntity<?> addExternal(HttpEntity<String> httpEntity) {
			//en vez de recibir un request body y usar el por defecto le decimos que usamos el nuestro
			//el string body lo catcheamos con http entyty
			try {
				ProductCli2 response = productBusiness.addExternal(httpEntity.getBody()); //creo instancia de product cli1
				HttpHeaders responseHeaders = new HttpHeaders(); //darle al respuesta que le damos siempre
			//responseHeaders.set("location", Constants.URL_INTEGRATION_CLI1 + "/products/" + response.getCodCli1());
				return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
			} catch (BusinessException e) {
				return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (FoundException e) {
				return new ResponseEntity<>(response.build(HttpStatus.FOUND, e, e.getMessage()), HttpStatus.FOUND);
			}
		}

}
