package com.example.demo.service;

import com.example.demo.domain.Give;
import com.example.demo.domain.Role;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface GiveService  {

    Give createGive(Give give, long userId);
//    Give getGiveByUserId(Long id);

    Collection<Give> getGivesForUser(Long userId);

    Give getGiveById (Long id);
}
