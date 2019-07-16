package gordon.study.snippet.beancopy.orika;

import gordon.study.snippet.beancopy.model.ModelA;
import gordon.study.snippet.beancopy.model.ModelB;
import gordon.study.snippet.beancopy.model.ModelUtil;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaConverterSample {

    public static void main(String[] args) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new LocalDateOrikaConvertor());
        converterFactory.registerConverter(new LocalDateTimeOrikaConvertor());

        MapperFacade mapper = mapperFactory.getMapperFacade();

        ModelB b = ModelUtil.giveMeB();
        System.out.println(b);

        ModelA a = mapper.map(b, ModelA.class);
        System.out.println(a);

        ModelB b2 = mapper.map(a, ModelB.class);
        System.out.println(b2);
    }
}
