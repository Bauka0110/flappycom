package kz.flappy.flappycom.flappycom.services;

import kz.flappy.flappycom.flappycom.entities.FriendsRequest;
import kz.flappy.flappycom.flappycom.entities.Images;

import java.util.List;


public interface FriendRequestService {

    List<FriendsRequest> myRequest(Long id);
    List<FriendsRequest> myResponse(Long id);

    FriendsRequest addFriendRequest(FriendsRequest friendsRequest);

    void deleteFriendRequest(Long id);
    List<FriendsRequest> getAllFollowersByUserId(Long id);

}
