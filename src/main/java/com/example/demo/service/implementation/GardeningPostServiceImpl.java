package com.example.demo.service.implementation;

import com.example.demo.domain.GardeningPost;
import com.example.demo.repository.GardeningPostRepository;
import com.example.demo.service.GardeningPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GardeningPostServiceImpl implements GardeningPostService {

    private final GardeningPostRepository<GardeningPost> gardeningPostRepository;

    @Override
    public GardeningPost createPost(long userId, GardeningPost gardeningPost, MultipartFile image) {
        return gardeningPostRepository.create(userId, gardeningPost, image);
    }

    @Override
    public List<GardeningPost> getAllGardeningPosts() {
        return gardeningPostRepository.getAllGardeningPosts();
    }

    @Override
    public List<GardeningPost> getAllGardeningPosts(int pageSize, int offset) {
        return gardeningPostRepository.getAllGardeningPosts(pageSize, offset);
    }

    @Override
    public List<GardeningPost> getAllGardeningPost(Pageable pageable) {
        return gardeningPostRepository.getAllGardeningPost(pageable);
    }

}
