package edu.tecnocampus.labinternet.restafaris.restafarisapi.api.frontendException;

import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.exception.CourseNotFound;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.exception.EntityNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingAdvice {
    @ResponseBody
    @ExceptionHandler(CourseNotFound.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Course not found")
    String courseNotFoundHandler(Exception ex) {
        return ex.getMessage();
    }
}