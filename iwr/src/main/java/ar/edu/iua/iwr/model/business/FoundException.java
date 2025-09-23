package ar.edu.iua.iwr.model.business;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FoundException extends Exception {

	//patron builder
	
	//THrpwable clase madre de todas excpciones
	
	@Builder
	public FoundException(String message, Throwable ex) {
		super(message,ex);
	}
	
	@Builder
	public FoundException(String message) {
		super(message);
	}
	
	@Builder
	public FoundException(Throwable ex) {
		super(ex);
	}
}
