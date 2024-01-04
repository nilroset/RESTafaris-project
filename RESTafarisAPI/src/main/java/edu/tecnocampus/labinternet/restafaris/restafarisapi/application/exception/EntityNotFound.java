package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.exception;

public class EntityNotFound extends RuntimeException{
    public EntityNotFound(Class entity, String fieldName, Object value ) {
        super(" Entity: " + entity.getName() + " not found with " + fieldName + " and value " + value);
    }
}
