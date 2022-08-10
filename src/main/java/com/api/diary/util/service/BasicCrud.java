package com.api.diary.util.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BasicCrud <D, E> {
    
    E create(D dto);

    E update(D dto, Long id);

    boolean delete(Long id);

    List<E> findAll();

    E findById(Long id);

    List<E> findAllEnabled();

    List<E> findAllDisabled();

    E checkAndSetAtributes(D dto, Long id);
}
