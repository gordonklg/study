package gordon.study.snippet.beancopy.orika;

import gordon.study.snippet.beancopy.model.DefaultState;
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
        converterFactory.registerConverter(new EnumOrikaConvertor());

        MapperFacade mapper = mapperFactory.getMapperFacade();

        ModelB b = ModelUtil.giveMeB();
        System.out.println(b);

        ModelB b1 = mapper.map(b, ModelB.class);
        System.out.println(b1);
        System.out.printf("ENUM name %s, ordinal %d, label %s, value %s, toString %s\n", b1.getState().name(),
                b1.getState().ordinal(), b1.getState().getLabel(), b1.getState().getValue(), b1.getState().toString());

        ModelA a = mapper.map(b, ModelA.class);
        System.out.println(a);

        ModelB b2 = mapper.map(a, ModelB.class);
        System.out.println(b2);
    }
}
