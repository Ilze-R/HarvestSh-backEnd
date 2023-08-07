package com.example.demo.service;

import com.example.demo.domain.Give;

public interface GiveService  {

    Give createGive(Give give, long userId);
    Give getGiveByUserId(Long id);

}
