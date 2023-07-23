package com.example.demo.repository;

import com.example.demo.domain.Gets;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GetsRepository extends PagingAndSortingRepository<Gets, Long>, ListCrudRepository<Gets, Long> {
}
