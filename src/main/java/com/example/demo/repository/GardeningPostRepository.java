package com.example.demo.repository;

import com.example.demo.domain.GardeningPost;
import com.example.demo.domain.Give;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GardeningPostRepository  <T extends GardeningPost>{

    T create(Long userId, T data, MultipartFile image);

    List<T> getAllGardeningPosts();

    List<T> getAllGardeningPosts(int pageSize, int offset);

    List<T> getAllGardeningPost(Pageable pageable);
}
