package br.com.digitalzone.cursomc.resources.exceptions;

import java.time.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import br.com.digitalzone.cursomc.services.exceptions.AuthorizationException;
import br.com.digitalzone.cursomc.services.exceptions.DataIntegrityException;
import br.com.digitalzone.cursomc.services.exceptions.FileException;
import br.com.digitalzone.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				OffsetDateTime.now());
		
		e.getBindingResult().getFieldErrors().forEach( x -> err.addError(x.getField(), x.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		
	}
	
	//Tratar erro quando user nao tem as permissões
	@ExceptionHandler(value = {AuthorizationException.class})
    public ResponseEntity<StandardError> handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(),
				OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }
	
	//Tratar erro quando user nao tem as permissões
	@ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<StandardError> handleUserServiceException(AccessDeniedException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(),
				OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }
	

	@ExceptionHandler(value = {FileException.class})
    public ResponseEntity<StandardError> handleFileException(FileException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
	
	@ExceptionHandler(value = {AmazonServiceException.class})
    public ResponseEntity<StandardError> handleAmazonServiceException(AmazonServiceException e, HttpServletRequest request) {
		
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		
		StandardError err = new StandardError(code.value(), e.getMessage(),
				OffsetDateTime.now());
		
		return ResponseEntity.status(code).body(err);
    }
	
	@ExceptionHandler(value = {AmazonS3Exception.class})
    public ResponseEntity<StandardError> handleAmazonS3Exception(AmazonS3Exception e, HttpServletRequest request) {

		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
	
	@ExceptionHandler(value = {AmazonClientException.class})
    public ResponseEntity<StandardError> handleAmazonClientException(AmazonClientException e, HttpServletRequest request) {

		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
