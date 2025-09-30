package ar.edu.iua.iwr.integration.cli2.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

//component cli 2 es una clase que tiene un id y una descripcion en un atributo component
public class ComponentCli2JsonSerializer extends StdSerializer<ComponentCli2> {

	protected ComponentCli2JsonSerializer(Class<ComponentCli2> t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	//json generator es la herramienta que nos deja generar un json desde 0
	
	@Override
	public void serialize(ComponentCli2 value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		// TODO Auto-generated method stub
		/*
		 * { "id" : valor de value, "component" : Harina}
		 */
		//apertura de llave:
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("component", value.getComponent());
		gen.writeEndObject();
	}
	
	
}
