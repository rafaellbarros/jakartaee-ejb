package br.com.rafaellbarros.jakartaee.ejb.core.repository.model.mapper;

import java.util.List;

public interface BaseMapper<E, D> {

    D toDTO(E entity);

    E toEntity(D dto);

    List<D> toDTO(List<E> entities);

    List<D> toDTO(Iterable<E> entities);

    List<E> toEntity(List<D> dtos);

    void fromDTO(D dto, E entity);

}