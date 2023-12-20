package repos;

import entities.File;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileRepository implements PanacheRepository<File> {
}