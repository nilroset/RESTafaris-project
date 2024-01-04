package edu.tecnocampus.labinternet.restafaris.restafarisapi.application.exception;
public class CourseNotFound extends RuntimeException {
    public CourseNotFound(String id){
        super("Course id" + id + "doesn't exist");
    }
}
