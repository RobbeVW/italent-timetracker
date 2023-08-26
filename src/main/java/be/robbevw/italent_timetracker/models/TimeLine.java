package be.robbevw.italent_timetracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

@Accessors(chain = true)
@Data
@RedisHash("Timeline")
public class TimeLine implements Comparable<TimeLine>, Serializable {

    @Id
    public Long id;
//    @JsonIgnore
//    @Reference
//    private Project project;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public int compareTo(TimeLine otherTimeline) {
        return otherTimeline.getStartTime().compareTo(this.getStartTime());

    }
}
