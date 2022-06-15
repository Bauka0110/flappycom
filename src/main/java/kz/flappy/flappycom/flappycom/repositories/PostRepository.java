package kz.flappy.flappycom.flappycom.repositories;

import kz.flappy.flappycom.flappycom.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long > {
    List<Posts> findAllByAddedDateIsNotNullOrderByAddedDateDesc();

    List<Posts> findAllByUsersId(Long id);
}
