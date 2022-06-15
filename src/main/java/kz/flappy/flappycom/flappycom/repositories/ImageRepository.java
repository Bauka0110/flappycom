package kz.flappy.flappycom.flappycom.repositories;

import kz.flappy.flappycom.flappycom.entities.Images;
import kz.flappy.flappycom.flappycom.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {

    List<Images> findAllByAddedDateIsNotNullOrderByAddedDateDesc();
    List<Images> findAllByUsersId(Long id);
}
