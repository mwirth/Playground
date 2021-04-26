package org.wimi.playground.server.core.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.wimi.playground.server.api.HelloWorldService;
import org.wimi.playground.server.core.beans.HelloGerman;

/**
 * @author Michael WIRTH
 */

@RequestScoped
public class HelloWorldResource implements HelloWorldService {

    @Inject
    private HelloGerman hello;

    @Override
    public String hello() {
        final String helloGerman = this.hello.sayHello();
        return "World Resourced: " + helloGerman;
    }

    @Override
    public String helloSub() {
        return "Hallo Test";
    }

}
