package com.example.demo.service.implementation;

import com.example.demo.domain.Give;
import com.example.demo.domain.Users;
import com.example.demo.repository.GiveRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.GiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;


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
    public Collection<Give> getGivesForUser(Long userId) {
        return giveRepository.listForUser(userId);
    }


//    @Override
//    public Page<Give> getGives(int page, int size) {
//        return giveRepository.findAll(of(page, size));
//    }

}
