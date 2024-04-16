package com.easy_flow_server.cucmber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;

import java.lang.reflect.Type;

public class CucumberConfig {
    private final ObjectMapper objectMapper;
    public CucumberConfig(){
        objectMapper=new ObjectMapper();
    }
    @DefaultParameterTransformer
    @DefaultDataTableCellTransformer
    @DefaultDataTableEntryTransformer
    public Object transform(final Object from, final Type to){
        return objectMapper.convertValue(from, objectMapper.constructType(to));
    }

}
