package org.wimi.playground.server.se.cdi;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("mp")
@RequestScoped
public class MPConfigResource {

    @Inject
    private Config config;

    @Inject
    @ConfigProperty(name = "message", defaultValue = "stranger")
    private Optional<String> message;

    @Inject
    @ConfigProperty(name = "message")
    private Provider<String> messageRuntime;

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, MP Config!";
    }

    @GET
    @Path("config")
    @Produces("text/plain")
    public String config() {
        final String value = this.config.getValue("message", String.class);
        return "Hello, " + value;
    }

    @GET
    @Path("configField")
    @Produces("text/plain")
    public String configField() {
        return "Hello, " + this.message.get();
    }

    @GET
    @Path("configRuntime")
    @Produces("text/plain")
    public String configRuntime() {
        return "Hello, " + this.messageRuntime.get();
    }

}