package com.thefightpredictor.sportsbook.core.service;

import javax.ejb.Stateless;

import com.thefightpredictor.sportsbook.core.service.interfaces.IHelloWorldEjb;

@Stateless
public class HelloWorldEjb implements IHelloWorldEjb
{
    public String getHelloWorld()
    {
        return "Hello world";
    }
}
