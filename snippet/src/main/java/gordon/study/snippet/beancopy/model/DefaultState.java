package gordon.study.snippet.beancopy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultState {
    INVALID("未生效", "1"),
    VALID("已生效", "2");
    private String label;
    private String value;

//    @Override
//    public String toString() {
//        return "WRONG";
//    }
}