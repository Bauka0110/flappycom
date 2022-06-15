package kz.flappy.flappycom.flappycom.repositories;

import kz.flappy.flappycom.flappycom.entities.FriendsRequest;
import kz.flappy.flappycom.flappycom.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendsRequest, Long> {
    List<FriendsRequest> findAllByToIdOrderByAddedDateDesc(Long userId);
    List<FriendsRequest> findAllByFromIdOrderByAddedDateDesc(Long userId);
    List<FriendsRequest> findAllByToId(Long id);

}
