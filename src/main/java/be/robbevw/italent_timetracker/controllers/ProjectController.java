package be.robbevw.italent_timetracker.controllers;

import be.robbevw.italent_timetracker.models.Project;
import be.robbevw.italent_timetracker.models.TimeLine;
import be.robbevw.italent_timetracker.repositories.ProjectRepository;
import be.robbevw.italent_timetracker.repositories.TimelineRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.apache.kafka.common.Uuid.randomUuid;

@RequiredArgsConstructor
@RequestMapping("/projects")
@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final TimelineRepository timelineRepository;
    private final KafkaTemplate<String, Project> kafkaTemplate;

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Project createProject(@RequestBody Project project) {
        project.setId(null);
        project.setUuid(randomUuid().toString());
        project.setDateCreated(LocalDateTime.now());

        return projectRepository.save(project);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Project getProject(@PathVariable String uuid) {
        return projectRepository.findByUuid(uuid);
    }

    @Transactional
    @GetMapping(value = "/{uuid}/toggle", produces = MediaType.APPLICATION_JSON_VALUE)
    public Project toggleProjectTimeline(@PathVariable String uuid) {
        final Project project = projectRepository.findByUuid(uuid);

        project.getTimeLines().stream().filter(timeLine -> timeLine.getEndTime() == null)
                .findFirst()
                .ifPresentOrElse(this::endCurrentTimeline, () -> createNewTimeline(project));

        kafkaTemplate.send("Projects", project);

        return projectRepository.save(project);
    }

    private TimeLine getNewTimeline() {
        final TimeLine timeLine = new TimeLine()
                .setStartTime(LocalDateTime.now());

        return timelineRepository.save(timeLine);
    }

    private void createNewTimeline(Project project) {
        project.getTimeLines().add(getNewTimeline());
    }

    private void endCurrentTimeline(TimeLine timeLine) {
        timeLine.setEndTime(LocalDateTime.now());

        timelineRepository.save(timeLine);
    }
}
