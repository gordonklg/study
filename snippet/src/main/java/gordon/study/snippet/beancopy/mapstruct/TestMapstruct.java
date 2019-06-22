package gordon.study.snippet.beancopy.mapstruct;

import gordon.study.snippet.beancopy.model.ModelB;
import gordon.study.snippet.beancopy.model.ModelBB;
import gordon.study.snippet.beancopy.model.ModelUtil;

public class TestMapstruct {

    public static void main(String[] args) {

        ModelB b = ModelUtil.giveMeB();
        ModelBB bb = TestMapper.INSTANCE.sourceToDestination(b);
        System.out.println(bb);

        long sum = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            bb = TestMapper.INSTANCE.sourceToDestination(b);
            sum += bb.getAge();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (end - start) + ". Sum: " + sum);
    }
}
