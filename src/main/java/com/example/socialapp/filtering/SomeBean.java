package com.example.socialapp.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Static Filtering
//adding Json Ignore in class level by mentioning field name, you can add multiple fields with {field names},
//mostly use at field level, as field name can change
//@JsonIgnoreProperties("field1")


//Dynamic Filtering
@JsonFilter("SomeBeanFilter")
public class SomeBean {
    private String field1;

    //mentioning Json Ignore in field level
    //@JsonIgnore
    private String field2;

    private String field3;

    public SomeBean(String field1, String field2, String field3) {
        super();
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    @Override
    public String toString() {
        return "SomeBean{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                '}';
    }
}
