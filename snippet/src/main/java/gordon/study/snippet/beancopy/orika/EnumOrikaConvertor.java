package gordon.study.snippet.beancopy.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Objects;

public class EnumOrikaConvertor extends BidirectionalConverter<String, Enum> {

    @Override
    public boolean canConvert(Type<?> sourceType, Type<?> destinationType) {
        if (sourceType.isEnum()) {
            return destinationType.equals(this.sourceType);
        }
        if (destinationType.isEnum()) {
            return this.sourceType.equals(sourceType);
        }
        return false;
    }

    @Override
    public Enum convertTo(String source, Type<Enum> destinationType, MappingContext mappingContext) {
        try {
            Class<?> destinationClass = destinationType.getRawType();
            Field[] sourceFiled = destinationClass.getDeclaredFields();
            for (Field field : sourceFiled) {
                field.setAccessible(true);
                if (field.getName().equals("label")) {
                    return valueOf(destinationType.getRawType(), source, field);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String convertFrom(Enum source, Type<String> destinationType, MappingContext mappingContext) {
        try {
            Class<?> sourceClass = source.getClass();
            Field[] sourceFiled = sourceClass.getDeclaredFields();
            for (Field field : sourceFiled) {
                field.setAccessible(true);
                if (field.getName().equals("label")) {
                    return (String) field.get(source);
                }
            }
        } catch (Exception e) {
        }

        return null;
    }

    // copy from com.baomidou.mybatisplus.core.toolkit.EnumUtils
    private <E extends Enum> E valueOf(Class<E> enumClass, Object value, Field field) {
        E[] es = enumClass.getEnumConstants();
        for (E e : es) {
            Object evalue = null;
            try {
                field.setAccessible(true);
                evalue = field.get(e);
            } catch (Exception e1) {
            }
            if (value instanceof Number && evalue instanceof Number
                    && new BigDecimal(String.valueOf(value)).compareTo(new BigDecimal(String.valueOf(evalue))) == 0) {
                return e;
            }
            if (Objects.equals(evalue, value)) {
                return e;
            }
        }
        return null;
    }
}