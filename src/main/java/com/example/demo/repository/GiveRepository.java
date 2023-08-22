package com.example.demo.repository;

import com.example.demo.domain.Give;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface GiveRepository <T extends Give>{

    T get (Long id);

  T create(Long userId, T data, MultipartFile image);
 //   T create(Long userId, String type, double amount, String amountType, String description, MultipartFile image, String location, String status);

    // Give createdGive = giveService.createGive(id, type, amount, amountType, description, image, location, status);


    Collection<T> listForUser(Long userId);

    T getGiveById(Long id);

//    void giveImage(Give give, MultipartFile image);
}
