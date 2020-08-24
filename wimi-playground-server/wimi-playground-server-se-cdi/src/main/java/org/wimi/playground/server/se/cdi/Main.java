package org.wimi.playground.server.se.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ContainerFactory;

public class Main {

    public static final void main(final String[] args) throws Exception {
        final SeContainerInitializer containerInitializer = SeContainerInitializer.newInstance();
        assert containerInitializer != null;
        try (final SeContainer container = containerInitializer.initialize()) {
            assert container != null;
        }
    }

    @ApplicationScoped
    static final class Producers {

        @Produces
        @Dependent
        private static final GrizzlyHttpContainer produceGrizzlyHttpContainer(final Instance<Application> applicationInstance) {
            if (applicationInstance != null && !applicationInstance.isUnsatisfied())
                return ContainerFactory.createContainer(GrizzlyHttpContainer.class, applicationInstance.get());

            return null;
        }

        @Produces
        @Dependent
        private static final HttpServer produceHttpServerFromContainer(final Instance<GrizzlyHttpContainer> handlerInstance) {
            if (handlerInstance != null && !handlerInstance.isUnsatisfied())
                return GrizzlyHttpServerFactory.createHttpServer(
                        UriBuilder.fromUri("").scheme("http").host("localhost").port(9090).path("api/helloworld").build(), handlerInstance.get(), false, null,
                        false);

            return null;
        }

    }

}