package org.wimi.playground.server.se;

import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import org.wimi.playground.server.core.resources.HelloWorldResource;

/**
 */
public final class PlaygroundServer {

    private static final int AUTO_SELECT_PORT = 0;

    private static CliArguments cliArguments = new CliArguments();

    private static final class CliArguments {

        CliArguments() {
            // Prevent Synthetic Access Warning
        }

        @Parameter(names = { "-s", "--server" })
        private String server = "localhost";

        final String server() {
            return this.server;
        }

        @Parameter(names = { "-p", "--port" })
        private int port = AUTO_SELECT_PORT;

        final int port() {
            return this.port;
        }

        @Parameter(names = { "-h", "--help" }, help = true)
        private boolean help;

        final boolean help() {
            return this.help;
        }

        @Parameter(names = { "-v", "--verbose" }, description = "be verbose")
        private boolean verbose;

        final boolean verbose() {
            return this.verbose;
        }
    }

    public static void main(final String[] args) throws InterruptedException {
        final JCommander jCommander = JCommander.newBuilder().programName("PlaygroundServer").addObject(cliArguments).build();

        try {
            jCommander.parse(args);
        } catch (final ParameterException e) {
            System.err.print(e.getMessage());
            System.exit(1);
        }

        if (cliArguments.help()) {
            jCommander.usage();
            System.exit(0);
        }

        if (cliArguments.verbose()) {
            rootLogger().setLevel(Level.ALL);
            consoleHandler().setLevel(Level.ALL);
        }

        final URI baseUri = UriBuilder.fromUri("").scheme("http").host(cliArguments.server()).port(cliArguments.port()).path("api/helloworld").build();
        final ResourceConfig resourceConfigFromClasses = new ResourceConfig(HelloWorldResource.class);

        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfigFromClasses);
        final int actualPort = httpServer.getListener("grizzly").getPort();

        System.out.printf("Playground Server running on port %d - Send SIGKILL to shutdown.%n", actualPort);

        Thread.currentThread().join();
    }

    private static final Logger rootLogger() {
        Logger logger = Logger.getGlobal();
        while (logger.getParent() != null)
            logger = logger.getParent();
        return logger;
    }

    private static final Handler consoleHandler() {
        final Handler[] handlers = rootLogger().getHandlers();
        for (final Handler handler : handlers)
            if (handler instanceof ConsoleHandler)
                return handler;
        return new ConsoleHandler();
    }

}
