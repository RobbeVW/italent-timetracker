package be.robbevw.italent_timetracker.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TimeLineTest {

    @Test
    void compare() {
        final TimeLine timeLineToday = new TimeLine().setStartTime(LocalDateTime.now());
        final TimeLine timeLineYesterday = new TimeLine().setStartTime(LocalDateTime.now().minusDays(1));

        final TreeSet<TimeLine> timelines = new TreeSet<>();
        timelines.add(timeLineToday);
        timelines.add(timeLineYesterday);

        assertThat(timelines.first()).isEqualTo(timeLineToday);
    }
}
