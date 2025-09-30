package ar.edu.iua.iwr.integration.cli2.model;

import static java.time.temporal.ChronoUnit.DAYS;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ar.edu.iua.iwr.util.JsonUtiles;

public class ProductCli2SlimV1JsonSerializer extends StdSerializer<ProductCli2> {


	public ProductCli2SlimV1JsonSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}
/*
 * {
 * category:null
 * }
 */
	@Override
	public void serialize(ProductCli2 value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("product", value.getProduct());
		gen.writeBooleanField("stock", value.isStock());
		gen.writeNumberField("price", value.getPrecio()); 		//para esto sirve, esta bueno


		if (value.getCategory() != null) { //puede o no tener
			gen.writeObjectFieldStart("category"); //category : {
			gen.writeNumberField("id", value.getCategory().getId());
			gen.writeStringField("category", value.getCategory().getCategory());
			gen.writeEndObject();
		} else {
			gen.writeNullField("category");
		}

		gen.writeObjectField("expirationDate", value.getExpirationDate());

		gen.writeNumberField("daysExpired", DAYS.between(Instant.ofEpochMilli(value.getExpirationDate().getTime())
				.atZone(ZoneId.systemDefault()).toLocalDateTime(), LocalDateTime.now()));

		String componentsStr = JsonUtiles //aca usamos lo que creamos recien ,el serializador
				.getObjectMapper(ComponentCli2.class, new ComponentCli2JsonSerializer(ComponentCli2.class), null)
				.writeValueAsString(value.getComponents());
		//aca obtenemos el set de componentes
		gen.writeFieldName("components");
		gen.writeRawValue(componentsStr);

		gen.writeEndObject();

	}

}
