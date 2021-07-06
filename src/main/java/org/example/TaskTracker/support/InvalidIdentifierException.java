package org.example.TaskTracker.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Missing identifier in table")
public class InvalidIdentifierException extends Exception{
    public InvalidIdentifierException(String table, Long id){
        super("There is no entry with a matching identifier: Table - \'" + table + "\'; ID[" + id + "]");
    }
}
