package hu.webuni.hr.npistu.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    D entityToDto(E entity);

    List<D> entitiesToDtos(List<E> entities);

    E dtoToEntity(D dto);

    List<E> dtosToEntities(List<D> dtos);
}
