package com.example.demo.repository;

import com.example.demo.domain.Give;
import java.util.Collection;

public interface GiveRepository <T extends Give>{

    T get (Long id);

    T create(T data, Long userId);

    Collection<T> listForUser(Long userId);

    T getGiveById(Long id);
}
