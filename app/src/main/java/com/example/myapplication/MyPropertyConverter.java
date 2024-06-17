package com.example.myapplication;

import org.greenrobot.greendao.converter.PropertyConverter;

public class MyPropertyConverter implements PropertyConverter<Address , String> {


    @Override
    public Address convertToEntityProperty(String databaseValue) {
        return null;
    }

    @Override
    public String convertToDatabaseValue(Address entityProperty) {
        return null;
    }
}
