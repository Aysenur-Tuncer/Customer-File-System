package resources;

import entities.Customer;
import entities.File;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import repos.CustomerRepository;
import repos.FileRepository;

import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
public class CustomerResource {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    FileRepository fileRepository;

    @GET
    @RolesAllowed({"admin"})
    public List<Customer> getAllCustomers() {
        return customerRepository.listAll();
    }

    @POST
    @Transactional
    @RolesAllowed({"admin"})
    public void createCustomer(Customer customer) {
        customerRepository.persist(customer);
    }

    @POST
    @Path("/{customerId}/files")
    @Transactional
    @RolesAllowed({"admin"})
    public Response addFileToCustomer(@PathParam("customerId") Long customerId, File file) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            return Response.status(404).build();
        }
        file.setCustomer(customer);
        fileRepository.persist(file);

        return Response.ok(file).status(201).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"admin"})
    public void updateCustomer(@PathParam("id") Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id);
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setSurname(customer.getSurname());
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"admin"})
    public void deleteCustomer(@PathParam("id") Long id) {
        customerRepository.deleteById(id);
    }
}