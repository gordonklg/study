package gordon.study.snippet.beancopy.orika;


import gordon.study.snippet.beancopy.model.ModelB;
import gordon.study.snippet.beancopy.model.ModelBB;
import gordon.study.snippet.beancopy.model.ModelUtil;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class TestOrika {

    public static void main(String[] args) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        ModelB b = ModelUtil.giveMeB();
        ModelBB bb = mapper.map(b, ModelBB.class);
        System.out.println(bb);

        long sum = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            bb = mapper.map(b, ModelBB.class);
            sum += bb.getAge();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (end - start) + ". Sum: " + sum);
    }

}
