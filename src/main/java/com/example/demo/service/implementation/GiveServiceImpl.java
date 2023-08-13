package com.example.demo.service.implementation;

import com.example.demo.domain.Give;
import com.example.demo.domain.Users;
import com.example.demo.repository.GiveRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@RequiredArgsConstructor
public class GiveServiceImpl implements GiveService {

    private final GiveRepository<Give> giveRepository;


    @Override
    public Give createGive( Give give, long userId) {
        return giveRepository.create(give, userId);
    }

    @Override
    public Collection<Give> getGivesForUser(Long userId) {
        return giveRepository.listForUser(userId);
    }

    @Override
    public Give getGiveById(Long id) {
        return giveRepository.getGiveById(id);
    }

}
