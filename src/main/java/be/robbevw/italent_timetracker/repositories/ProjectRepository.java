package be.robbevw.italent_timetracker.repositories;

import be.robbevw.italent_timetracker.models.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByUuid(String projectUuid);
}
