package com.example.demo.service;

import com.example.demo.domain.Give;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface GiveService  {

    Give createGive(long userId, Give give, MultipartFile image);

    Collection<Give> getGivesForUser(Long userId);

    Give getGiveById (Long id);

//    void giveImage(Give give, MultipartFile image);
}
