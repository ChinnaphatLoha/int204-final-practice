package sit.int204.int204final.utils;

import org.hibernate.type.internal.ParameterizedTypeImpl;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import sit.int204.int204final.dtos.PageDto;

import java.lang.reflect.Type;
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

    public <S, T> PageDto<T> convertPage(Page<S> page, Class<T> targetClass) {
        Type targetType = new ParameterizedTypeImpl(PageDto.class, new Type[]{targetClass}, null);
        PageDto<T> pageDto = modelMapper.map(page, targetType);
        pageDto.setContent(convertList(page.getContent(), targetClass));
        return pageDto;
    }
}
