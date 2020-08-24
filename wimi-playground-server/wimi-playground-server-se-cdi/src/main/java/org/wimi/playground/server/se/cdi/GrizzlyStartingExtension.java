package org.wimi.playground.server.se.cdi;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;

import org.glassfish.grizzly.http.server.HttpServer;

public class GrizzlyStartingExtension implements Extension {

    // TODO make thread save?
    private volatile HttpServer server;

    @SuppressWarnings("unused")
    private void startServer(@Observes final AfterDeploymentValidation event, final BeanManager beanManager) {
        if (beanManager != null && this.server == null) {
            final HttpServer server = get(beanManager, HttpServer.class);
            if (server != null) {
                Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(server)));
                if (!server.isStarted())
                    try {
                        server.start();
                        System.out.printf("Server running on port %d - Send SIGKILL to shutdown.%n", server.getListener("grizzly").getPort());
                    } catch (final IOException ioException) {
                        event.addDeploymentProblem(ioException);
                    }
                this.server = server;
            }
        }
    }

    @SuppressWarnings("unused")
    private void shutdownServer(@Observes final BeforeShutdown event, final BeanManager beanManager) {
        if (this.server != null) {
            try {
                // TODO find a better way
                Thread.currentThread().join();
            } catch (final InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
            shutdown(this.server);
        }
    }

    @SuppressWarnings("unchecked")
    private static final <T> T get(final BeanManager beanManager, final Type type, final Annotation... qualifiers) {
        if (beanManager != null && type != null) {
            final Set<Bean<?>> beans = beanManager.getBeans(type, qualifiers);
            if (beans != null && !beans.isEmpty())
                return (T) beanManager.getReference(beanManager.resolve(beans), type, beanManager.createCreationalContext(beanManager.resolve(beans)));
        }
        return null;
    }

    private static final void shutdown(final HttpServer server) {
        if (server != null && server.isStarted())
            try {
                server.shutdown().get();
            } catch (final ExecutionException ignore) {
                // TODO add handling
            } catch (final InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
    }

}