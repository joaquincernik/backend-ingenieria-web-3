package ar.edu.iua.iwr.integration.cli1.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iwr.controllers.BaseRestController;
import ar.edu.iua.iwr.controllers.Constants;
import ar.edu.iua.iwr.integration.cli1.model.ProductCli1;
import ar.edu.iua.iwr.integration.cli1.model.business.IProductCli1Business;
import ar.edu.iua.iwr.model.business.BusinessException;
import ar.edu.iua.iwr.model.business.FoundException;
import ar.edu.iua.iwr.model.business.NotFoundException;
import ar.edu.iua.iwr.util.IStandartResponseBusiness;

@RestController
@RequestMapping(Constants.URL_INTEGRATION_CLI1 + "/products")
@Profile("cli1")

public class ProductCli1RestController extends BaseRestController {

	@Autowired
	private IProductCli1Business productBusiness;

	@Autowired
	private IStandartResponseBusiness response;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> list() {
		try {
			return new ResponseEntity<>(productBusiness.list(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{codCli1}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loadByCode(@PathVariable("codCli1") String codCli1) {
		try {
			return new ResponseEntity<>(productBusiness.load(codCli1), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "")
	public ResponseEntity<?> add(@RequestBody ProductCli1 product) {
		try {
			ProductCli1 response = productBusiness.add(product);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constants.URL_INTEGRATION_CLI1 + "/products/" + response.getCodCli1());
			return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.FOUND, e, e.getMessage()), HttpStatus.FOUND);
		}
	}
	
	
	
	// nuevo api B2B, OTRO sistema nos envie anosotros un json, un producto de otro lugar con otras codificaciones
	@PostMapping(value = "/b2b")
	public ResponseEntity<?> addExternal(HttpEntity<String> httpEntity) {
		//en vez de recibir un request body y usar el por defecto le decimos que usamos el nuestro
		//el string body lo catcheamos con http entyty
		try {
			ProductCli1 response = productBusiness.addExternal(httpEntity.getBody()); //creo instancia de product cli1
			HttpHeaders responseHeaders = new HttpHeaders(); //darle al respuesta que le damos siempre
			responseHeaders.set("location", Constants.URL_INTEGRATION_CLI1 + "/products/" + response.getCodCli1());
			return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.FOUND, e, e.getMessage()), HttpStatus.FOUND);
		}
	}

	



}