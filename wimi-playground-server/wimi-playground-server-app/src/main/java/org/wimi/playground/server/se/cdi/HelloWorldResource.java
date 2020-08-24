package org.wimi.playground.server.se.cdi;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("hello")
@RequestScoped
public class HelloWorldResource {

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, world!";
    }

}