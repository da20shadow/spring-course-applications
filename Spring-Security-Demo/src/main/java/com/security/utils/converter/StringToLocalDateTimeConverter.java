package com.security.utils.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(MappingContext<String, LocalDateTime> context) {
        String source = context.getSource();
        if (source == null || source.trim().isEmpty()) {
            return null;
        }

        DateTimeFormatter formatter;
        if (source.length() <= 10) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }

        return LocalDateTime.parse(source, formatter);
    }
}


