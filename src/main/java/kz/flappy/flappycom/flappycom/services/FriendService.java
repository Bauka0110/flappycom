package kz.flappy.flappycom.flappycom.services;

import kz.flappy.flappycom.flappycom.entities.Friends;

import java.util.List;

public interface FriendService {

    Friends addFriend(Friends friend);
    List<Friends> getAllFriends(Long id);
//    List<Friends> getAllFriendsByUserId(Long id);
//    List<Friends> getAllFriendsByUserId1(Long id);
}
