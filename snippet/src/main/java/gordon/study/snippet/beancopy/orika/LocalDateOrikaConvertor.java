package gordon.study.snippet.beancopy.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateOrikaConvertor extends BidirectionalConverter<String, LocalDate> {

    @Override
    public LocalDate convertTo(String s, Type<LocalDate> type, MappingContext mappingContext) {
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String convertFrom(LocalDate localDate, Type<String> type, MappingContext mappingContext) {
        if (localDate == null) {
            return "";
        }

        return localDate.toString();
    }
}