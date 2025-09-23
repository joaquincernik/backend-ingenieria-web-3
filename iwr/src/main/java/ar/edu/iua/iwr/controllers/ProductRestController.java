package ar.edu.iua.iwr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iwr.model.Category;
import ar.edu.iua.iwr.model.Product;
import ar.edu.iua.iwr.model.business.BusinessException;
import ar.edu.iua.iwr.model.business.FoundException;
import ar.edu.iua.iwr.model.business.ICategoryBusiness;
import ar.edu.iua.iwr.model.business.IProductBusiness;
import ar.edu.iua.iwr.model.business.NotFoundException;
import ar.edu.iua.iwr.util.IStandartResponseBusiness;

//marca que es un controller
@RestController

//mapea lo que te viene a esa url base
@RequestMapping(Constants.URL_PRODUCTS)
public class ProductRestController {

	// esto se encargan de instanciar los objetos
	@Autowired
	private IProductBusiness productBusiness;
	@Autowired
	private IStandartResponseBusiness response;

	// produce un json cuando le llega el valor .../
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> list() {
		// <?> devuelve algun tipo generico
		try {
			// <> operador diamante, compilador puede inferir el tipo generico a partir del
			// contexto
			// si no tenias que poner ResponseEntity<List<Product>> ...
			return new ResponseEntity<>(productBusiness.list(), HttpStatus.OK);
		} catch (BusinessException e) {

			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping(value = "")
	// lo que te lleva de body lo convierte en objeto
	public ResponseEntity<?> add(@RequestBody Product product) {
		try {
			Product response = productBusiness.add(product);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constants.URL_PRODUCTS + "/" + response.getId());
			return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);

		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (FoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.FOUND, e, e.getMessage()), HttpStatus.FOUND);
		}
	}

	// products/1
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> load(@PathVariable long id) {
		try {
			return new ResponseEntity<>(productBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.FOUND, e, e.getMessage()), HttpStatus.FOUND);
		}
	}

	// http://localhost:8080/api/v1/products/by-name/Arroz
	@GetMapping(value = "/by-name/{product}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> load(@PathVariable String product) {
		try {
			return new ResponseEntity<>(productBusiness.load(product), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "")
	public ResponseEntity<?> update(@RequestBody Product product) {
		try {
			productBusiness.update(product);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (FoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.FOUND, e, e.getMessage()), HttpStatus.FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		try {
			productBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	
	//Categorías 
	
		@Autowired
		private ICategoryBusiness categoryBusiness;

		@GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> listCategories() {
			try {
				return new ResponseEntity<>(categoryBusiness.list(), HttpStatus.OK);
			} catch (BusinessException e) {
				return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@GetMapping(value = "/categories/{id}")
		public ResponseEntity<?> loadCategory(@PathVariable("id") long id) {
			try {
				return new ResponseEntity<>(categoryBusiness.load(id), HttpStatus.OK);
			} catch (BusinessException e) {
				return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (NotFoundException e) {
				return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
			}
		}

		@PostMapping(value = "/categories")
		public ResponseEntity<?> addCategory(@RequestBody Category category) {
			try {
				Category response = categoryBusiness.add(category);
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.set("location", Constants.URL_PRODUCTS + "/categories/" + response.getId());
				return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
			} catch (BusinessException e) {
				return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (FoundException e) {
				return new ResponseEntity<>(response.build(HttpStatus.FOUND, e, e.getMessage()), HttpStatus.FOUND);
			}
		}

		@PutMapping(value = "/categories")
		public ResponseEntity<?> updateCategory(@RequestBody Category category) {
			try {
				categoryBusiness.update(category);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (BusinessException e) {
				return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (NotFoundException e) {
				return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
			}
		}

		@DeleteMapping(value = "/categories/{id}")
		public ResponseEntity<?> deleteCategory(@PathVariable("id") long id) {
			try {
				categoryBusiness.delete(id);
				return new ResponseEntity<String>(HttpStatus.OK);
			} catch (BusinessException e) {
				return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (NotFoundException e) {
				return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
			}
		}


}