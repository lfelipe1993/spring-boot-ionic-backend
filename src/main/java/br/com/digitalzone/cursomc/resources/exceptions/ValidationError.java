package br.com.digitalzone.cursomc.resources.exceptions;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> list = new ArrayList<>();

	public ValidationError(Integer status, String msg, OffsetDateTime timestamp) {
		super(status, msg, timestamp);
	}

	public List<FieldMessage> getErrors() {
		return list;
	}

	public void addError(String fieldName, String messages) {
		list.add(new FieldMessage(fieldName,messages));
	}
	
	

}
