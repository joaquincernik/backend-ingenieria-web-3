package ar.edu.iua.iwr.integration.cli1.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import ar.edu.iua.iwr.model.business.BusinessException;
import ar.edu.iua.iwr.model.business.ICategoryBusiness;
import ar.edu.iua.iwr.model.business.NotFoundException;
import ar.edu.iua.iwr.util.JsonUtiles;

public class ProductCli1JsonDeserializer extends StdDeserializer<ProductCli1> {


	protected ProductCli1JsonDeserializer(Class<?> vc) {
		super(vc);
	}

	private ICategoryBusiness categoryBusiness;

	public ProductCli1JsonDeserializer(Class<?> vc, ICategoryBusiness categoryBusiness) {
		super(vc);
		this.categoryBusiness = categoryBusiness;
	}

	@Override
	public ProductCli1 deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
		ProductCli1 r = new ProductCli1();
		JsonNode node = jp.getCodec().readTree(jp); //objeto que represenra el json

		String code = JsonUtiles.getString(node, "product_code,code_product,code".split(","),
				System.currentTimeMillis() + ""); //cualquier de esos 3 va a establecerlo en co
		String productDesc = JsonUtiles.getString(node,
				"product,description,product_description,product_name".split(","), null);
		double price = JsonUtiles.getDouble(node, "product_price,price_product,price".split(","), 0);
		boolean stock = JsonUtiles.getBoolean(node, "stock,in_stock".split(","), false);
		
		//chequeo que no venga vacio
		if(productDesc == null) {
			//hayq ue perfeccionar como tira wel error
			throw new IOException("tenes que enviar un nombre de producto");
		}
		else {
		r.setCodCli1(code);
		r.setProduct(productDesc);
		r.setPrecio(price);
		r.setStock(stock);
		
		//esperamos el nombre de la cateogira, no el id
		String categoryName = JsonUtiles.getString(node, "category,product_category,category_product".split(","), null);
		if (categoryName != null) {
			try {
				r.setCategory(categoryBusiness.load(categoryName)); //el load e sl que busca
			} catch (NotFoundException | BusinessException e) {
			}
		}
		return r;
		}
	}

}
