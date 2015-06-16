package ca.easybooks.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.easybooks.service.interfaces.HelloWorldEjb;

@Stateless
public class HelloWorldEjbImpl implements HelloWorldEjb
{
    @PersistenceContext
    private EntityManager em;

    public String getHelloWorld()
    {
        return "Hello ";
    }
}
