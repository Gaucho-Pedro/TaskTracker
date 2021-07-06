package org.example.TaskTracker.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Identifiers conflict")
public class PrimaryKeyViolationException extends Exception{
    public PrimaryKeyViolationException(String table, Long id){
        super("A row with the given identifier already exists: Table - \'" + table + "\'; ID[" + id + "]");
    }
}
