package org.wimi.playground.server.core.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.wimi.playground.server.core.beans.HelloGerman;

/**
 * @author Michael WIRTH
 */
@Path("")
@RequestScoped
public class HelloWorldResource {

    @Inject
    private HelloGerman hello;

    @GET
    @Produces("text/plain")
    public String hello() {
        String helloGerman = hello.sayHello();
        return "World Resourced: " + helloGerman;
    }

}
