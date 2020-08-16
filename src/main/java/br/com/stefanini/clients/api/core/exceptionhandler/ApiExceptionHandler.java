package br.com.stefanini.clients.api.core.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.stefanini.clients.api.data.exception.ClienteExistenteException;
import br.com.stefanini.clients.api.data.exception.EntidadeNaoEncontradaException;
import br.com.stefanini.clients.api.data.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(status.value());
		exceptionResponse.setTitulo("Not found");
		exceptionResponse.setDataHora(OffsetDateTime.now());
		exceptionResponse.setErrors(Arrays.asList(new ExceptionResponse.Error(ex.getMessage())));
		return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(status.value());
		exceptionResponse.setTitulo("Um ou mais campos estão inválidos");
		exceptionResponse.setDataHora(OffsetDateTime.now());
		exceptionResponse.setErrors(Arrays.asList(new ExceptionResponse.Error(ex.getMessage())));
		return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ExceptionResponse.Error> campos = new ArrayList<ExceptionResponse.Error>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = nome + " " + messageSource.getMessage(error, LocaleContextHolder.getLocale());

			campos.add(new ExceptionResponse.Error(mensagem));
		}

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(status.value());
		exceptionResponse
				.setTitulo("Um ou mais campos estão inválidos. " + "Faça o preenchimento correto e tente novamente");
		exceptionResponse.setDataHora(OffsetDateTime.now());
		exceptionResponse.setErrors(campos);

		return super.handleExceptionInternal(ex, exceptionResponse, headers, status, request);
	}

	@ExceptionHandler({ ClienteExistenteException.class })
	public ResponseEntity<Object> handlePessoaExistenteException(ClienteExistenteException ex) {

		String mensagem = messageSource.getMessage("cliente.existente", null, LocaleContextHolder.getLocale());

		List<ExceptionResponse.Error> error = new ArrayList<ExceptionResponse.Error>();
		error.add(new ExceptionResponse.Error(mensagem));

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(400);
		exceptionResponse.setTitulo("CPF já cadastro");
		exceptionResponse.setDataHora(OffsetDateTime.now());
		exceptionResponse.setErrors(error);

		return ResponseEntity.badRequest().body(exceptionResponse);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e) {

		List<ExceptionResponse.Error> erros = new ArrayList<ExceptionResponse.Error>();

		for (ConstraintViolation<?> error : e.getConstraintViolations()) {
			String mensagem = error.getPropertyPath().toString() + " " + error.getMessage();
			erros.add(new ExceptionResponse.Error(mensagem));
		}

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(400);
		exceptionResponse
				.setTitulo("Um ou mais campos estão inválidos. " + "Faça o preenchimento correto e tente novamente");
		exceptionResponse.setDataHora(OffsetDateTime.now());
		exceptionResponse.setErrors(erros);

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

	}

}
