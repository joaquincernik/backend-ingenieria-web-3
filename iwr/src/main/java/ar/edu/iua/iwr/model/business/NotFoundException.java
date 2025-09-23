package ar.edu.iua.iwr.model.business;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends Exception {

	//patron builder
	
	//THrpwable clase madre de todas excpciones
	
	@Builder
	public NotFoundException(String message, Throwable ex) {
		super(message,ex);
	}
	
	@Builder
	public NotFoundException(String message) {
		super(message);
	}
	
	@Builder
	public NotFoundException(Throwable ex) {
		super(ex);
	}
}
