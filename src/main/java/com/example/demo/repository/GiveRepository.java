package com.example.demo.repository;

import com.example.demo.domain.Give;
import com.example.demo.form.UpdateForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface GiveRepository <T extends Give>{

    T get (Long id);

  T create(Long userId, T data, MultipartFile image);

    Collection<T> listForUser(Long userId);

    T getGiveById(Long id);

    T updateGive(Long userId, Give give);

}
