package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Driver need to be online to perform this action.")
public class DriverNotOnlineException extends ConstraintsViolationException
{

    static final long serialVersionUID = -3387516993224229948L;


    public DriverNotOnlineException(String message)
    {
        super(message);
    }

}
