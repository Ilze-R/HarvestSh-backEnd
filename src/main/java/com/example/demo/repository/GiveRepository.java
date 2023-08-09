package com.example.demo.repository;

import com.example.demo.domain.Give;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface GiveRepository <T extends Give>{

    T get (Long id);

    T create(T data, Long userId);

    Collection<T> listForUser(Long userId);
}
