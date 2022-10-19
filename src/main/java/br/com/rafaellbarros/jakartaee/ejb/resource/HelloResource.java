package br.com.rafaellbarros.jakartaee.ejb.resource;

import br.com.rafaellbarros.jakartaee.ejb.service.XablauService;


import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/hello")
public class HelloResource {

    @EJB
    private XablauService xablauService;

    @GET
    @Produces("text/plain")
    public Response hello() {
        return Response.ok(getMessageFormatted()).build();
    }

    private String getMessageFormatted() {
        return xablauService.xablau().toUpperCase();
    }
}