package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car already mapped to online driver.")
public class CarAlreadyInUseException extends ConstraintsViolationException
{

    static final long serialVersionUID = -3387516993224229948L;


    public CarAlreadyInUseException(String message)
    {
        super(message);
    }

}
