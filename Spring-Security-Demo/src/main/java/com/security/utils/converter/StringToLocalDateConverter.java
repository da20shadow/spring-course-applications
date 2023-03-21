package com.security.utils.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(MappingContext<String, LocalDate> context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.parse(context.getSource(), formatter);
    }
}

