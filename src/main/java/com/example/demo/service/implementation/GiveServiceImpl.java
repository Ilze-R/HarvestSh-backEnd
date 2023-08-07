package com.example.demo.service.implementation;

import com.example.demo.domain.Give;
import com.example.demo.domain.Users;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.GiveRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GiveServiceImpl implements GiveService {

    private final GiveRepository<Give> giveRepository;
    private final UserRepository<Users> userRepository;


    @Override
    public Give createGive( Give give, long userId) {
//        give.setUsers_give_id(userId);
//        Users user = userRepository.get(userId);
//        give.setUser(user);
        return giveRepository.create(give, userId);
    }

    @Override
    public Give getGiveByUserId(Long id) {
        Give give = giveRepository.get(id);
        if (give == null) {
            throw new ApiException("No give found by id: " + id);
        }
        return give;
    }
}
