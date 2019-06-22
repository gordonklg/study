package gordon.study.snippet.beancopy.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ModelBB {

    private String name;

    private Integer age;

    private Double hp;

    private LocalDate date;

    private LocalDateTime time;

    private String etc;

    private List<ModelDD> addresses;
}
