package kz.flappy.flappycom.flappycom.repositories;

import kz.flappy.flappycom.flappycom.entities.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {
    List<Friends> findAllByMeId(Long Id);


}
