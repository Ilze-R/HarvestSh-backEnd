package com.example.demo.repository;

import com.example.demo.domain.Give;

import java.util.List;

public interface GiveRepository <T extends Give> {

    T get (Long id);

    T create(T data, Long userId);

    List<T> findAll();


}
