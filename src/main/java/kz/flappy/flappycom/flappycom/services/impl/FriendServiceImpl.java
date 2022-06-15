package kz.flappy.flappycom.flappycom.services.impl;

import kz.flappy.flappycom.flappycom.entities.Friends;
import kz.flappy.flappycom.flappycom.repositories.FriendRepository;
import kz.flappy.flappycom.flappycom.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Override
    public Friends addFriend(Friends friend) {
        return friendRepository.save(friend);
    }

    @Override
    public List<Friends> getAllFriends(Long id) {
        return friendRepository.findAllByMeId(id);
    }

}
