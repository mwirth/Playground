/*
 * Copyright (C) 2020 ProSeS BDE GmbH
 */

package org.wimi.playground.server.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Michael WIRTH (wirth@quipsy.de)
 *
 */
@Path("")
public interface HelloWorldService {

    @GET
    @Produces("text/plain")
    public String hello();

    @GET
    @Path("test")
    @Produces("text/plain")
    public String helloSub();

}