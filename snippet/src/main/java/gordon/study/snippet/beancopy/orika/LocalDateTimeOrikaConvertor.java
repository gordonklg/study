package gordon.study.snippet.beancopy.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeOrikaConvertor extends BidirectionalConverter<String, LocalDateTime> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime convertTo(String s, Type<LocalDateTime> type, MappingContext mappingContext) {
        try {
            return LocalDateTime.parse(s, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String convertFrom(LocalDateTime localDateTime, Type<String> type, MappingContext mappingContext) {
        if (localDateTime == null) {
            return "";
        }

        return localDateTime.format(formatter);
    }
}