package org.wimi.playground.server.se.cdi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Application;

@ApplicationScoped
public class HelloWorldApplication extends Application {

    public HelloWorldApplication() {
        super();
    }

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> hashSet = new HashSet<>();
        Collections.addAll(hashSet, HelloWorldResource.class);
        return hashSet;
    }
}