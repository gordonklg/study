package gordon.study.snippet.beancopy.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelAA {

    private String name;

    private String age;

    private Integer hp;

    private String date;

    private String time;

    private String unknown;
}
