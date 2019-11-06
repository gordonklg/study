package gordon.study.snippet.jdk.lambda;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private String name;
    private int salary;
    private String office;
}
