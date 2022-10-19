package br.com.rafaellbarros.jakartaee.ejb.resource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloResourceTest {

    @Test
    void hello() {
        String result = "hello!";
        assertEquals("hello!", result);
    }
}

