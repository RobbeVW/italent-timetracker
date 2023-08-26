package be.robbevw.italent_timetracker.repositories;

import be.robbevw.italent_timetracker.models.TimeLine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineRepository extends CrudRepository<TimeLine, Long> {

}
