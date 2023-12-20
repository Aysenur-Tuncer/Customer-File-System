package resources;

import entities.User;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jwttoken.PasswordHashUtil;
import jwttoken.TokenService;
import repos.UserRepository;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    TokenService service;

    @Inject
    UserRepository userRepository;

    @POST
    @Path("/register")
    @Transactional
    public Response register(User user) {
        if (userRepository.findByName(user.getName()) != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Username is already taken")
                    .build();
        }
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(PasswordHashUtil.hashPassword(user.getPassword()));
        userRepository.persist(newUser);

        return Response.ok("Registration successful").build();
    }
    @GET
    @Path("/login")
    public Response login(@QueryParam("name")String name, @QueryParam("password") String password) {
        User user = userRepository.findByName(name);

        if (user != null && PasswordHashUtil.verifyPassword(password, user.getPassword())) {
            String jwtToken = service.generateToken(user.getName());
            return Response.ok(jwtToken).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
        }
    }
}