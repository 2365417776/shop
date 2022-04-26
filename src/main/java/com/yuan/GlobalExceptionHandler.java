package com.yuan;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest request, Exception e)throws Exception{
            e.printStackTrace();
            Class<?> constraintViolationException = Class.forName("org.hibernate.exception.ConstraintViolationException");
            if(e.getCause() != null && constraintViolationException == e.getCause().getClass())
                return "违反了约束，多半是外键约束";
            return e.getMessage();
    }
}
