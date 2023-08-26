package be.robbevw.italent_timetracker.models;

import jdk.jfr.Relational;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@RedisHash("Project")
public class Project implements Serializable {

    @Id
    public Long id;
    @Indexed
    public String uuid;
    public String name;
    public LocalDateTime dateCreated;
    @Reference
    public Set<TimeLine> timeLines = new TreeSet<>();
}
