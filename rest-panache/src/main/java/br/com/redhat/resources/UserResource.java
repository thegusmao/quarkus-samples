package br.com.redhat.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import br.com.redhat.model.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    
    @GET
    @Path("/test")
    public Response exploring() {
        List<User> users = User.withJobTitle("Developer");
        return Response.ok(users).build();
    }

    @GET
    public Response users() {
        List<User> users = User.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response user(@PathParam("id") Long id) {
        Optional<User> user = User.findByIdOptional(id);
        if(user.isPresent()) {
            return Response.ok(user.get()).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    public Response newUser(User user) {
        user.persist();
        return Response.status(Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, User user) {
        Optional<User> databaseUser = User.findByIdOptional(id);
        if(databaseUser.isPresent()) {
            databaseUser.get().name = user.name;
            databaseUser.get().password = user.password;
            databaseUser.get().email = user.email;
            return Response.ok(user).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        Optional<User> user = User.findByIdOptional(id);
        if(user.isPresent()) {
            user.get().delete();
            return Response.noContent().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }
}
