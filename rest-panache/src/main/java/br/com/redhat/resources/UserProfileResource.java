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

import br.com.redhat.model.UserProfile;

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserProfileResource {
    
    @GET
    public Response profiles() {
        List<UserProfile> profiles = UserProfile.listAll();
        return Response.ok(profiles).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Optional<UserProfile> profile = UserProfile.findByIdOptional(id);
        if(profile.isPresent()) {
            return Response.ok(profile.get()).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    public Response create(UserProfile profile) {
        profile.persist();
        return Response.created(URI.create("/profiles/" + profile.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, UserProfile profile) {
        Optional<UserProfile> databaseProfile = UserProfile.findByIdOptional(id);
        if(databaseProfile.isPresent()) {
            databaseProfile.get().age = profile.age;
            databaseProfile.get().jobTitle = profile.jobTitle;
            databaseProfile.get().location = profile.location;
            return Response.ok(databaseProfile).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Optional<UserProfile> profile = UserProfile.findByIdOptional(id);
        if(profile.isPresent()) {
            profile.get().delete();
            return Response.noContent().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }
}
