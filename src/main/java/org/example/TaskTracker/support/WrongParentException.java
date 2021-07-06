package org.example.TaskTracker.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid parent specified")
public class WrongParentException extends Exception{
    public WrongParentException(Long selfId, Long parentId){
        super("Invalid parent specified. Most likely the descendant and ancestor are the same: " +
                "Self ID[" + selfId + "]; Parent ID[" + parentId + "]");
    }
}
