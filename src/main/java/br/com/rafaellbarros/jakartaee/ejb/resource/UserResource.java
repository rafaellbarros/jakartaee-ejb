package br.com.rafaellbarros.jakartaee.ejb.resource;

import br.com.rafaellbarros.jakartaee.ejb.model.request.UserRequestCreateDTO;
import br.com.rafaellbarros.jakartaee.ejb.model.request.UserRequestUpdateDTO;
import br.com.rafaellbarros.jakartaee.ejb.service.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@RequestScoped
@Path("/users")
@Tag(name = "User resource", description = "Get User")
public class UserResource {

    @EJB
    private UserService userService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "We could not find the id User",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN))
                    ,
                    @APIResponse(
                            responseCode = "200",
                            description = "We found the id User",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON))})
    @Operation(summary = "Outputs id",
            description = "This method outputs id User")
    public Response getById(@PathParam("id") final Long id) {
        return Response.ok(userService.getById(id)).build();
    }

    @GET
    @Path("/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "We could not find the username User",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN))
                    ,
                    @APIResponse(
                            responseCode = "200",
                            description = "We found the username User",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON))})
    @Operation(summary = "Outputs username",
            description = "This method outputs username User")
    public Response getByUsername(@PathParam("username") final String username) {
        return Response.ok(userService.getByUsername(username)).build();
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "We could not find the email User",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN))
                    ,
                    @APIResponse(
                            responseCode = "200",
                            description = "We found the email User",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON))})
    @Operation(summary = "Outputs email",
            description = "This method outputs email User")
    public Response getByEmail(@PathParam("email") final String email) {
        return Response.ok(userService.getByEmail(email)).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "We could not find count Users",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN))
                    ,
                    @APIResponse(
                            responseCode = "200",
                            description = "We found the count Users",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON))})
    @Operation(summary = "Outputs count Users",
            description = "This method outputs count Users")
    public Response count() {
        return Response.ok(userService.getUsersCount()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "We could not find Users",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN))
                    ,
                    @APIResponse(
                            responseCode = "200",
                            description = "We found the Users",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON))})
    @Operation(summary = "Outputs Users",
            description = "This method outputs Users")
    public Response getUsers() {
        return Response.ok(userService.getUsers()).build();
    }

    @GET
    @Path("/search/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "404",
                            description = "We could not find Users or Emails",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN))
                    ,
                    @APIResponse(
                            responseCode = "200",
                            description = "We found the Users or Emails",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON))})
    @Operation(summary = "Outputs Users or Emails",
            description = "This method outputs Users or Emails")
    public Response searchForUserByUsernameOrEmail(@PathParam("search") final String search) {
        return Response.ok(userService.searchForUserByUsernameOrEmail(search)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "We create the username",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON))})
    @Operation(summary = "Outputs User",
            description = "This method outputs User")
    public Response create(UserRequestCreateDTO userRequestCreateDTO) {
        return Response.ok(userService.create(userRequestCreateDTO)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "We update the User",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON))})
    @Operation(summary = "Outputs User",
            description = "This method outputs User")
    public Response update(@PathParam("id") final Long id, UserRequestUpdateDTO userRequestUpdateDTO) {
        return Response.ok(userService.update(id, userRequestUpdateDTO)).build();
    }


}