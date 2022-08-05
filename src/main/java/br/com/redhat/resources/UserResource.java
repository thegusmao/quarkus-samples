package br.com.redhat.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.redhat.model.User;

@Path("/users")
public class UserResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response exploring() {
        List<User> users = User.byGender("M");
        return Response.ok(users).build();
    }
}
