package pl.mateusz_semklo.automationshoprest;

import jakarta.validation.ConstraintViolationException;
import org.hibernate.JDBCException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody ResponseEntity<String> badRequest(ConstraintViolationException e){
        ResponseEntity<String> responseEntity=new ResponseEntity<>("BŁADConstraintViolationException",HttpStatus.CONFLICT);
        return responseEntity;
    }

    @ExceptionHandler({JDBCException.class})
    public @ResponseBody ResponseEntity<String> badRequest1(MethodArgumentNotValidException e){
        ResponseEntity<String> responseEntity=new ResponseEntity<>("BŁADJDBCException",HttpStatus.CONFLICT);
        return responseEntity;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public @ResponseBody ResponseEntity<String> badRequest2(MethodArgumentNotValidException e){
        ResponseEntity<String> responseEntity=new ResponseEntity<>("BŁADMethodArgumentNotValidException",HttpStatus.CONFLICT);
        return responseEntity;
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public @ResponseBody ResponseEntity<String> badRequest3(MethodArgumentNotValidException e){
        ResponseEntity<String> responseEntity=new ResponseEntity<>("BŁADDataIntegrityViolationException",HttpStatus.CONFLICT);
        return responseEntity;
    }


    @ExceptionHandler(AuthenticationException.class)
    public @ResponseBody ResponseEntity<String> badRequest5(MethodArgumentNotValidException e){
        ResponseEntity<String> responseEntity=new ResponseEntity<>("BŁADAuthenticationException",HttpStatus.CONFLICT);
        return responseEntity;
    }



}
