package com.thefightpredictor.sportsbook.core.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thefightpredictor.sportsbook.core.data.UserLogin;
import com.thefightpredictor.sportsbook.core.service.interfaces.IHelloWorldEjb;

@Stateless
public class HelloWorldEjb implements IHelloWorldEjb
{
    @PersistenceContext(unitName = "sportsbook-persistence")
    private EntityManager em;

    public String getHelloWorld()
    {
        final UserLogin theUserLogin = em.find(UserLogin.class, 100);
        return "Hello " + theUserLogin.getUserId();
    }
}
