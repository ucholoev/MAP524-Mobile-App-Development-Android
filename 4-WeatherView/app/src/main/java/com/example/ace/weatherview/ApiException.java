package com.example.ace.weatherview;

public class ApiException extends Exception
{
    public ApiException(String message, Exception e)
    {
        super(message, e);
    }
}
