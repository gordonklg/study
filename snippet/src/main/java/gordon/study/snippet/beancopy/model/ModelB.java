package gordon.study.snippet.beancopy.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ModelB {

    private String name;

    private Integer age;

    private double hp;

    private LocalDate date;

    private LocalDateTime time;

    private String etc;

    private List<ModelD> addresses;

    private BigDecimal att;

    private DefaultState state;


}
