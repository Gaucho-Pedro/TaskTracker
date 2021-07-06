package org.example.TaskTracker.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Required field not initialized")
public class RequiredFieldMissingException extends Exception {
    public RequiredFieldMissingException(String field){
        super("The field \'" + field + "\' is not initialized. " +
                "Probably the request specified an invalid name for the property.");
    }
}
