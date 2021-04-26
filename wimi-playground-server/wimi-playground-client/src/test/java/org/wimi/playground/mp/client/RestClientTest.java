package org.wimi.playground.mp.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wimi.playground.server.api.HelloWorldService;

public class RestClientTest {

    private static HelloWorldService client;

    @BeforeClass
    public static void init() throws MalformedURLException {
        final URI baseURI = URI.create("http://localhost:9090/api/helloworld");
        client = RestClientBuilder.newBuilder().baseUri(baseURI).build(HelloWorldService.class);
        assertNotNull(client);
    }

    @Test
    public void root() throws MalformedURLException {
        final String result = client.hello();
        assertNotNull(result);
    }

    @Test
    public void helloTest() throws MalformedURLException {
        final String result = client.helloSub();
        assertEquals(result, "Hallo Test");
    }
}