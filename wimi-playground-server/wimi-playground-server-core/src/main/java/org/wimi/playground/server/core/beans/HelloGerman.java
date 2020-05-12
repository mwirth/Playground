package org.wimi.playground.server.core.beans;

import javax.enterprise.context.Dependent;

import org.wimi.playground.server.api.Hello;

@Dependent
public class HelloGerman implements Hello {

    @Override
    public String sayHello(){
        return "Hallo Deutsch";
    }
}
