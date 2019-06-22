package gordon.study.snippet.guava.eventbus.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomGuavaEvent extends BaseGuavaEvent {

    private String name;

    public CustomGuavaEvent() {
        super();
    }
}
