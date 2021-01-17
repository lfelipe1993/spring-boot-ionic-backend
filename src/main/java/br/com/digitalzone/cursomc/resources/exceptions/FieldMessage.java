package br.com.digitalzone.cursomc.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	public FieldMessage() {
		// TODO Auto-generated constructor stub
	}

	public FieldMessage(String fieldname, String message) {
		super();
		this.fieldName = fieldname;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldname) {
		this.fieldName = fieldname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
