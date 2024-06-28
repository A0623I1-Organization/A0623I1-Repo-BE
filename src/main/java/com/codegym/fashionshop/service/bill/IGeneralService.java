package com.codegym.fashionshop.service.bill;

import java.util.List;

public interface IGeneralService<T> {
    List<T> findAll();

    T findById(Long id);

    void save(T t);

    void deleteById(Long id);
}
