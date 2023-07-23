package com.example.demo.repository;

import com.example.demo.domain.Gives;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GivesRepository extends PagingAndSortingRepository<Gives, Long>, ListCrudRepository<Gives, Long> {
}
