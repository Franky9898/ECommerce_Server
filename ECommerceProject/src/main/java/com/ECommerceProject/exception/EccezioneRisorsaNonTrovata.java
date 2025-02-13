package com.ECommerceProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class EccezioneRisorsaNonTrovata extends RuntimeException
{
	public EccezioneRisorsaNonTrovata(String message) 
	{
        super(message);
    }
}
