package com.example.library.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {
    private final ModelMapper modelMapper;

    public Mapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
    }

    public <T> T toModel(Object obj, Class<T> zClass) {
        return modelMapper.map(obj, zClass);
    }

    public <T, Y> List<T> toList(List<Y> list, Class<T> zClass) {
        return list.stream().map(e -> toModel(e, zClass)).toList();
    }
}
