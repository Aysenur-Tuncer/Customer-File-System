package resources;

import entities.File;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import repos.FileRepository;

import java.util.List;

@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FileResource {

    @Inject
    FileRepository fileRepository;

    @GET
    @RolesAllowed({"admin"})
    public List<File> getAllFiles() {
        return fileRepository.listAll();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"admin"})
    public void updateFile(@PathParam("id") Long id, File file) {
        File existingFile = fileRepository.findById(id);
        if (existingFile != null) {
            existingFile.setFileName(file.getFileName());
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"admin"})
    public void deleteFile(@PathParam("id") Long id) {
        fileRepository.deleteById(id);
    }
}
