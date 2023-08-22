package com.example.demo.service.implementation;

import com.example.demo.domain.Give;
import com.example.demo.repository.GiveRepository;
import com.example.demo.service.GiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;


@Service
@RequiredArgsConstructor
public class GiveServiceImpl implements GiveService {

    private final GiveRepository<Give> giveRepository;


    @Override
    public Give createGive(long userId, Give give, MultipartFile image) {
        return giveRepository.create(userId, give, image);
    }

    @Override
    public Collection<Give> getGivesForUser(Long userId) {
        return giveRepository.listForUser(userId);
    }

    @Override
    public Give getGiveById(Long id) {
        return giveRepository.getGiveById(id);
    }

//    @Override
//    public void giveImage(Give give, MultipartFile image) {
//        giveRepository.giveImage(give, image);
//    }

}
