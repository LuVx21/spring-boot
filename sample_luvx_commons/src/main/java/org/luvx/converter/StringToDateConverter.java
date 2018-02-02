package org.luvx.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {

    private String datePattern;

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public Date convert(String arg0) {
        SimpleDateFormat sf = new SimpleDateFormat(this.datePattern);
        try {
            Date d = sf.parse(arg0);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}