package sit.int204.int204final.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.StreamSupport;

public class ListMapper {
    private static final ListMapper converter = new ListMapper();
    private final ModelMapper modelMapper;

    private ListMapper() {
        this.modelMapper = new ModelMapper();
    }

    public static ListMapper getInstance() {
        return converter;
    }

    public <S, T> List<T> convertList(Iterable<S> source, Class<T> targetClass) {
        return StreamSupport.stream(source.spliterator(), false)
                .map(entity -> modelMapper.map(entity, targetClass))
                .toList();
    }

    public <S, T> List<T> convertList(List<S> source, Class<T> targetClass) {
        return source.stream()
                .map(entity -> modelMapper.map(entity, targetClass))
                .toList();
    }
}
