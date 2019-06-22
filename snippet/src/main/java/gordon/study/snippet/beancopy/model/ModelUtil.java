package gordon.study.snippet.beancopy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ModelUtil {

    public static ModelB giveMeB() {

        return ModelB.builder().name("Gordon").age(88).date(LocalDate.now()).time(LocalDateTime.now())
                .hp(25.9).etc("whatever").addresses(ModelUtil.giveMeDList()).build();

    }

    public static List<ModelD> giveMeDList() {

        List<ModelD> list = new ArrayList<>();
        list.add(ModelD.builder().address("addr a").zipcode("100100").build());
        list.add(ModelD.builder().address("addr b").zipcode("100200").build());
        list.add(ModelD.builder().address("addr c").zipcode("100200").build());
        list.add(ModelD.builder().address("addr d").zipcode("100300").build());

        return list;
    }
}
