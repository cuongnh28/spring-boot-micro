package com.demo.util;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StringToDateConverter implements Converter<String, Date> {
    private static final List<String> datePatterns = Arrays.asList(
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSS ZZZZZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSS Z",
            "yyyy-MM-dd'T'HH:mm:ss.SSS z",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    );
    @Override
    public Date convert(String source) {
        for(String pattern : datePatterns){
            try{
                Date result = new SimpleDateFormat(pattern).parse(source);
                return result;
            }
            catch(Exception e){
                continue;
            }
        }
        throw new IllegalArgumentException("Can not find matched date format");
    }
}
