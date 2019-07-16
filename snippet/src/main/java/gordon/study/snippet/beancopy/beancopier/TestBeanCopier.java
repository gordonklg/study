package gordon.study.snippet.beancopy.beancopier;

import gordon.study.snippet.beancopy.model.ModelB;
import gordon.study.snippet.beancopy.model.ModelBB;
import gordon.study.snippet.beancopy.model.ModelUtil;
import net.sf.cglib.beans.BeanCopier;

public class TestBeanCopier {
    public static void main(String[] args) {

        ModelB b = ModelUtil.giveMeB();
        ModelBB bb = new ModelBB();
        final BeanCopier beanCopier = BeanCopier.create(ModelB.class, ModelBB.class, false);
        beanCopier.copy(b, bb, null);
        System.out.println(bb);

        long sum = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            beanCopier.copy(b, bb, null);
            sum += bb.getAge();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (end - start) + ". Sum: " + sum);
    }
}
