package com.example.socialapp.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    //Dynamic Filtering using MappingJacksonValue
    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        //bean which contains values to return back
        SomeBean someBean=new SomeBean("value1", "value2", "value3");

        //Creating an instance of mappingJacksonvalue with particular pojo as parameter to execute the filter
        //MappingJacksonValue will allow you to add serialisation logic in addtion to your data
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(someBean);


        //defining dynamic filter logic, by allowing data members in bean which should comes as response using SimpleBeanPropertyFilter
        SimpleBeanPropertyFilter filterLogic= SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");

        //to provide a specific filter, creating an instance of FilterProvider and attaching the filter logic and annotated ID of that bean
        FilterProvider filters=new SimpleFilterProvider().addFilter("SomeBeanFilter",filterLogic);

        //we are setting the filter to mappingJacksonValue
        mappingJacksonValue.setFilters(filters);

        //returning the bean and serialisation logic
        return mappingJacksonValue;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {

        List<SomeBean> list=Arrays.asList(new SomeBean("value1", "value2", "value3"),new SomeBean("value4", "value5", "value6") );

        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(list);

        SimpleBeanPropertyFilter filterLogic= SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");

        FilterProvider filters=new SimpleFilterProvider().addFilter("SomeBeanFilter",filterLogic);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
