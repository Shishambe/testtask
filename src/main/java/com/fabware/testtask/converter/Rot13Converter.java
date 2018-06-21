package com.fabware.testtask.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class Rot13Converter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return convert(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return convert(dbData);
    }

    private String convert(String string) {
        char[] charset = string.toCharArray();
        for(int i = 0; i < charset.length; i++) {
            if ((charset[i] >= 'A' && charset[i] <= 'M') ||
                (charset[i] >= 'a' && charset[i] <= 'm')) {
                charset[i] += 13;
            } else if ((charset[i] >= 'N' && charset[i] <= 'Z') ||
                       (charset[i] >= 'n' && charset[i] <= 'z')) {
                charset[i] -= 13;
            }
        }
        return String.valueOf(charset);
    }
}
