package gordon.study.snippet.beancopy.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ModelUtil {

    public static ModelB giveMeB() {
        ModelB b = new ModelB();
        b.setName("Gordon");
        b.setAge(88);
        b.setDate(LocalDate.now());
        b.setTime(LocalDateTime.now());
        b.setHp(25.9);
        b.setEtc("whatever");
        b.setAddresses(ModelUtil.giveMeDList());
        b.setAtt(new BigDecimal(new BigInteger("12345678901234567890123456789"), 14));
        b.setState(DefaultState.VALID);
        return b;
    }

    public static List<ModelD> giveMeDList() {
        List<ModelD> list = new ArrayList<>();
        list.add(new ModelD("addr a", "100100"));
        list.add(new ModelD("addr b", "100200"));
        list.add(new ModelD("addr c", "100300"));
        list.add(new ModelD("addr d", "100400"));

        return list;
    }
}
