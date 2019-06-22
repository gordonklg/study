package gordon.study.snippet.guava.eventbus.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class BaseGuavaEvent {
    private LocalDateTime time;

    public BaseGuavaEvent() {
        time = LocalDateTime.now();
    }
}
