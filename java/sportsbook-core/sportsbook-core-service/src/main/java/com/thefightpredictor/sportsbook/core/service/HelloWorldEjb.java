package com.thefightpredictor.sportsbook.core.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thefightpredictor.sportsbook.core.data.UserLogin;
import com.thefightpredictor.sportsbook.core.service.interfaces.IHelloWorldEjb;

@Stateless
public class HelloWorldEjb implements IHelloWorldEjb
{
    @PersistenceContext
    private EntityManager em;

    public String getHelloWorld()
    {
        final List<UserLogin> theUserLogin = em.createQuery("SELECT u FROM UserLogin u", UserLogin.class).getResultList();
        return "Hello " + theUserLogin.get(0).getUserId();
    }
}
