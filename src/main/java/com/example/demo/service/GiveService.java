package com.example.demo.service;

import com.example.demo.domain.Give;
import com.example.demo.dto.UserDTO;
import com.example.demo.form.UpdateForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface GiveService  {

    Give createGive(long userId, Give give, MultipartFile image);

    Collection<Give> getGivesForUser(Long userId);

    Give getGiveById (Long id);

    Give updateGive(Long userId, Give give);
}
