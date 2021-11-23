package com.demo.errorhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.constant.Constants;
import com.demo.customer.service.impl.CustomerServiceImpl;
import com.demo.response.RestErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

	/*
	 * Handle Method Argument data type
	 * @param exception MethodArgumentTypeMismatchException
	 * @return RestErrorResponse
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody
	RestErrorResponse handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException exception) {
		log.error("Bad Request");
		log.error(exception.getParameter().getParameterName(), " type is invalid");
		log.error(exception.getMessage());
		return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(),
				exception.getParameter().getParameterName() + " type is invalid", exception.getMessage());
	}

	/**
	 * Handle Global Exception
	 * @param ex Exception
	 * @return RestErrorResponse
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody
	RestErrorResponse handleException(Exception ex) {
		log.error("Internal Server Error");
		log.error(ex.getLocalizedMessage());
		return new RestErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage());
	}

	/**
	 * Handle Response Status Exception,
	 *
	 * @param ex ResponseStatusException
	 * @return RestErrorResponse
	 */
	@ExceptionHandler(ResponseStatusException.class)
	public @ResponseBody
	ResponseEntity<RestErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
		log.error("Exception Error");
		log.error(ex.getReason());
		log.error(ex.getLocalizedMessage());
		return new ResponseEntity<>(new RestErrorResponse(ex.getStatus().value(), ex.getReason(),
				ex.getLocalizedMessage()), ex.getStatus());
	}

	/*
	 * Handle Missing Parameter exception and Override from ResponseEntityExceptionHandler
	 * @param ex MissingServletRequestParameterException
	 * @param headers HttpHeaders
	 * @param status HttpStatus
	 * @param request WebRequest
	 * @return Bad Request Exception
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
																		  HttpHeaders headers, HttpStatus status,
																		  WebRequest request) {
		log.error("Handle Missing Parameter Error - Bad Request");
		log.error(ex.getParameterName(), " is required");
		log.error(ex.getMessage());
		return new ResponseEntity<>(new RestErrorResponse(HttpStatus.BAD_REQUEST.value(),
				ex.getParameterName() + " is required", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/*
	 * Handle when method argument is not valid
	 * @param exception MethodArgumentNotValidException
	 * @param headers HttpHeaders
	 * @param status HttpStatus
	 * @param request WebRequest
	 * @return RestErrorResponse
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
																  final HttpHeaders headers, final HttpStatus status,
																  final WebRequest request) {
		final List<String> errors = new ArrayList<>();
		for (final FieldError error : exception.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : exception.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		log.error("Handle Method Argument Error - Bad Request");
		log.error(exception.getMessage());
		return new ResponseEntity<>(new RestErrorResponse(HttpStatus.BAD_REQUEST.value(),
				errors.toString(), exception.getMessage()), HttpStatus.BAD_REQUEST);
	}

    /*
     * Handle invalid JSON requests
     * @param ex HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return RestErrorResponse
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Handle Invalid JSON Error - Bad Request");
		log.error(ex.getMessage());
		return new ResponseEntity<>(new RestErrorResponse(HttpStatus.BAD_REQUEST.value(),
				Constants.JSON_INVALID, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
    
}