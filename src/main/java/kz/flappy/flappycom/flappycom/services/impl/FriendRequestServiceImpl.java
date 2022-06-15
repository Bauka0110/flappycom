package kz.flappy.flappycom.flappycom.services.impl;

import kz.flappy.flappycom.flappycom.entities.FriendsRequest;
import kz.flappy.flappycom.flappycom.repositories.FriendRequestRepository;
import kz.flappy.flappycom.flappycom.services.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Override
    public List<FriendsRequest> myRequest(Long id) {
        return friendRequestRepository.findAllByFromIdOrderByAddedDateDesc(id);
    }

    @Override
    public List<FriendsRequest> myResponse(Long id) {
        return friendRequestRepository.findAllByToIdOrderByAddedDateDesc(id);
    }

    @Override
    public FriendsRequest addFriendRequest(FriendsRequest friendsRequest) {
        return friendRequestRepository.save(friendsRequest);
    }

    @Override
    public void deleteFriendRequest(Long id) {
        friendRequestRepository.deleteById(id);
    }

    @Override
    public List<FriendsRequest> getAllFollowersByUserId(Long id) {
        return friendRequestRepository.findAllByToId(id);
    }
}
