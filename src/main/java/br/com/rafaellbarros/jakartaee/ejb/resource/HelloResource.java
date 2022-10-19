package br.com.rafaellbarros.jakartaee.ejb.resource;

import br.com.rafaellbarros.jakartaee.ejb.service.XablauService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;


@Path("/hello")
public class HelloResource {

    @EJB
    private XablauService xablauService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "We could not find the Hello message ",
                            content = @Content(mediaType = "text/plain"))
                    ,
                    @APIResponse(
                            responseCode = "200",
                            description = "We found the Hello message",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Properties.class)))})
    @Operation(summary = "Outputs Hello Message",
            description = "This method outputs Hello message")
    public Response hello() {
        return Response.ok(xablauService.xablau().toUpperCase()).build();
    }

    private String getMessageFormatted() {
        return xablauService.xablau().toUpperCase();
    }
}